package CoBo.ItFarm.Service.Util;

import CoBo.ItFarm.Repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;


    public ResponseEntity<HttpStatus> test() {
        String content = "ITFarm 테스트 이메일입니다.";
        try{
            for(String email : userRepository.getEmailList()){
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setSubject("ITFarm입니다.");
                mimeMessageHelper.setText(content);
                mimeMessageHelper.setTo(email);
                javaMailSender.send(mimeMessage);
                log.info("이메일 전송 성공");
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (MessagingException messagingException){
            throw new RuntimeException(messagingException);
        }
    }
}
