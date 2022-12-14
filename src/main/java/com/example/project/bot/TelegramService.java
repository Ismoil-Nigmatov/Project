package com.example.project.bot;

import com.example.project.entity.Order;
import com.example.project.entity.Support;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.io.IOException;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 5:17 PM on 11/15/2022
 * @project Project
 */

@Service
public interface TelegramService {
    SendMessage sendOrder(Order order) throws IOException;

    SendPhoto sendPhoto(MultipartFile file) throws IOException;

    SendDocument sendDocument(MultipartFile file) throws IOException;

    SendMessage help(Support support);

    SendMessage sendEmail(String email);
}
