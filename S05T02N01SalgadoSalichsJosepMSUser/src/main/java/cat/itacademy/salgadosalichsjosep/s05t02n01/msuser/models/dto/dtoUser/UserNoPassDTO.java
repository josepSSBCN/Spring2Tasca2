package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class UserNoPassDTO {
    //region ATTRIBUTES
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String dateSignUp;

    @Getter
    @Setter
    private int totalPlays;

    @Getter
    @Setter
    private int wonPlays;

    //endregion ATTRIBUTES

}
