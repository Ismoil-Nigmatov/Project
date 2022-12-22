package com.example.project.service;

import com.example.project.bot.TelegramBot;
import com.example.project.bot.TelegramService;
import com.example.project.dto.ApiResponse;
import com.example.project.dto.SupportDTO;
import com.example.project.entity.Support;
import com.example.project.repository.SupportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:22 PM on 12/1/2022
 * @project Project
 */

@Service
@RequiredArgsConstructor
public class SupportService {

    @Value("${spring.mail.username}")
    String fromEmail;

    private final SupportRepository supportRepository;

    private final TelegramBot telegramBot;

    private final TelegramService telegramService;

    private final JavaMailSender javaMailSender;

    public ApiResponse<?> contact(SupportDTO supportDTO) {

        Support support=new Support();
        support.setFullName(supportDTO.getFullName());
        support.setPhone(supportDTO.getPhone());
        support.setEmail(supportDTO.getEmail());
        support.setDescription(supportDTO.getDescription());
        supportRepository.save(support);

        try {
            telegramBot.execute(telegramService.help(support));
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo("ismoilnigmatov98@gmail.com");
            String content=" FROM : "+support.getFullName()+
                    "\n EMAIL : "+support.getEmail()+
                    "\n PHONE : "+support.getPhone()+
                    "\n DESCRIPTION : "+support.getDescription()+
                    "\n CREATED : "+support.getTimestamp();

            helper.setText(content);
            helper.setSubject("APPLICATION FROM SUPPORT");
            helper.setFrom(fromEmail, "Globlang Translation");

            javaMailSender.send(message);
            return ApiResponse.builder().success(true).message("Your application has been accepted and forwarded to our staff").build();
        } catch (Exception e) {
            return ApiResponse.builder().success(false).build();
        }
    }
}
