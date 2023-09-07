package CoBo.ItFarm.Repository;

import CoBo.ItFarm.Data.Entity.TimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface TimeRepository extends JpaRepository<TimeEntity, Long> {

    Optional<TimeEntity> findByTime(Timestamp time);
}
