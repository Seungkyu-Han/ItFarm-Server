package CoBo.ItFarm.Repository;

import CoBo.ItFarm.Data.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByRefreshToken(String refreshToken);
}
