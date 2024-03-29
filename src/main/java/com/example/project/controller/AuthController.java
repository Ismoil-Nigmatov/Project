package com.example.project.controller;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.FlatColorBackgroundProducer;
import cn.apiclub.captcha.text.producer.DefaultTextProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;
import com.example.project.dto.*;
import com.example.project.entity.User;
import com.example.project.repository.RoleRepository;
import com.example.project.repository.UserRepository;
import com.example.project.security.JwtProvider;
import com.example.project.util.CaptchaUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.List;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 3:22 PM on 10/6/2022
 * @project Project
 */

//@CrossOrigin(origins = {"*"},maxAge = 4800,allowCredentials = "false")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Value("${spring.mail.username}")
    String fromEmail;

    private final RoleRepository roleRepository;
    private final JavaMailSender mailSender;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public static final long OTPVALIDDURATION=5 * 60 * 1000L;

    @PostMapping("/login")
    @Operation(summary = "Return token after login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        String token = jwtProvider.generateToken(loginDTO.getEmail());

        return ResponseEntity.ok(TokenDTO.builder().token(token).email(loginDTO.getEmail()).build());
    }

    @Operation(summary = "Captcha")
    @GetMapping("/signup")
    public ResponseEntity<?> registration() {
        UserDTO userDto = new UserDTO();
        getCaptcha(userDto);
        return ResponseEntity.ok(userDto);
    }

    @Operation(summary = "Saving user")
    @PostMapping("/signup")
    ResponseEntity<?> registration(@Valid @RequestBody UserDTO userDto) throws MessagingException, UnsupportedEncodingException {
        if (userDto.getCaptcha().equals(userDto.getHiddenCaptcha())) {
            if (userRepository.existsByEmail(userDto.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already in use");
            }
            if (!userDto.getPassword().equals(userDto.getSecondPassword())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Passwords are not the same");
            }
            User user = new User();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setRole(roleRepository.findById(2L).orElseThrow(() -> new RuntimeException("Role not Found")));
            user.setEnabled(false);
            User save = userRepository.save(user);
            generateOneTimePassword(save);
            return ResponseEntity.ok("We have sent a code to verify your email address. Please enter code ");
        } else {
            getCaptcha(userDto);
            ApiResponse response = new ApiResponse<>();
            response.setData(userDto);
            response.setMessage("Enter the captcha correctly!");
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @Operation(summary = "EmailVerification")
    @PostMapping("/verify")
    public ResponseEntity<?> emailVerification(@Valid @RequestBody VerifyOtpDTO verifyOtpDTO){
        Optional<User> byEmail = userRepository.findByEmail(verifyOtpDTO.getEmail());
        if (byEmail.isPresent()){
            User user = byEmail.get();
            long time = user.getOtpRequestedTime().getTime();
            long l = System.currentTimeMillis();
            if (time + OTPVALIDDURATION > l){
                if (user.getOneTimePassword().equals(verifyOtpDTO.getOtp())) {
                    user.setEnabled(true);
                    clearOTP(user);
                    userRepository.save(user);
                    return ResponseEntity.ok("User successfully registered");
                }return ResponseEntity.status(HttpStatus.CONFLICT).body("Enter valid OTP");
            }return ResponseEntity.status(HttpStatus.CONFLICT).body("OTP expired!");
        }else return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Forgot password!. It will send an email with new 8 digit random static code ")
    @PostMapping("/password")
    public ResponseEntity<?> emailSms(@Valid @RequestBody EmailDTO emailDto) throws MessagingException, UnsupportedEncodingException {
        if (userRepository.existsByEmail(emailDto.getEmail())){
            String senderName = "Globlang Translation";
            String subject = "Reset your Password.";
            String content = "Dear [[name]],<br>"
                    + "We have received a request to reset your Globlang Translation password.<br>"
                    + "This is your password: "+"[[password]]"+"<br>"
                    + "It will give you access to your account<br>"
                    + "Please do not give the code to anyone<br>"
                    +"<br>"
                    +"<br>"
                    + "Sincerely,<br>"
                    + "Globlang Translation Team.";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(fromEmail, senderName);
            helper.setTo(emailDto.getEmail());
            helper.setSubject(subject);

            Optional<User> byEmail = userRepository.findByEmail(emailDto.getEmail());
            if (byEmail.isPresent()) {
                User user = byEmail.get();

                content = content.replace("[[name]]", user.getFirstName());
                double random = Math.random() * 1000000;
                String password=String.valueOf(random).substring(9,9+8);
                content = content.replace("[[password]]",password);

                user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);
                helper.setText(content, true);

                mailSender.send(message);
                return ResponseEntity.ok("We have sent an email to "+emailDto.getEmail()+" .Please check your email to reset your password. It may take 5 minutes to send email. Please make sure to check your spam or junk folder as well.");
            }else return ResponseEntity.status(HttpStatus.CONFLICT).body("Sorry. Something went wrong. Try later!");
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> e = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            e.put(fieldName, errorMessage);
        });
        return e;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> unauthorized(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The email address or password is incorrect");
    }

    private void getCaptcha(UserDTO userDto) {
        java.util.List<Color> textColors = Arrays.asList(Color.BLACK, Color.BLUE, Color.RED,Color.MAGENTA,Color.cyan,Color.ORANGE);
        List<Font> textFonts = Arrays.asList(new Font("Arial", Font.BOLD, 40), new Font("Courier", Font.BOLD, 40));

        Color backgroundColor = Color.WHITE;

        Captcha captcha = new Captcha.Builder(200,50)
                .addText(
                        new DefaultTextProducer(),
                        new DefaultWordRenderer(textColors, textFonts))
                .addBackground(new FlatColorBackgroundProducer(backgroundColor))
                .build();

        userDto.setHiddenCaptcha(captcha.getAnswer());
        userDto.setCaptcha("");
        userDto.setRealCaptcha(CaptchaUtil.encodeCaptcha(captcha));
    }

    public void generateOneTimePassword(User user) throws MessagingException, UnsupportedEncodingException {
        String otp = RandomString.make(8);

        user.setOneTimePassword(otp);
        user.setOtpRequestedTime(new Date());

        userRepository.save(user);

        sendOTPEmail(user, otp);
    }

    public void sendOTPEmail(User user, String otp) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromEmail, "Globlang Support");
        helper.setTo(user.getEmail());

        String subject = "Here's your One Time Password (OTP) - Expire in 5 minutes!";

        String content = "<p>Hello " + user.getFirstName() + "</p>"
                + "<p>For security reason, you're required to use the following "
                + "One Time Password to login:</p>"
                + "<p><b>" + otp + "</b></p>"
                + "<br>"
                + "<p>Note: this OTP is set to expire in 5 minutes.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    public void clearOTP(User user) {
        user.setOneTimePassword(null);
        user.setOtpRequestedTime(null);
        userRepository.save(user);
    }
}
