package CoBo.ItFarm.Data.Entity;

import CoBo.ItFarm.Data.Enum.WarningCategoryEnum;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "warning")
@Data
public class WarningEntity {

    @Id
    private Long id;

    @ManyToOne
    private TimeEntity time;

    @Enumerated(EnumType.ORDINAL)
    private WarningCategoryEnum category;
}
