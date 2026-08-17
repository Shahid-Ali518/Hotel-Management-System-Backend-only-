package com.satech.ourluxuryhotel.ai.service;

import com.satech.ourluxuryhotel.ai.prompt.SystemPrompt;
import com.satech.ourluxuryhotel.ai.tools.AiTools;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class ChatToolService implements AiService {

    private final ChatClient chatClient;

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
                .user(input)
                .call()
                .content();
    }

}
