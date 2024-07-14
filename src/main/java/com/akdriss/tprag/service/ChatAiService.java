package com.akdriss.tprag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class ChatAiService {
    private final ChatClient chatClient;
    public ChatAiService(ChatClient.Builder builder){
        this.chatClient= builder.build();
    }

    public String ragChat(Prompt prompt ){
        return chatClient.prompt(prompt).call().content();
    }

}
