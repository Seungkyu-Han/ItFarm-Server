package CoBo.ItFarm.Data.Entity;

import CoBo.ItFarm.Data.Enum.WarningCategoryEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "warning")
@Data
@NoArgsConstructor
public class WarningEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "time")
    private TimeEntity time;

    @Enumerated(EnumType.ORDINAL)
    private WarningCategoryEnum category;

    public WarningEntity(TimeEntity time, WarningCategoryEnum warningCategoryEnum){
        this.time = time;
        this.category = warningCategoryEnum;
    }
}
