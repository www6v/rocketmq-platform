/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/22
 *
 */
public class RequestUtils {

    private static Logger logger = LoggerFactory.getLogger(RequestUtils.class);
    private static final Gson REQUEST_GSON = new Gson();
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
            props.load(RequestUtils.class.getClassLoader().getResourceAsStream("application.properties"));
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
        }
        catch (IOException e) {
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
                }
                catch (UnirestException e) {
                    logger.error("login ARCompute failure");
                    throw new IllegalStateException("login ARCompute failure", e);
                }
                Map<String, String> result = REQUEST_GSON.fromJson(response, new TypeToken<Map<String, String>>(){}.getType());
                String error = result.get("error");
                if (!StringUtils.isEmpty(error)) {
                    logger.error("ARCompute Username or Password Wrong");
                    throw new IllegalStateException("ARCompute Username or Password Wrong");
                }
                else {
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

    public static HttpRequest get(String uri) {
        return Unirest.get(host + URL_SEPERATOR + uri).queryString(SESSION_ID, getter.getSessionId());
    }

    public static HttpRequestWithBody post(String uri) {
        return Unirest.post(host + URL_SEPERATOR + uri + "?" + SESSION_ID + "=" + getter.getSessionId());
    }

}