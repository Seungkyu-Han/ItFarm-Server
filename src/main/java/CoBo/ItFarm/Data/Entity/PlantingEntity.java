package CoBo.ItFarm.Data.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "planting")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class PlantingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "time")
    private TimeEntity time;

    private Integer since;
}
