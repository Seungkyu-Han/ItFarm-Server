package CoBo.ItFarm.Controller;

import CoBo.ItFarm.Service.Util.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/email")
    public ResponseEntity<HttpStatus> emailTest(){
        return emailService.test();
    }
}
