package CoBo.ItFarm.Data.Dto.Device.Res;

import CoBo.ItFarm.Data.Entity.PredictionEntity;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class DevicePredictionRes {

    private Timestamp time;

    private Float ph;

    private Float ec;

    private Float water_temperature;

    private Float field_temperature;

    public DevicePredictionRes(PredictionEntity predictionEntity){
        this.time = predictionEntity.getTime().getTime();
        this.ph = predictionEntity.getPh();
        this.ec = predictionEntity.getEc();
        this.water_temperature = predictionEntity.getWater_temperature();
        this.field_temperature = predictionEntity.getField_temperature();
    }
}
