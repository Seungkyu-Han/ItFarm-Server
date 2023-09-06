package CoBo.ItFarm.Repository;

import CoBo.ItFarm.Data.Entity.WarningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarningRepository extends JpaRepository<WarningEntity, Long> {
}
