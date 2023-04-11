package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Structure class, to define Play in MySQL.
 */
@Data
@Entity
@Table(name = "plays")
public class PlayEntity {
    //region ATTRIBUTES
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Setter
    private int id;

    @Getter
    @Column
    @NotNull
    @Setter
    private int userId;

    @Column
    @Getter
    @NotNull
    @Setter
    private int playNum;

    @Column
    @Getter
    @NotNull
    @Setter
    private byte dice1Value;

    @Column
    @Getter
    @NotNull
    @Setter
    private byte dice2Value;

    //endregion ATTRIBUTES

}
