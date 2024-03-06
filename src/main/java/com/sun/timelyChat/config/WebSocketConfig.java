package com.sun.timelyChat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker

public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
    @SuppressWarnings("null")
    @Override
    public void registerStompEndpoints (StompEndpointRegistry registry){
        //add new stomp endpoint to websocket config
        //ws websocket
        registry.addEndpoint("/ws").withSockJS();

    }
    @SuppressWarnings("null")
    @Override
    public void configureMessageBroker (MessageBrokerRegistry registry){
        //add apllication destination prefixes
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");

    }
}