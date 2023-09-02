package CoBo.ItFarm.Data.Dto.Auth.Res;

import CoBo.ItFarm.Data.Entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AuthCheckRes {

    @Schema(description = "유저 이름", example = "한승규")
    private String name;

    public AuthCheckRes(UserEntity userEntity){
        this.name = userEntity.getName();
    }
}
