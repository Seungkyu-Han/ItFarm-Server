package CoBo.ItFarm.Controller;

import CoBo.ItFarm.Data.Enum.WarningCategoryEnum;
import CoBo.ItFarm.Service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
            description = "컨테이너 수위 그리고 시간")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public ResponseEntity<HttpStatus> Data10Sec(
            @RequestParam Timestamp time,
            @RequestParam Float first,
            @RequestParam Float second){
        return deviceService.data10Sec(time, first, second);
    }

    @GetMapping("/1hour")
    @Operation(
            summary = "1시간 마다 전송하는 데이터 API",
            description = "필드 온도, 필드 습도, 수온, PH, EC 그리고 시간")
    @Parameters({
            @Parameter(name = "field_temperature", description = "필드 온도", example = "20.1"),
            @Parameter(name = "water_temperature", description = "수온", example = "10.1"),
            @Parameter(name = "humidity", description = "필드 습도", example = "71"),
            @Parameter(name = "ph", description = "ph", example = "5.5"),
            @Parameter(name = "ec", description = "전기전도도", example = "12"),
            @Parameter(name = "time", description = "시간", example = "2023-09-07 17:10:06.728144")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public ResponseEntity<HttpStatus> Data1Hour(
            @RequestParam Float field_temperature,
            @RequestParam Float water_temperature,
            @RequestParam Float humidity,
            @RequestParam Float ph,
            @RequestParam Float ec,
            @RequestParam Timestamp time
    ){
        return deviceService.data1Hour(field_temperature, humidity, water_temperature, ph, ec, time);
    }

    @GetMapping("/predict")
    @Operation(
            summary = "예측 데이터를 전송하는 API",
            description = "예측 수온, 예측 필드 온도 그리고 예측 시간"
    )
    @Parameters({
            @Parameter(name = "prediction_water_temperature", description = "예측 수온", example = "11.1"),
            @Parameter(name = "prediction_field_temperature", description = "예측 필드 온도", example = "21.1"),
            @Parameter(name = "prediction_ph", description = "예측 ph", example = "7.7"),
            @Parameter(name = "prediction_ec", description = "예측 ec", example = "12.4"),
            @Parameter(name = "prediction_time", description = "예측 시간", example = "2023-09-07 18:10:06.728144")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public ResponseEntity<HttpStatus> prediction(
            @RequestParam Float prediction_water_temperature,
            @RequestParam Float prediction_field_temperature,
            @RequestParam Float prediction_ph,
            @RequestParam Float prediction_ec,
            @RequestParam Timestamp prediction_time
    ){
      return deviceService.predict(prediction_ph, prediction_ec, prediction_water_temperature, prediction_field_temperature, prediction_time) ;
    }

    @GetMapping("/report")
    @Operation(
            summary = "경보를 전송하는 API",
            description = "WarningCategory를 참조해서 파라미터 전송(Notion 참고)"
    )
    @Parameters({
            @Parameter(name = "warningCategoryEnum", description = "경보 카테고리", example = "WATER_LEVEL_TOO_HIGH"),
            @Parameter(name = "time", description = "경보 발생 시간", example = "2023-09-07 18:10:06.728144")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public ResponseEntity<HttpStatus> report(
            @RequestParam WarningCategoryEnum warningCategoryEnum, @RequestParam Timestamp time){
        return deviceService.report(warningCategoryEnum, time);
    }
}
