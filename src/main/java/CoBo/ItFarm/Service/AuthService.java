package CoBo.ItFarm.Service;

import CoBo.ItFarm.Data.Dto.Auth.Req.AuthPatchLoginReq;
import CoBo.ItFarm.Data.Dto.Auth.Res.AuthCheckRes;
import CoBo.ItFarm.Data.Dto.Auth.Res.AuthLoginRes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface AuthService {

    ResponseEntity<AuthLoginRes> login(String code) throws IOException;

    ResponseEntity<AuthLoginRes> login(AuthPatchLoginReq authPatchLoginReq);

    ResponseEntity<AuthCheckRes> check(Authentication authentication);
}
