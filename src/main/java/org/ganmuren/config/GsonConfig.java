package org.ganmuren.config;

import com.google.gson.GsonBuilder;
import org.ganmuren.annotation.NoLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.lang.reflect.Modifier;

/**
 * @author ganmuren
 */
@Configuration
public class GsonConfig {

    // 读取配置文件的时间格式
    @Value("${spring.gson.date-format}")
    private String dateFormat;

    // 将解析器注入Spring容器，替代默认的解析器
    // 默认解析器无法解析无get/set的对象
    @Bean
    @NoLog
    public GsonHttpMessageConverter customConverters() {
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        converter.setGson(new GsonBuilder()
                .setDateFormat(dateFormat)//处理时间戳
                .excludeFieldsWithModifiers(Modifier.PROTECTED)//过滤字段类型PROTECTED
                .create());
        return converter;
    }
}
