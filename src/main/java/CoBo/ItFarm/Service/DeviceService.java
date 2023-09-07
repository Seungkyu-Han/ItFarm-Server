package CoBo.ItFarm.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;

public interface DeviceService {
    ResponseEntity<HttpStatus> data10Sec(Timestamp time, Float first, Float second);
}
