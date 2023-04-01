package com.investment.simulatedInvestment.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Slf4j
public class WebSocketChattingHandler extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("#webScoket payload {}",payload);

        TextMessage textMessage = new TextMessage("웹소켓 테스트 완료");
        session.sendMessage(textMessage);
    }

    @EnableWebSocket
    @Configuration
    @RequiredArgsConstructor
    public static class WebSockConfig implements WebSocketConfigurer{

        private final WebSocketHandler webSocketHandler;
        @Override
        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
        }
    }
}
