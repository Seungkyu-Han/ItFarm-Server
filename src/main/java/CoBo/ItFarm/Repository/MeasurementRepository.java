package CoBo.ItFarm.Repository;

import CoBo.ItFarm.Data.Entity.LevelEntity;
import CoBo.ItFarm.Data.Entity.MeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<MeasurementEntity, Long> {

    @Query(
            "SELECT m FROM measurement m " +
                    "JOIN m.time t " +
                    "WHERE t.time >= :time"
    )
    List<MeasurementEntity> getPastData(Timestamp time);
}
