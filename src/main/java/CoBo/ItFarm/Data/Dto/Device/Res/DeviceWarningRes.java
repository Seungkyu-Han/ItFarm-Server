package CoBo.ItFarm.Data.Dto.Device.Res;

import CoBo.ItFarm.Data.Entity.WarningEntity;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class DeviceWarningRes {

    private Timestamp time;

    private String category;

    public DeviceWarningRes(WarningEntity warningEntity){
        this.time = warningEntity.getTime().getTime();
        this.category = warningEntity.getCategory().name();
    }
}
