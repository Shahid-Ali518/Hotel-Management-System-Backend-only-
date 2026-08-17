package com.satech.ourluxuryhotel.ai.service;

import com.satech.ourluxuryhotel.ai.dto.AiChatResponse;
import jakarta.annotation.Nullable;
import reactor.core.publisher.Flux;

public interface AiService {

    @Nullable
    AiChatResponse chat(String input);

    default Flux<String> stream(String input) {
        return Flux.empty();
    }
}
