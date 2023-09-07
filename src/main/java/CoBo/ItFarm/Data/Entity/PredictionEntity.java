package CoBo.ItFarm.Data.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "prediction")
@Data
@NoArgsConstructor
public class PredictionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "time")
    private TimeEntity time;

    private Float ph;

    private Float ec;

    private Float water_temperature;

    private Float field_temperature;

    public PredictionEntity(TimeEntity time, Float ph, Float ec, Float water_temperature, Float field_temperature) {
        this.time = time;
        this.ph = ph;
        this.ec = ec;
        this.water_temperature = water_temperature;
        this.field_temperature = field_temperature;
    }
}
