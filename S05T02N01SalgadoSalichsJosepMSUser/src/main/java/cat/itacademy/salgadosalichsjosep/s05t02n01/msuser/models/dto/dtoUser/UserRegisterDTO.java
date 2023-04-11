package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

public class UserRegisterDTO {
    //region ATTRIBUTES
    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String pass;

    @Getter
    @Setter
    private String dateSignUp;

    //endregion ATTRIBUTES

}
