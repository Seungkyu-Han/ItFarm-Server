package CoBo.ItFarm.Repository;

import CoBo.ItFarm.Data.Entity.LevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<LevelEntity, Long> {
}
