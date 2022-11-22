package com.example.project.service;

import com.example.project.bot.TelegramBot;
import com.example.project.bot.TelegramService;
import com.example.project.dto.ApiResponse;
import com.example.project.dto.OrderDTO;
import com.example.project.entity.AttachmentContent;
import com.example.project.entity.Order;
import com.example.project.repository.AttachmentRepository;
import com.example.project.repository.OrderRepository;
import com.example.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
public class OrderService {

    final Path root = Paths.get("D:\\PDP\\G7\\Project\\src\\main\\resources\\templates\\uploads");

    private final OrderRepository orderRepository;

    private final AttachmentRepository attachmentRepository;

    private final UserRepository userRepository;

    private final TelegramBot telegramBot;

    private final TelegramService telegramService;

    @SneakyThrows
    public ApiResponse save(List<MultipartFile> files, OrderDTO orderDTO) {
        Order order=new Order();
        order.setFromLanguage(orderDTO.getFromLanguage());
        order.setTargetLanguage(orderDTO.getTargetLanguage());
        order.setName(orderDTO.getName());
        order.setEmail(orderDTO.getEmail());
        order.setPhone(orderDTO.getPhone());

        List<AttachmentContent> attachmentContentList = new ArrayList<>();

        if (Objects.nonNull(files)) {
            for (MultipartFile file : files) {
                AttachmentContent attachmentContent = new AttachmentContent();
                attachmentContent.setFileName(file.getName());
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
        for (MultipartFile file : files) {
            if (file.getContentType().equals("application/pdf"))telegramBot.execute(telegramService.sendDocument(file));
            if (file.getContentType().equals("image/jpeg"))telegramBot.execute(telegramService.sendPhoto(file));
            if (file.getContentType().equals("video/mp4"))telegramBot.execute(telegramService.sendVideo(file));
            Files.delete(Path.of(root + "\\" + file.getOriginalFilename()));
        }


        return ApiResponse.builder().success(true).build();
    }

    public ResponseEntity<?> download(Long id) {
        AttachmentContent attachmentContent = attachmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));


        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(attachmentContent.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachmentContent; filename=\"" + attachmentContent.getFileName() + "\"")
                .body(attachmentContent.getBytes());
        }
    }
