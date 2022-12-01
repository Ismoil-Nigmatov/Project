package com.example.project.service;

import com.example.project.bot.TelegramBot;
import com.example.project.bot.TelegramService;
import com.example.project.dto.ApiResponse;
import com.example.project.entity.Support;
import com.example.project.repository.SupportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:22 PM on 12/1/2022
 * @project Project
 */
@Service
@RequiredArgsConstructor
public class SupportService {
    private final SupportRepository supportRepository;

    private final TelegramBot telegramBot;

    private final TelegramService telegramService;

    public ApiResponse<?> contact(Support support) {
        supportRepository.save(support);
        try {
            telegramBot.execute(telegramService.contact(support));
            return ApiResponse.builder().success(true).message("Your application has been accepted and forwarded to our staff").build();
        } catch (TelegramApiException e) {
            return ApiResponse.builder().success(false).build();
        }
    }
}
