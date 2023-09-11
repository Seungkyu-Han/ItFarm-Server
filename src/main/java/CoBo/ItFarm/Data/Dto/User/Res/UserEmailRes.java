package CoBo.ItFarm.Data.Dto.User.Res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEmailRes {

    @Schema(name = "report_email 온오프", description = "report_email 설정 여부")
    private boolean report_email;

    @Schema(name = "warning_email 온오프", description = "warning_email 설정 여부")
    private boolean warning_email;
}
