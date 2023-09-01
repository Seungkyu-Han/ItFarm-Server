package CoBo.ItFarm.Data.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "prediction")
@Data
public class PredictionEntity {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time", referencedColumnName = "time")
    private TimeEntity time;

    private Float ph;

    private Float ec;

    private Float water_temperature;

    private Float field_temperature;
}
