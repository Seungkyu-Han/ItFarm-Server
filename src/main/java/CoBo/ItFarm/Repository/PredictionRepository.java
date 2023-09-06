package CoBo.ItFarm.Repository;

import CoBo.ItFarm.Data.Entity.PredictionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionRepository extends JpaRepository<PredictionEntity, Long> {
}
