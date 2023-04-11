package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
public class UserCompletDTO {
    //region ATTRIBUTES
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String dateSignUp;

    @Getter
    @Setter
    private String pass;

    @Getter
    @Setter
    private byte wonPlays;

    @Getter
    @Setter
    private int totalPlays;

    @Getter
    @Setter
    private int x100Vict;

    //endregion ATTRIBUTES

}
