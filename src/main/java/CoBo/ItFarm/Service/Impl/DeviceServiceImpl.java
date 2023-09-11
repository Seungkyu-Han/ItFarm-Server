package CoBo.ItFarm.Service.Impl;

import CoBo.ItFarm.Data.Dto.Device.Res.DeviceLevelRes;
import CoBo.ItFarm.Data.Dto.Device.Res.DeviceMeasurementRes;
import CoBo.ItFarm.Data.Dto.Device.Res.DevicePredictionRes;
import CoBo.ItFarm.Data.Dto.Device.Res.DeviceWarningRes;
import CoBo.ItFarm.Data.Entity.*;
import CoBo.ItFarm.Data.Enum.WarningCategoryEnum;
import CoBo.ItFarm.Repository.*;
import CoBo.ItFarm.Service.DeviceService;
import CoBo.ItFarm.Service.Util.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
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
    private final PlantingRepository plantingRepository;
    private final EmailService emailService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Async
    public ResponseEntity<HttpStatus> level(Timestamp time, Float first, Float second) {

        TimeEntity timeEntity = getTimeEntity(time);

        LevelEntity levelEntity = new LevelEntity(timeEntity, first, second);
        levelRepository.save(levelEntity);

        applicationEventPublisher.publishEvent(new DeviceLevelRes(levelEntity));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Async
    public ResponseEntity<HttpStatus> measurement(
            Float field_temperature,
            Float humidity,
            Float water_temperature,
            Float ph,
            Float ec,
            Float led_height,
            Timestamp time) {

        TimeEntity timeEntity = getTimeEntity(time);

        MeasurementEntity measurementEntity = MeasurementEntity.builder()
                .field_temperature(field_temperature)
                .humidity(humidity)
                .water_temperature(water_temperature)
                .ph(ph)
                .ec(ec)
                .led_height(led_height)
                .time(timeEntity)
                .build();

        measurementRepository.save(measurementEntity);
        applicationEventPublisher.publishEvent(new DeviceMeasurementRes(measurementEntity));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Async
    public ResponseEntity<HttpStatus> report(WarningCategoryEnum warningCategoryEnum, Timestamp time) {
        WarningEntity warningEntity = new WarningEntity(getTimeEntity(time), warningCategoryEnum);
        warningRepository.save(warningEntity);
        if(warningCategoryEnum.isSendMail())
            emailService.sendWarningEmail(warningCategoryEnum.name());

        applicationEventPublisher.publishEvent(new DeviceWarningRes(warningEntity));

        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> since(Integer day, Timestamp time) {

        PlantingEntity plantingEntity = PlantingEntity.builder()
                .time(getTimeEntity(time))
                .since(day)
                .build();

        plantingRepository.save(plantingEntity);


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Async
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

        applicationEventPublisher.publishEvent(new DevicePredictionRes(predictionEntity));

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
