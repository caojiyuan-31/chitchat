package org.ganmuren.config;

import lombok.SneakyThrows;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

//自定义healthInfo
@Component
public class WebHealth implements HealthIndicator {

    @SneakyThrows //lombok自动try/catch
    @Override
    public Health health() {
        InetAddress inet = InetAddress.getByName("www.baidu.com");
        if(inet.isReachable(100)){
            return Health.up().withDetail("msg", "网络连接正常").build();
        }
        return Health.down().withDetail("msg", "网络连接断开").build();
    }
}
