package com.sun.timelyChat.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
//for logging when the user leavs chat
@Slf4j
public class WebSocketEventListener {

    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        //implement later
    }
}
