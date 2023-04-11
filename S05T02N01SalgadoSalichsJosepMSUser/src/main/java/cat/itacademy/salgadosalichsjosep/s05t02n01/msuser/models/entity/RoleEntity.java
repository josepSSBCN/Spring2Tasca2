package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Structure class, to define Role in MySQL.
 */
@Entity
@Getter
@Setter
@Table(name = "roles")
public class RoleEntity {
    //region ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private  String name;

    //endregion ATTRIBUTES

}
