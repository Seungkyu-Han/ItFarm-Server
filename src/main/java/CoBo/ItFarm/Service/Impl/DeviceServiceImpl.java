package CoBo.ItFarm.Service.Impl;

import CoBo.ItFarm.Data.Entity.LevelEntity;
import CoBo.ItFarm.Data.Entity.MeasurementEntity;
import CoBo.ItFarm.Data.Entity.PredictionEntity;
import CoBo.ItFarm.Data.Entity.TimeEntity;
import CoBo.ItFarm.Repository.*;
import CoBo.ItFarm.Service.DeviceService;
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
            Float field_humidity,
            Float water_temperature,
            Float ph,
            Float ec,
            Timestamp time,
            Float prediction_water_temperature,
            Float prediction_field_temperature,
            Float prediction_ph,
            Float prediction_ec,
            Timestamp prediction_time) {

        TimeEntity currentTime = getTimeEntity(time);
        TimeEntity predictionTime = getTimeEntity(prediction_time);

        MeasurementEntity measurementEntity = new MeasurementEntity(currentTime, ph, ec, water_temperature, field_temperature);

        measurementRepository.save(measurementEntity);

        PredictionEntity predictionEntity = new PredictionEntity(predictionTime, prediction_ph, prediction_ec,
                prediction_water_temperature, prediction_field_temperature);

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
