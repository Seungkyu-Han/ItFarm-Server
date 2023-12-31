package CoBo.ItFarm.Controller;

import CoBo.ItFarm.Data.Dto.Auth.Req.AuthPatchLoginReq;
import CoBo.ItFarm.Data.Dto.Auth.Res.AuthCheckRes;
import CoBo.ItFarm.Data.Dto.Auth.Res.AuthLoginRes;
import CoBo.ItFarm.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "인증 및 로그인 관련 API")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/check")
    @Operation(summary = "로그인 확인 API", description = "Authorization을 사용하여 로그인을 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = AuthCheckRes.class))),
            @ApiResponse(responseCode = "403", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = Integer.class)))
    })
    public ResponseEntity<AuthCheckRes> check(
            @Parameter(hidden = true)Authentication authentication){
        return authService.check(authentication);
    }

    @GetMapping("/login")
    @Operation(summary = "로그인 API", description = "AccessToken, RefershToken을 반환")
    @Parameters({
            @Parameter(name = "code", description = "카카오 로그인 code", example = "1242134213")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
            content = @Content(schema = @Schema(implementation = AuthLoginRes.class)))
    })
    public ResponseEntity<AuthLoginRes> login(
            @RequestParam String code) throws IOException {
        return authService.login(code);
    }

    @PatchMapping("/login")
    @Operation(summary = "로그인 API", description = "RefreshToken으로 AccessToken을 반환")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
            content = @Content(schema = @Schema(implementation = AuthLoginRes.class)))
    })
    public ResponseEntity<AuthLoginRes> login(@RequestBody AuthPatchLoginReq authPatchLoginReq){
        return authService.login(authPatchLoginReq);
    }

}
