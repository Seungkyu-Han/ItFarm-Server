package CoBo.ItFarm.Data.Entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity(name = "level")
@Data
public class LevelEntity {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time", referencedColumnName = "time")
    private TimeEntity time;

    private Double first;

    private Double second;
}
