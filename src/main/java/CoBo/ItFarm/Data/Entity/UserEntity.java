package CoBo.ItFarm.Data.Entity;

import CoBo.ItFarm.Data.Enum.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "user")
@Data
public class UserEntity {

    @Id
    private Integer id;

    @Column(length = 10)
    private String name;

    @Column(length = 50)
    private String email;

    @Enumerated(EnumType.ORDINAL)
    private RoleEnum role;

    private String refreshToken;
}
