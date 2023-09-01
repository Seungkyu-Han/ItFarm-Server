package CoBo.ItFarm.Data.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "measurement")
@Data
public class MeasurementEntity {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time", referencedColumnName = "time")
    private TimeEntity time;

    private Float ph;

    private Float ec;

    private Float water_temperature;

    private Float field_temperature;
}
