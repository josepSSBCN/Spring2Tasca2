package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.services;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions.*;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoGame.PlayBasicDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoGame.PlayCompletDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for Play for MySQL and MongoDDBB
 */
@Service
public interface PlayServiceInter {
    //region METHODS
    void AddPlay(PlayBasicDTO playBasicDTOIn) throws ExceptionUnexpectedError, ExceptionUserNameNoExist, ExceptionPlayInfoNoValid, ExceptionPlayErrorSave;

    List<PlayCompletDTO> GetAll(String userNameIn) throws ExceptionUserNameEmpty, ExceptionUserNameNoExist, ExceptionUnexpectedError;

    void DeleteAll (String userNameIn) throws ExceptionUnexpectedError, ExceptionUserNameNoExist, ExceptionUserNameEmpty;

    //endregion METHODS

}
