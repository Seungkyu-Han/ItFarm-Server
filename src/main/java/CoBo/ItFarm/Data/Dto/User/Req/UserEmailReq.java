package CoBo.ItFarm.Data.Dto.User.Req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserEmailReq {

    @Schema(name = "report_email", description = "report_email 설정 여부")
    private Boolean report_email;

    @Schema(name = "warning_email", description = "warning_email 설정 여부")
    private Boolean warning_email;
}
