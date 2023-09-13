package CoBo.ItFarm.Data.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "level")
@Data
@NoArgsConstructor
public class LevelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "time")
    private TimeEntity time;

    private Float first;

    private Float second;

    public LevelEntity(TimeEntity time, Float first, Float second){
        this.time = time;
        this.first = first;
        this.second = second;
    }
}
