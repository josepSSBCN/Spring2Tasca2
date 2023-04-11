package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class UserSemiCompletDTO {
    //region ATTRIBUTES
    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String dateSignUp;

    @Getter
    @Setter
    private int wonPlays;

    @Getter
    @Setter
    private int totalPlays;

    //endregion ATTRIBUTES


    //region METHODS: PUBLIC
    public void CopyFromStruct(UserSemiCompletDTO userIn){
        this.userName = userIn.getUserName();
        this.dateSignUp = userIn.getDateSignUp();
        this.wonPlays = userIn.getWonPlays();
        this.totalPlays = userIn.getTotalPlays();

    }

    //endregion METHODS: PUBLIC


}
