package CoBo.ItFarm.Data.Entity;

import CoBo.ItFarm.Data.Enum.WarningCategoryEnum;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "warning")
@Data
public class WarningEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "time")
    private TimeEntity time;

    @Enumerated(EnumType.ORDINAL)
    private WarningCategoryEnum category;
}
