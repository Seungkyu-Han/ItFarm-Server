package CoBo.ItFarm.Data.Dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthLoginRes {

    private String accessToken;

    private String refreshToken;
}
