package com.example.project.util;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.noise.CurvedLineNoiseProducer;
import cn.apiclub.captcha.text.producer.DefaultTextProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 3:37 PM on 10/27/2022
 * @project Project
 */
public class CaptchaUtil {
    //Creating Captcha Object
    public static Captcha createCaptcha(Integer width, Integer height) {

        return new Captcha.Builder(width, height)
                .addBackground(new GradiatedBackgroundProducer())
                .addText(new DefaultTextProducer(), new DefaultWordRenderer())
                .addNoise(new CurvedLineNoiseProducer())
                .build();
    }

    //Converting to binary String
    public static String encodeCaptcha(Captcha captcha) {
        String image = null;
        try {
            ByteArrayOutputStream bos= new ByteArrayOutputStream();
            ImageIO.write(captcha.getImage(),"jpg", bos);
            byte[] byteArray= Base64.getEncoder().encode(bos.toByteArray());
            //rasmdan textni o'qib olgan joyi
            image = new String(byteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}
