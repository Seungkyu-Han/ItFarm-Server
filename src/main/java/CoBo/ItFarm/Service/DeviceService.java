package CoBo.ItFarm.Service;

import CoBo.ItFarm.Data.Enum.WarningCategoryEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;

public interface DeviceService {
    ResponseEntity<HttpStatus> level(Timestamp time, Float first, Float second);
    ResponseEntity<HttpStatus> measurement(Float field_temperature, Float humidity, Float water_temperature, Float ph, Float ec, Float led_height,Timestamp time);
    ResponseEntity<HttpStatus> predict(Float prediction_ph, Float prediction_ec, Float prediction_water_temperature, Float prediction_field_temperature, Timestamp prediction_time);
    ResponseEntity<HttpStatus> report(WarningCategoryEnum warningCategoryEnum, Timestamp time);

}
