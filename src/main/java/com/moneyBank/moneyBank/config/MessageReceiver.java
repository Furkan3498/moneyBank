package com.moneyBank.moneyBank.config;

import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {
    public void receiveMessage(String message) {
        System.out.println("Received Message: " + message);
    }
}