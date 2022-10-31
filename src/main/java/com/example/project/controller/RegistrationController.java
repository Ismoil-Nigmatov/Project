//package com.example.project.controller;
//
//import cn.apiclub.captcha.Captcha;
//import cn.apiclub.captcha.backgrounds.FlatColorBackgroundProducer;
//import cn.apiclub.captcha.noise.NoiseProducer;
//import cn.apiclub.captcha.text.producer.DefaultTextProducer;
//import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;
//import com.example.project.dto.UserDto;
//import com.example.project.entity.User;
//import com.example.project.repository.RoleRepository;
//import com.example.project.repository.UserRepository;
//import com.example.project.util.CaptchaUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.*;
//
//import java.awt.*;
//import java.util.*;
//import java.util.List;
//
///**
// * @author "ISMOIL NIGMATOV"
// * @created 4:32 PM on 10/27/2022
// * @project Project
// */
//@Controller
////@RequestMapping("/r")
//@RequiredArgsConstructor
//public class RegistrationController {
////
////    private final UserRepository userRepository;
////    private final PasswordEncoder passwordEncoder;
////    private final RoleRepository roleRepository;
////
////    @GetMapping("/registration")
////    public String registerUser(Model model) {
////        UserDto userDto = new UserDto();
////        getCaptcha(userDto);
////        model.addAttribute("user", userDto);
////        return "registerUser";
////    }
////
////    @PostMapping("/registration")
////    public String saveUser(@ModelAttribute UserDto userDto,Model model) {
////        if (userDto.getCaptcha().equals(userDto.getHiddenCaptcha())) {
////                if(userRepository.existsByEmail(userDto.getEmail())){
////                    model.addAttribute("message","Email is already in use");
////                    return "registerUser";
////                }
////                if(!userDto.getPassword().equals(userDto.getSecondPassword())) {
////                    model.addAttribute("message","Passwords are not the same");
////                    return "registerUser";
////                }
////
////                User user=new User();
////                user.setFirstName(userDto.getFirstName());
////                user.setLastName(userDto.getLastName());
////                user.setEmail(userDto.getEmail());
////                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
////                user.setPhoneNumber(userDto.getPhoneNumber());
////                if(Objects.nonNull(userDto.getCompanyName())) user.setCompanyName(userDto.getCompanyName());
////                user.setRole(roleRepository.findById(2L).orElseThrow(() -> new RuntimeException("Role not Found")));
////                userRepository.save(user);
////            model.addAttribute("message", "User Registered successfully!");
////            return "redirect:signup";
////        } else {
////            model.addAttribute("message", "Enter the captcha correctly");
////            getCaptcha(userDto);
////            model.addAttribute("user", userDto);
////        }
////        return "registerUser";
////    }
////    private void getCaptcha(UserDto userDto) {
////        List<Color> textColors = Arrays.asList(Color.BLACK, Color.BLUE, Color.RED,Color.MAGENTA);
////        List<Font> textFonts = Arrays.asList(new Font("Arial", Font.BOLD, 40), new Font("Courier", Font.BOLD, 40));
////
////        Color backgroundColor = Color.CYAN;
////
////        Captcha captcha = new Captcha.Builder(200,50)
////                .addText(
////                        new DefaultTextProducer(),
////                        new DefaultWordRenderer(textColors, textFonts))
////                .addBackground(new FlatColorBackgroundProducer(backgroundColor))
////                .gimp()
////                .build();
////
////
////        userDto.setHiddenCaptcha(captcha.getAnswer());
////        userDto.setCaptcha("");
////        userDto.setRealCaptcha(CaptchaUtil.encodeCaptcha(captcha));
////    }
////
////    @ResponseStatus(HttpStatus.BAD_REQUEST)
////    @ExceptionHandler(MethodArgumentNotValidException.class)
////    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
////        Map<String, String> e = new HashMap<>();
////        ex.getBindingResult().getAllErrors().forEach((error) -> {
////            String fieldName = ((FieldError) error).getField();
////            String errorMessage = error.getDefaultMessage();
////            e.put(fieldName, errorMessage);
////        });
////        return e;
////    }
//}
