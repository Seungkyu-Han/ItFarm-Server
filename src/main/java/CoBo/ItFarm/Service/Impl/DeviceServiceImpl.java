package CoBo.ItFarm.Service.Impl;

import CoBo.ItFarm.Data.Entity.LevelEntity;
import CoBo.ItFarm.Data.Entity.TimeEntity;
import CoBo.ItFarm.Repository.*;
import CoBo.ItFarm.Service.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

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

        TimeEntity timeEntity = new TimeEntity(time);
        timeRepository.save(timeEntity);

        LevelEntity levelEntity = new LevelEntity(timeEntity, first, second);
        levelRepository.save(levelEntity);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
