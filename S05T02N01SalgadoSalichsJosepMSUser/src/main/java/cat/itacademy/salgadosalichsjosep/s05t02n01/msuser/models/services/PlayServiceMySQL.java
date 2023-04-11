package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.services;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions.*;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoGame.PlayBasicDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoGame.PlayCompletDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.entity.PlayEntity;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.entity.UserEntity;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.repository.PlaysRepository;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.repository.UserRepository;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayServiceMySQL implements PlayServiceInter {
    //region ATTRIBUTES
    @Autowired
    private PlaysRepository playsRepository;

    @Autowired
    private UserRepository userRepository;

    //endregion ATTRIBUTES


    //region METHODS: OVERRRIDE
    @Override
    public void AddPlay(PlayBasicDTO playBasicDTOIn) throws ExceptionUnexpectedError, ExceptionUserNameNoExist,
            ExceptionPlayInfoNoValid, ExceptionPlayErrorSave {
        //region VARIABLES
        UserEntity userEntity;
        PlayEntity playEntityToSave;
        PlayEntity playEntitySaved;

        //endregion VARIABLES


        //region ACTIONS
        try {
            if ((playBasicDTOIn == null) || playBasicDTOIn.getUserName().trim().isEmpty()) {
                // playBasicDTOIn no have all info necessary.
                throw new ExceptionPlayInfoNoValid();
            } else {
                // Get user info
                userEntity = Utils.GetUser(playBasicDTOIn.getUserName(), userRepository);

                // Transform data strucutre
                playEntityToSave = Utils.modelMapper.map(playBasicDTOIn, PlayEntity.class);
                playEntityToSave.setUserId(userEntity.getId());

                // Save Play
                playEntitySaved = playsRepository.save(playEntityToSave);

                // Check results
                if (playEntitySaved.getId() == 0) {
                    throw new ExceptionPlayErrorSave();
                }
            }
        } catch (ExceptionPlayInfoNoValid ex) {
            throw new ExceptionPlayInfoNoValid();
        } catch (ExceptionUserNameNoExist ex) {
            throw new ExceptionUserNameNoExist();
        } catch (ExceptionPlayErrorSave ex) {
            throw new ExceptionPlayErrorSave();
        } catch (Exception ex) {
            throw new ExceptionUnexpectedError("PlayServiceMySQL", "AddPlay");
        }

        //endregion ACTIONS

    }

    @Override
    public List<PlayCompletDTO> GetAll(String userNameIn) throws ExceptionUserNameEmpty, ExceptionUserNameNoExist,
            ExceptionUnexpectedError {
        //region VARIABLES
        int result;
        UserEntity userEntity;
        List<PlayEntity> playEntityList;
        List<PlayCompletDTO> playCompletDTOList;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Check username have something.
            if (userNameIn.trim().isEmpty()) {
                // playBasicDTOIn no have all info necessary.
                throw new ExceptionUserNameEmpty();
            } else {
                // Get user info
                userEntity = Utils.GetUser(userNameIn, userRepository);

                // Get all plays from DDBB
                playEntityList = playsRepository.findAllByUserId(userEntity.getId());

                // Transform from PlayDocument to PlayCompletDTO.
                playCompletDTOList = new ArrayList<>();
                if (playEntityList.size() > 0) {
                    // Transform from PlayEntity to PlayCompletDTO
                    for (PlayEntity pd : playEntityList) {
                        result = pd.getDice1Value() + pd.getDice2Value();
                        playCompletDTOList.add(new PlayCompletDTO(0, userNameIn, pd.getPlayNum(), pd.getDice1Value(),
                                pd.getDice2Value(), (byte) result, result == 7)
                        );
                    }
                }
            }
        } catch (ExceptionUserNameEmpty ex) {
            throw new ExceptionUserNameEmpty();
        } catch (ExceptionUserNameNoExist ex) {
            throw new ExceptionUserNameNoExist();
        } catch (Exception ex) {
            throw new ExceptionUnexpectedError("PlayServiceMySQL", "GetAll");
        }

        //endregion ACTIONS


        // OUT
        return playCompletDTOList;

    }

    @Override
    public void DeleteAll(String userNameIn) throws ExceptionUnexpectedError, ExceptionUserNameNoExist,
            ExceptionUserNameEmpty {
        //region VARIABLES
        UserEntity userEntity;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Check username have something.
            if (userNameIn.trim().isEmpty()) {
                // playBasicDTOIn no have all info necessary.
                throw new ExceptionUserNameEmpty();
            } else {
                // Get user info
                userEntity = Utils.GetUser(userNameIn, userRepository);

                // Delete all user plays of DDBB
                playsRepository.deleteAllByUserId(userEntity.getId());

            }
        } catch (ExceptionUserNameEmpty ex) {
            throw new ExceptionUserNameEmpty();
        } catch (ExceptionUserNameNoExist ex) {
            throw new ExceptionUserNameNoExist();
        } catch (Exception ex) {
            throw new ExceptionUnexpectedError("PlayServiceMySQL", "DeleteAll");
        }

        //endregion ACTIONS

    }

    //endregion METHODS: OVERRRIDE

}
