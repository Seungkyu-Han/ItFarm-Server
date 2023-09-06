package CoBo.ItFarm.Data.Entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity(name = "level")
@Data
@IdClass(TimeEntity.class)
public class LevelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "time")
    private TimeEntity time;

    private Double first;

    private Double second;
}
