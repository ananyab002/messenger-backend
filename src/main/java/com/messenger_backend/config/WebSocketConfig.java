package com.messenger_backend.config;

import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

     @Override
public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/message")
            .setHandshakeHandler(new DefaultHandshakeHandler())
            .addInterceptors(new HandshakeInterceptor() {
                @Override
                public boolean beforeHandshake(
                        ServerHttpRequest request, 
                        ServerHttpResponse response,
                        WebSocketHandler wsHandler,
                        Map<String, Object> attributes) {

                    // Extract token from query parameters
                    String query = request.getURI().getQuery();
                    if (query != null && query.startsWith("token=")) {
                        String token = query.substring(6);
                        attributes.put("token", token);
                    }

                    return true;
                }

                @Override
                public void afterHandshake(ServerHttpRequest request, 
                                          ServerHttpResponse response, 
                                          WebSocketHandler wsHandler, 
                                          Exception exception) {
                    // No action needed after handshake
                }
            })
            .setAllowedOriginPatterns("http://localhost:5173") // Use patterns instead of origins
            .withSockJS();
}

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}

