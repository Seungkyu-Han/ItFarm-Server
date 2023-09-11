package CoBo.ItFarm.Data.Dto.Device.Res;

import CoBo.ItFarm.Data.Entity.LevelEntity;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class DeviceLevelRes {
    private Timestamp time;
    private Float first;
    private Float second;

    public DeviceLevelRes(LevelEntity levelEntity){
        this.time = levelEntity.getTime().getTime();
        this.first = levelEntity.getFirst();
        this.second = levelEntity.getSecond();
    }
}
