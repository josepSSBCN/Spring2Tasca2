package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class UserBasicDTO {
    //region ATTRIBUTES
    @Getter
    @Setter
    private int id;

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


////*    //region CONSTRUCTOR
//    public UserBasicDTO(){
//
//    }
//
//    //endregion CONSTRUCTOR

}
