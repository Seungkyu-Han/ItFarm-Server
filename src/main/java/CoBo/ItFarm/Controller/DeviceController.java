package CoBo.ItFarm.Controller;

import CoBo.ItFarm.Data.Dto.Auth.Res.AuthCheckRes;
import CoBo.ItFarm.Service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/device")
@Tag(name = "제어 기기에서 사용하는 API")
@RequiredArgsConstructor
@Slf4j
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping("/10sec")
    @Operation(
            summary = "10초 마다 전송하는 데이터 API",
            description = "수위컨테이너와 시간 전")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = AuthCheckRes.class)))
    })
    public ResponseEntity<HttpStatus> Data10Sec(
            @RequestParam Timestamp time,
            @RequestParam Float first,
            @RequestParam Float second){
        return deviceService.data10Sec(time, first, second);
    }
}
