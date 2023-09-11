package CoBo.ItFarm.Service;

import CoBo.ItFarm.Data.Dto.User.Req.UserEmailReq;
import CoBo.ItFarm.Data.Dto.User.Res.UserEmailRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface UserService {
    ResponseEntity<UserEmailRes> email(Authentication authentication);

    ResponseEntity<HttpStatus> email(UserEmailReq userEmailReq, Authentication authentication);
}
