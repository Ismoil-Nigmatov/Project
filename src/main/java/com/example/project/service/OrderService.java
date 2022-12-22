package com.example.project.service;

import com.example.project.bot.TelegramBot;
import com.example.project.bot.TelegramService;
import com.example.project.dto.ApiResponse;
import com.example.project.entity.AttachmentContent;
import com.example.project.entity.Order;
import com.example.project.repository.AttachmentRepository;
import com.example.project.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:49 PM on 11/15/2022
 * @project Project
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    @Value("${spring.mail.username}")
    String fromEmail;

    private final OrderRepository orderRepository;

    private final AttachmentRepository attachmentRepository;

    private final TelegramBot telegramBot;

    private final TelegramService telegramService;

    private final JavaMailSender javaMailSender;

    @SneakyThrows
    public ApiResponse save(MultipartFile[] files,String fromLanguage,String targetLanguage,String name, String email, String phone) {
        try {

            Order order = new Order();
            order.setFromLanguage(fromLanguage);
            order.setTargetLanguage(targetLanguage);
            order.setName(name);
            order.setEmail(email);
            order.setPhone(phone);

            List<AttachmentContent> attachmentContentList = new ArrayList<>();

            if (Objects.nonNull(files)) {
                for (MultipartFile file : files) {
                    AttachmentContent attachmentContent = new AttachmentContent();
                    attachmentContent.setFileName(file.getOriginalFilename());
                    attachmentContent.setContentType(file.getContentType());
                    attachmentContent.setSize(file.getSize());
                    attachmentContent.setBytes(file.getBytes());
                    attachmentContentList.add(attachmentContent);
                    attachmentRepository.save(attachmentContent);
                }
            }

            order.setAttachmentContent(attachmentContentList);
            Order save = orderRepository.save(order);

            telegramBot.execute(telegramService.sendOrder(save));
            log.info("Order sent to the Telegram");

            try {
                if (Objects.nonNull(files)) {
                    for (MultipartFile file : files) {
                        if (file.getContentType().startsWith("application"))
                            telegramBot.execute(telegramService.sendDocument(file));
                        if (file.getContentType().startsWith("image"))
                            telegramBot.execute(telegramService.sendPhoto(file));
                    }
                }
            } catch (Exception e) {
                log.error(String.valueOf(e));
            }

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("ismoilnigmatov98@gmail.com");
            String file;
            if (save.getAttachmentContent().isEmpty())file="NO";else file="YES";
            String content=" FROM : "+save.getName()+
                    "\n EMAIL : "+save.getEmail()+
                    "\n PHONE : "+save.getPhone()+
                    "\n FROM-LANGUAGE : "+save.getFromLanguage()+
                    "\n TARGET-LANGUAGE : "+save.getTargetLanguage()+
                    "\n UPLOADED FILE : "+file+
                    "\n DATE : "+save.getDate()+
                    "\n TIME : "+save.getTime();

            helper.setText(content);
            helper.setSubject("NEW ORDER");
            helper.setFrom(fromEmail, "Globlang Translation");
            javaMailSender.send(message);

            return ApiResponse.builder().success(true).message("Your application has been accepted and forwarded to our staff").build();
        }
        catch (Exception e){
            log.error(String.valueOf(e));
        }
        return ApiResponse.builder().success(false).build();
        }

    public ApiResponse<?> getAll() {
        List<Order> all = orderRepository.findAll();
        return ApiResponse.builder().success(true).data(all).build();
    }

    public ApiResponse<?> getAll(String email) {
        List<Order> allByEmail = orderRepository.findAllByEmail(email);
        return ApiResponse.builder().success(true).data(allByEmail).build();
    }
}
