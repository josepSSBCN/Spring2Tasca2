package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoGame;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class PlayBasicDTO {
    //region ATTRIBUTES
    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private int playNum;

    @Getter
    @Setter
    private byte dice1Value;

    @Getter
    @Setter
    private byte dice2Value;

    //endregion ATTRIBUTES


    //region METHODS: PUBLIC
    public void CopyFromStructure(PlayBasicDTO playBasicDTOIn){
        this.userName = playBasicDTOIn.getUserName();
        this.playNum = playBasicDTOIn.getPlayNum();
        this.dice1Value = playBasicDTOIn.getDice1Value();
        this.dice2Value = playBasicDTOIn.getDice2Value();

    }

    //endregion METHODS: PUBLIC

}
