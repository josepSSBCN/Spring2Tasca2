package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.utils;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions.ExceptionUserNameNoExist;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser.UserCompletDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoGame.PlayBasicDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoGame.PlayCompletDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.entity.UserEntity;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.repository.UserRepository;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Method with more common methods.
 */
public class Utils {
    //region ATTRIBUTES: PUBLIC
    public static ModelMapper modelMapper = new ModelMapper();

    //endregion ATTRIBUTES: PUBLIC


    //region MessagesTexts
    public static final String playInfoNoValid = "Info of play isn't valid.";
    public static final String playErrorWhenSave = "Error when save the play.";
    public static final String unexpectedError = "An unexpected error occurred!\nPlease try again later.";
    public static final String userNameNoExist = "ERROR: User Name no exist.";
    public static final String userErrorWhenSave = "Error when save the user.";
    public static final String userErrorWhenUpdate = "Error when update the user.";
    public static final String userNameSendNoValid = "The user name recived isn't valid.";

    //endregion MessagesTexts


    //region METHODS: PUBLIC
    public static PlayCompletDTO CreatePlayCompletFromBasic(PlayBasicDTO playBasicDTOIn){
        int valueResult = (playBasicDTOIn.getDice1Value() + playBasicDTOIn.getDice2Value());

        return new PlayCompletDTO(0, playBasicDTOIn.getUserName(), playBasicDTOIn.getPlayNum(), playBasicDTOIn.getDice1Value(),
                playBasicDTOIn.getDice2Value(), (byte) valueResult, (valueResult == 7));

    }

    public static List<UserCompletDTO> SortUsersForVict(List<UserCompletDTO> userCompletDTOListIn){
        //region VARIABLES
        UserCompletDTO userCompletDTO;
        List<UserCompletDTO> usersTempListIn = new ArrayList<>();

        //endregion VARIABLES


        //region ACTIONS
        for (int i = userCompletDTOListIn.size()-1; i >= 0; i--){
            userCompletDTO = new UserCompletDTO();
            userCompletDTO = userCompletDTOListIn.stream().max(Comparator.comparing(UserCompletDTO::getX100Vict)).get();
            usersTempListIn.add(userCompletDTO);
            userCompletDTOListIn.remove(userCompletDTO);
        }

        //endregion ACTIONS


        // OUT
        return usersTempListIn;

    }

    public static UserEntity GetUser(String userNameIn, UserRepository userRepositoryIn) throws ExceptionUserNameNoExist {
        //region VARIABLES
        UserEntity userEntity;

        //endregion VARIABLES


        //region ACTIONS
        // Get user info
        userEntity = userRepositoryIn.findByUserName(userNameIn);

        // Check result
        if (userEntity == null || userEntity.getId() == 0){
            throw new ExceptionUserNameNoExist();
        }

        //endregion ACTIONS


        // OUT
        return userEntity;

    }

    //endregion METHODS: PUBLIC

}

