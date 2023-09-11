package CoBo.ItFarm.Data.Dto.Device.Res;

import CoBo.ItFarm.Data.Entity.MeasurementEntity;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class DeviceMeasurementRes {

    private Timestamp time;

    private Float ph;

    private Float ec;

    private Float water_temperature;

    private Float field_temperature;

    private Float humidity;

    public DeviceMeasurementRes(MeasurementEntity measurementEntity){
        this.time = measurementEntity.getTime().getTime();
        this.ph = measurementEntity.getPh();
        this.ec = measurementEntity.getEc();
        this.water_temperature = measurementEntity.getWater_temperature();
        this.field_temperature = measurementEntity.getField_temperature();
        this.humidity = measurementEntity.getHumidity();
    }
}
