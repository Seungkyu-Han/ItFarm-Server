package CoBo.ItFarm.Data.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Time;

@Entity(name = "time")
@Data
public class TimeEntity {

    @Id
    private Time time;
}
