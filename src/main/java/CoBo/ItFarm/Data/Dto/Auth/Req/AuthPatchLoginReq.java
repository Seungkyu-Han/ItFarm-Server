package CoBo.ItFarm.Data.Dto.Auth.Req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthPatchLoginReq {

    @Schema(description = "refreshToken", example = "asdf124sadsafasdf")
    private String refreshToken;
}
