package com.investment.simulatedInvestment.controller;

import com.investment.simulatedInvestment.entity.ChatRoom;
import com.investment.simulatedInvestment.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatService chatService;

    @GetMapping("/room")
    public String room(){
        return "chat/room";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> rooms(){
        return chatService.findAllRoom();
    }

    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name){
           return chatService.createChatRoom(name);
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId){
        model.addAttribute("roomId", roomId);
        return "chat/roomDetail";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId){
        return chatService.findRoomById(roomId);
    }
}
