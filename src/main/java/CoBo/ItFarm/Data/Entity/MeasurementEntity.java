package CoBo.ItFarm.Data.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "measurement")
@Data
@NoArgsConstructor
public class MeasurementEntity {

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

    /**
     * MeasurementEntity id 제외한 생성자
     * @param time TimeEntity
     * @param ph ph
     * @param ec ec
     * @param water_temperature water_temperature
     * @param field_temperature field_temperature
     * @Author Seungkyu-Han
     */
    public MeasurementEntity(TimeEntity time, Float ph, Float ec, Float water_temperature, Float field_temperature){
        this.time = time;
        this.ph = ph;
        this.ec = ec;
        this.water_temperature = water_temperature;
        this.field_temperature = field_temperature;
    }
}
