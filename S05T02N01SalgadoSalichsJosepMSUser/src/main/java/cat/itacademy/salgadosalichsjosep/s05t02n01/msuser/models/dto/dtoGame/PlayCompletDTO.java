package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoGame;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class PlayCompletDTO {
    //region ATTRIBUTES
    @Getter
    @Setter
    private int id;

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

    @Getter
    @Setter
    private byte result;

    @Getter
    @Setter
    private boolean victory;

    //endregion ATTRIBUTES


    //region METHODS: PUBLIC
    public void CopyFromStructure(PlayCompletDTO playCompletDTOIn){
        this.id = playCompletDTOIn.getId();
        this.userName = playCompletDTOIn.getUserName();
        this.playNum = playCompletDTOIn.getPlayNum();
        this.dice1Value = playCompletDTOIn.getDice1Value();
        this.dice2Value = playCompletDTOIn.getDice2Value();
        this.result = playCompletDTOIn.getResult();
        this.victory = playCompletDTOIn.isVictory();

    }

    //endregion METHODS: PUBLIC


}
