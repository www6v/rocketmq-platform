package com.lenovo.arcloud.mq.hbase;

import com.lenovo.arcloud.mq.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.hadoop.conf.Configuration;

import javax.annotation.Resource;
import java.nio.charset.Charset;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/29
 *
 */
public class AbstractHbaseTemplate {


    private static final Charset CHARSET = Charset.forName("UTF-8");

    @Setter @Getter
    @Resource
    private TableFactory tableFactory;

    @Setter @Getter
    @Resource
    private HbaseConfigurationFactoryBean hbaseConfiguration;

    @Setter
    private String encoding;

    public Charset getCharset(){
        return !StringUtils.isEmpty(encoding)?Charset.forName(encoding):CHARSET;
    }
}
