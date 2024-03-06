package com.sun.timelyChat.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ChatMessage {
    //message
    private String content; 
    //sender
    private String sender; 
    //message Type
    private MessageType type;
}
