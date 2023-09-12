package CoBo.ItFarm.Repository;

import CoBo.ItFarm.Data.Entity.LevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface LevelRepository extends JpaRepository<LevelEntity, Long> {
    @Query(
            "SELECT l FROM level l " +
                    "JOIN l.time t " +
                    "WHERE t.time >= :time"
    )
    List<LevelEntity> getPastData(Timestamp time);
}
