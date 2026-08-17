package com.satech.ourluxuryhotel.ai.controller;

import com.satech.ourluxuryhotel.ai.dto.ChatRequest;
import com.satech.ourluxuryhotel.ai.service.AiService;
import com.satech.ourluxuryhotel.ai.service.ChatToolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final ChatToolService chatToolService;

    public AiController(ChatToolService chatToolService) {
        this.chatToolService = chatToolService;
    }

    @PostMapping("/chat")
    public ResponseEntity<?>  chat(@RequestBody ChatRequest request) {

        return chatToolService.chat(request.message()) != null ?
                ResponseEntity.ok(chatToolService.chat(request.message())) :
                ResponseEntity.badRequest().body("Please provide a valid message for AI chat.");
    }
}
