package com.example.project.service;

import com.example.project.bot.TelegramBot;
import com.example.project.bot.TelegramService;
import com.example.project.dto.ApiResponse;
import com.example.project.dto.OrderDTO;
import com.example.project.entity.AttachmentContent;
import com.example.project.entity.Order;
import com.example.project.repository.AttachmentRepository;
import com.example.project.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    private final OrderRepository orderRepository;

    private final AttachmentRepository attachmentRepository;

    private final TelegramBot telegramBot;

    private final TelegramService telegramService;

    @SneakyThrows
    public ApiResponse save(OrderDTO orderDTO) {
        try {

            Order order = new Order();
            order.setFromLanguage(orderDTO.getFromLanguage());
            order.setTargetLanguage(orderDTO.getTargetLanguage());
            order.setName(orderDTO.getName());
            order.setEmail(orderDTO.getEmail());
            order.setPhone(orderDTO.getPhone());

            List<AttachmentContent> attachmentContentList = new ArrayList<>();

            List<MultipartFile> files = orderDTO.getFiles();

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
            log.info("order sent");

            try {
                if (!(files == null)) {
                    for (MultipartFile file : files) {
                        if (file.getContentType().startsWith("application"))
                            telegramBot.execute(telegramService.sendDocument(file));
                        if (file.getContentType().startsWith("image"))
                            telegramBot.execute(telegramService.sendPhoto(file));
                        if (file.getContentType().startsWith("video"))
                            telegramBot.execute(telegramService.sendVideo(file));
                    }
                }
            } catch (Exception e) {
                log.error(String.valueOf(e));
            }

            return ApiResponse.builder().success(true).message("Your application has been accepted and forwarded to our staff").build();
        }
        catch (Exception e){
            log.error(String.valueOf(e));
        }
        return ApiResponse.builder().success(false).build();
        }
    }
