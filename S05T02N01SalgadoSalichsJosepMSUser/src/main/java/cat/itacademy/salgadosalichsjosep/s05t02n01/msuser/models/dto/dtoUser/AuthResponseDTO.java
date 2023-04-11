package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser;

import lombok.Data;

@Data
public class AuthResponseDTO {
    //region ATTRIBUTES
    private String accessToken;
    private String tokenType = "Bearer ";

    //endregion ATTRIBUTES


    //region CONSTRUCTOR
    public AuthResponseDTO(String accessTokenIn){
        this.accessToken = accessTokenIn;
    }

    //endregion CONSTRUCTOR

}
