package com.investment.simulatedInvestment.controller;

import com.investment.simulatedInvestment.common.MessageType;
import com.investment.simulatedInvestment.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatController {
    private final SimpMessageSendingOperations messageSendingOperations;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message){
        if(MessageType.ENTER.equals(message.getType())){
            message.setMessage(message.getSender()+"님이 입장하였습니다.");
        }
        messageSendingOperations.convertAndSend("/sub/chat/room/"+message.getRoomId(), message);
    }

}
