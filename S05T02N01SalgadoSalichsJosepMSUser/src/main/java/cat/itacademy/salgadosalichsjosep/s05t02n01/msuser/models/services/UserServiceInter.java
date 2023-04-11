package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.services;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions.*;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser.UserBasicDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser.UserCompletDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser.UserNoPassDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser.UserRegisterDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser.UserSemiCompletDTO;
import org.springframework.stereotype.Service;

/**
 * Service layer for User
 */
@Service
public interface UserServiceInter {
    //region METHODS

    /**
     * Method to add a new User on DDBB.
     *
     * @param userBasicDTOIn UserBasicDTO class with info to add on DDBB.
     * @return UserBasicDTO class with the ID of user.
     */
    UserBasicDTO Add(UserBasicDTO userBasicDTOIn);

    UserCompletDTO GetLoser() throws ExceptionUnexpectedError;

    UserCompletDTO GetWinner() throws ExceptionUnexpectedError;

    void Update(UserSemiCompletDTO userSemiCompletDTOIn) throws ExceptionUserNameNoExist, ExceptionUnexpectedError,
            ExceptionUserInfoNoValid, ExceptionUserErrorUpdate;

    void UpdateUserName(String actualNameIn, String newNameIn) throws ExceptionUserNameNoExist, ExceptionUserInfoNoValid,
            ExceptionUserErrorUpdate, ExceptionUnexpectedError;

    void Register(UserRegisterDTO userRegisterDTO) throws ExceptionUnexpectedError, ExceptionUserNameEmpty,
            ExceptionUserNameNoExist, ExceptionUserErrorSave;

    //endregion METHODS

}
