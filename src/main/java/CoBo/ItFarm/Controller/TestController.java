package CoBo.ItFarm.Controller;

import CoBo.ItFarm.Data.Dto.Auth.Res.AuthLoginRes;
import CoBo.ItFarm.Data.Entity.MeasurementEntity;
import CoBo.ItFarm.Data.Entity.TimeEntity;
import CoBo.ItFarm.Repository.MeasurementRepository;
import CoBo.ItFarm.Repository.TimeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/api/test")
@Tag(name = "테스트 API(삭제 예정)")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final MeasurementRepository measurementRepository;
    private final TimeRepository timeRepository;

    @GetMapping("/test")
    @Operation(summary = "로그인 API", description = "AccessToken, RefershToken을 반환")
    @Parameters({
            @Parameter(name = "ph", description = "ph", example = "7.0"),
            @Parameter(name = "ec", description = "ec", example = "1.1"),
            @Parameter(name = "field_temperature", description = "field_temperature", example = "37"),
            @Parameter(name = "water_temperature", description = "water_temperature", example = "21"),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = AuthLoginRes.class)))
    })
    public void test(
            @RequestParam Float ph,
            @RequestParam Float ec,
            @RequestParam Float field_temperature,
            @RequestParam Float water_temperature
    ){
        TimeEntity timeEntity = new TimeEntity();
        timeEntity.setTime(new Timestamp(System.currentTimeMillis()));
        timeRepository.save(timeEntity);

        MeasurementEntity measurementEntity = new MeasurementEntity();
        measurementEntity.setPh(ph);
        measurementEntity.setEc(ec);
        measurementEntity.setField_temperature(field_temperature);
        measurementEntity.setWater_temperature(water_temperature);
        measurementEntity.setTime(timeEntity);
        measurementRepository.save(measurementEntity);
    }
}
