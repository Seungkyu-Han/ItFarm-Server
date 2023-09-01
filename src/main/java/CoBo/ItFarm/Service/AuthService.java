package CoBo.ItFarm.Service;

import CoBo.ItFarm.Data.Dto.Auth.Res.AuthLoginRes;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AuthService {

    ResponseEntity<AuthLoginRes> login(String code) throws IOException;
}
