package de.saphijaga.spoozer.config;

import de.saphijaga.spoozer.config.resolver.HttpSessionArgumentResolver;
import de.saphijaga.spoozer.config.resolver.UserDetailsArgumentResolver;
import de.saphijaga.spoozer.config.session.HttpSessionIdHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // TODO add client topics
        config.enableSimpleBroker();
        // TODO add server topics
        config.setApplicationDestinationPrefixes();
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/connect").addInterceptors(new HttpSessionIdHandshakeInterceptor()).withSockJS();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserDetailsArgumentResolver());
        argumentResolvers.add(new HttpSessionArgumentResolver());
    }
}