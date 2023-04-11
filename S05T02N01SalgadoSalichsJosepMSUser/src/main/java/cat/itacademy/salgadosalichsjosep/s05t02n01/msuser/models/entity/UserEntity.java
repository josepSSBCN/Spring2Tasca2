package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Structure class, to define User in MySQL.
 */
@Data
@Entity
@Table(name = "User")
public class UserEntity {
    //region ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    @Column(length = 50)
    @NotNull
    @Getter
    @Setter
    private String userName;

    @Column
    @NotNull
    @Getter
    @Setter
    private String pass;

    @Column(length = 50)
    @NotNull
    @Getter
    @Setter
    private String dateSignUp;

    @Column
    @NotNull
    @Getter
    @Setter
    private int totalPlays;

    @Column
    @NotNull
    @Getter
    @Setter
    private int wonPlays;

    //endregion ATTRIBUTES


    //region JOINTABLE
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<RoleEntity> roles = new ArrayList<>();


    //endregion JOINTABLE


    //region CONSTRUCTOR
    public UserEntity(){

    }
    public UserEntity(UserEntity userEntityIn){
        this.id = userEntityIn.getId();
        this.userName = userEntityIn.getUserName();
        this.pass = userEntityIn.getPass();
        this.dateSignUp = userEntityIn.getDateSignUp();
        this.totalPlays = userEntityIn.getTotalPlays();
        this.wonPlays = userEntityIn.getWonPlays();
        this.roles.addAll(userEntityIn.getRoles());

    }

    //endregion CONSTRUCTOR

}
