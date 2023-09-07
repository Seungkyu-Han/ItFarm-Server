package CoBo.ItFarm.Service.Impl;

import CoBo.ItFarm.Data.Entity.*;
import CoBo.ItFarm.Data.Enum.WarningCategoryEnum;
import CoBo.ItFarm.Repository.*;
import CoBo.ItFarm.Service.DeviceService;
import CoBo.ItFarm.Service.Util.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService {

    private final LevelRepository levelRepository;
    private final MeasurementRepository measurementRepository;
    private final PredictionRepository predictionRepository;
    private final TimeRepository timeRepository;
    private final WarningRepository warningRepository;
    private final EmailService emailService;

    @Override
    public ResponseEntity<HttpStatus> data10Sec(Timestamp time, Float first, Float second) {

        TimeEntity timeEntity = getTimeEntity(time);

        LevelEntity levelEntity = new LevelEntity(timeEntity, first, second);
        levelRepository.save(levelEntity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> data1Hour(
            Float field_temperature,
            Float humidity,
            Float water_temperature,
            Float ph,
            Float ec,
            Timestamp time) {

        TimeEntity timeEntity = getTimeEntity(time);

        MeasurementEntity measurementEntity = MeasurementEntity.builder()
                .field_temperature(field_temperature)
                .humidity(humidity)
                .water_temperature(water_temperature)
                .ph(ph)
                .ec(ec)
                .time(timeEntity)
                .build();

        measurementRepository.save(measurementEntity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> report(WarningCategoryEnum warningCategoryEnum, Timestamp time) {
        WarningEntity warningEntity = new WarningEntity(getTimeEntity(time), warningCategoryEnum);
        warningRepository.save(warningEntity);
        if(warningCategoryEnum.isSendMail())
            emailService.sendWarningEmail(warningCategoryEnum.name());

        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> predict(
            Float prediction_ph,
            Float prediction_ec,
            Float prediction_water_temperature,
            Float prediction_field_temperature,
            Timestamp prediction_time) {

        TimeEntity timeEntity = getTimeEntity(prediction_time);

        PredictionEntity predictionEntity = PredictionEntity.builder()
                .ph(prediction_ph)
                .ec(prediction_ec)
                .water_temperature(prediction_water_temperature)
                .field_temperature(prediction_field_temperature)
                .time(timeEntity)
                .build();

        predictionRepository.save(predictionEntity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private TimeEntity getTimeEntity(Timestamp time){
        Optional<TimeEntity> optionalTimeEntity = timeRepository.findByTime(time);
        TimeEntity timeEntity;

        if(optionalTimeEntity.isPresent())
            timeEntity = optionalTimeEntity.get();
        else{
            timeEntity = new TimeEntity(time);
            timeRepository.save(timeEntity);
        }
        return timeEntity;
    }
}
