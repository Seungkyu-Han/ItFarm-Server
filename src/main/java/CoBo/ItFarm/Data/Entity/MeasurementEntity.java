package CoBo.ItFarm.Data.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "measurement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    private Float humidity;

}
