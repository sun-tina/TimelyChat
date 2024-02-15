package com.sun.timelyChat.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;


@Controller
public class ChatController {
    //add user

    // send message
    //sent to this endpoint and qued to topic/public
    @MessageMapping("/chat.sendMessage")
    //topic from websocket config
    @SendTo("/topic/public")
    public ChatMessage sendMessage (@Payload ChatMessage chatMessage){
        return chatMessage;
    }
    //establishes connection btwn user and websocket
    @MessageMapping("/chat.addUser")
    //topic from websocket config
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){

        //adds username to websocket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
