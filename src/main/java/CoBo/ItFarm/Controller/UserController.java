package CoBo.ItFarm.Controller;

import CoBo.ItFarm.Data.Dto.User.Req.UserEmailReq;
import CoBo.ItFarm.Data.Dto.User.Res.UserEmailRes;
import CoBo.ItFarm.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "유저 관련 API")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/email")
    @Operation(summary = "유저의 이메일 발송 정보를 가져오는 API", description = "알림, 보고 관련 정보")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = UserEmailRes.class))),
            @ApiResponse(responseCode = "403", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = Integer.class)))
    })
    public ResponseEntity<UserEmailRes> email(
            @Parameter(hidden = true) Authentication authentication){
        return userService.email(authentication);
    }

    @PatchMapping("/email")
    @Operation(summary = "유저의 이메일 발송 정보를 수정하는 API", description = "알림, 보고 관련 정보")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = HttpStatusCode.class))),
            @ApiResponse(responseCode = "403", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = HttpStatusCode.class)))
    })
    public ResponseEntity<HttpStatus> email(
            @RequestBody UserEmailReq userEmailReq,
            @Parameter(hidden = true) Authentication authentication
            ){
        return userService.email(userEmailReq, authentication);
    }

}
