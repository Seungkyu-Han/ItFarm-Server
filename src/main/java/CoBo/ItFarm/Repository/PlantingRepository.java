package CoBo.ItFarm.Repository;

import CoBo.ItFarm.Data.Entity.PlantingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantingRepository extends JpaRepository<PlantingEntity, Integer> {
}
