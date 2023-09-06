package CoBo.ItFarm.Repository;

import CoBo.ItFarm.Data.Entity.MeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<MeasurementEntity, Long> {
}
