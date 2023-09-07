package CoBo.ItFarm.Repository;

import CoBo.ItFarm.Data.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByRefreshToken(String refreshToken);

    @Query("SELECT u.email FROM user u " +
            "WHERE u.warningEmail = :warningEmail")
    List<String> getWarningEmailList(Boolean warningEmail);
}
