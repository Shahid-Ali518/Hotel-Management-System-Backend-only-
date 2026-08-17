package com.satech.ourluxuryhotel.ai.service;

import com.satech.ourluxuryhotel.ai.prompt.SystemPrompt;
import com.satech.ourluxuryhotel.ai.tools.AiTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatToolService implements AiService {

    private final ChatClient chatClient;

     private final String CONVERSATION_ID = "hotel-assistant-conversation";



    public ChatToolService(ChatClient.Builder chatClientBuilder, AiTools aiTools) {
        this.chatClient = chatClientBuilder
                .defaultSystem(SystemPrompt.HOTEL_ASSISTANT)
                .defaultTools(aiTools)
                .build();
    }

    @Override
    public String chat(String input) {
        log.info("AI chat request: {}", input);

        return chatClient
                .prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID,  CONVERSATION_ID))
                .user(input)
                .call()
                .content();
    }

}
