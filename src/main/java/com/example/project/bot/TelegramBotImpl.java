package com.example.project.bot;

import com.example.project.entity.Order;
import com.example.project.entity.Support;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import java.io.*;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 5:18 PM on 11/15/2022
 * @project Project
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramBotImpl implements TelegramService {
    @Value("${chat.id}")
    private String chatId;

    @Override
    public SendMessage sendOrder(Order order) {
        String uploaded;
        if (order.getAttachmentContent().isEmpty()) uploaded = "No";
        else uploaded = "Yes";

        return SendMessage.builder()
                .text("NEW ORDER"
                        + "\n FROM : " + order.getName() +
                        "\n EMAIL : " + order.getEmail() +
                        "\n PHONE : " + order.getPhone() +
                        "\n FROM-LANGUAGE : " + order.getFromLanguage() +
                        "\n TARGET-LANGUAGE : " + order.getTargetLanguage() +
                        "\n UPLOADED FILE : " + uploaded +
                        "\n DATE : " + order.getDate()+
                        "\n TIME : " + order.getTime().toString().substring(0,8))
                .chatId(chatId)
                .build();
    }

    @Override
    public SendPhoto sendPhoto(MultipartFile file) throws IOException {
        InputStream inputStream=file.getInputStream();
        return SendPhoto.builder().photo(new InputFile(inputStream,file.getOriginalFilename())).chatId(chatId).build();
    }

    @Override
    public SendDocument sendDocument(MultipartFile file) throws IOException {
        InputStream inputStream=file.getInputStream();
        return SendDocument.builder().document(new InputFile(inputStream,file.getOriginalFilename())).chatId(chatId).build();
    }

    @Override
    public SendVideo sendVideo(MultipartFile file) throws IOException {
        InputStream inputStream=file.getInputStream();
        return SendVideo.builder().video(new InputFile(inputStream,file.getOriginalFilename())).chatId(chatId).build();
    }

    @Override
    public SendMessage contact(Support support) {
        return SendMessage.builder().text("APPLICATION FROM SUPPORT"+
                "\n FROM : "+support.getFullName()+
                "\n EMAIL : "+support.getEmail()+
                "\n PHONE : "+support.getPhone()+
                "\n DESCRIPTION : "+support.getDescription()).chatId(chatId).build();
    }
}
