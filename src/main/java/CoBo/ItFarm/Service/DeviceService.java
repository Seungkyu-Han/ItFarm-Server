package CoBo.ItFarm.Service;

import CoBo.ItFarm.Data.Enum.WarningCategoryEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;

public interface DeviceService {
    ResponseEntity<HttpStatus> data10Sec(Timestamp time, Float first, Float second);

    ResponseEntity<HttpStatus> data1Hour(Float field_temperature, Float field_humidity, Float water_temperature, Float ph, Float ec, Timestamp time, Float prediction_water_temperature, Float prediction_field_temperature, Float prediction_ph, Float prediction_ec, Timestamp prediction_time);

    ResponseEntity<HttpStatus> report(WarningCategoryEnum warningCategoryEnum, Timestamp time);
}
