package com.lenovo.arcloud.mq.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/22
 *
 */
public class RequestUtils {

    private static Logger logger = Logger.getLogger(RequestUtils.class);
    private static final Gson gson = new Gson();
    private static final String SESSION_ID = "session.id";
    private static final String URL_SEPERATOR = "/";

    private static String host;
    private static String username;
    private static String password;
    private static String period;
    private static Timer timer = new Timer();
    private static SessionIdGetter getter = new SessionIdGetter();

    static {
        Properties props = new Properties();
        try {
            props.load(RequestUtils.class.getClassLoader().getResourceAsStream("arCompute.properties"));
            host = props.getProperty("arCompute.address");
            username = props.getProperty("arCompute.user");
            password = props.getProperty("arCompute.password");
            period = props.getProperty("arCompute.period", String.valueOf(20 * 60 * 1000));
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                     getter.setActive(false);
                }
            }, Integer.valueOf(period), Integer.valueOf(period));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class SessionIdGetter {
        private String sessionId;
        private boolean active = false;

        public synchronized String getSessionId() {
            if (StringUtils.isEmpty(sessionId) || !isActive()) {
                String response;
                try {
                    response = Unirest.post(host).header("accept", "application/json").field("action", "login")
                            .field("username", username).field("password", password).asString().getBody();
                } catch (UnirestException e) {
                    logger.error("登录ARCompute出错");
                    throw new IllegalStateException("登录录ARCompute出错", e);
                }
                Map<String, String> result = gson.fromJson(response, new TypeToken<Map<String, String>>() {
                }.getType());
                String error = result.get("error");
                if (!StringUtils.isEmpty(error)) {
                    logger.error("ARCompute用户名或密码错误");
                    throw new IllegalStateException("ARCompute用户名或密码错误");
                } else {
                    sessionId = result.get(SESSION_ID);
                }
                active = true;
            }
            return sessionId;
        }

        private synchronized boolean isActive() {
            return active;
        }

        private synchronized SessionIdGetter setActive(boolean active) {
            this.active = active;
            return this;
        }
    }

    public static HttpRequest get(String uri){
        return Unirest.get(host + URL_SEPERATOR + uri).queryString(SESSION_ID ,getter.getSessionId());
    }

    public static HttpRequestWithBody post(String uri){
        return Unirest.post(host + URL_SEPERATOR + uri + "?" +SESSION_ID+"="+getter.getSessionId());
    }

}
