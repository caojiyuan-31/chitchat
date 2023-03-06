package org.ganmuren.config;

import org.ganmuren.annotation.NoLog;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker //开启webSocket消息代理
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    @NoLog
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //设置消息代理的前缀，即如果消息的前缀是"/topic"，就会将消息转发给消息代理（broker），再由消息代理将消息广播给当前连接的客户端。
        registry.enableSimpleBroker("/topic", "/queue");
        //配置需要被注解处理的消息，例：@MessageMapping("/send")接收/app/send路径的消息
        //前缀为"/app"的 destination 可以通过@MessageMapping注解的方法处理，而其他destination（例如"topic","/queue"）将被直接交给broker处理。
        registry.setApplicationDestinationPrefixes("/app");
        WebSocketMessageBrokerConfigurer.super.configureMessageBroker(registry);
    }

    @Override
    @NoLog
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //定义一个前缀为"/chat"的 endpoint，为建立连接的路径，并开启sockjs支持
        //sockjs可以解决浏览器对 WebSocket 的兼容性问题，客户端通过这里配置的URL来建立 WebSocket 连接
        registry.addEndpoint("/chat").withSockJS();
        WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registry);
    }
}
