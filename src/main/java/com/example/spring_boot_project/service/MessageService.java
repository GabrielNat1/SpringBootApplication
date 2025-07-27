package com.example.spring_boot_project.service;

import com.example.spring_boot_project.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public String getMessage() {
        return messageRepository.getMessage();
    }
}
