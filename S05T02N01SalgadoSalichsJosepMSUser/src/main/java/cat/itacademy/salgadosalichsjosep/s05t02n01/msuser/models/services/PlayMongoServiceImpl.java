package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.services;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions.*;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.Documents.PlayDocument;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoGame.PlayBasicDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoGame.PlayCompletDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.entity.UserEntity;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.repository.PlaysRepositoryMongo;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.repository.UserRepository;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayMongoServiceImpl implements PlayServiceInter {
    //region ATTRIBUTES
    @Autowired
    UserRepository userRepository;

    @Autowired
    PlaysRepositoryMongo playsRepositoryMongo;

    //endregion ATTRIBUTES


    //region METHODS: OVERRIDE
    @Override
    public void AddPlay(PlayBasicDTO playBasicDTOIn) throws ExceptionUnexpectedError, ExceptionUserNameNoExist,
            ExceptionPlayInfoNoValid, ExceptionPlayErrorSave {
        //region VARIABLES
        UserEntity userEntity;
        PlayDocument playDocumentToSave;
        PlayDocument playDocumentSaved;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Check if input info is OK.
            if ((playBasicDTOIn == null) || playBasicDTOIn.getUserName().trim().isEmpty()) {
                // playBasicDTOIn no have all info necessary.
                throw new ExceptionPlayInfoNoValid();
            } else {
                // Get user info
                userEntity = Utils.GetUser(playBasicDTOIn.getUserName(), userRepository);

                // Transform data strucutre
                playDocumentToSave = Utils.modelMapper.map(playBasicDTOIn, PlayDocument.class);
                playDocumentToSave.setUserId(userEntity.getId());

                // Save Play
                playDocumentSaved = playsRepositoryMongo.save(playDocumentToSave);

                // Check results
                if (playDocumentSaved.getId().isEmpty()) {
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
            throw new ExceptionUnexpectedError("PlayMongoServiceImpl", "AddPlay");
        }

        //endregion ACTIONS

    }

    @Override
    public List<PlayCompletDTO> GetAll(String userNameIn) throws ExceptionUserNameEmpty, ExceptionUserNameNoExist,
            ExceptionUnexpectedError {
        //region VARIABLES
        int result;
        UserEntity userEntity;
        List<PlayDocument> playDocumentList;
        List<PlayCompletDTO> playCompletList;

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
                playDocumentList = playsRepositoryMongo.findAllByUserId(userEntity.getId());

                // Transform from PlayDocument to PlayCompletDTO.
                playCompletList = new ArrayList<>();
                if (playDocumentList.size() > 0) {
                    // Transform from PlayEntity to PlayCompletDTO
                    for (PlayDocument pd : playDocumentList) {
                        result = pd.getDice1Value() + pd.getDice2Value();
                        playCompletList.add(new PlayCompletDTO(0, userNameIn, pd.getPlayNum(), pd.getDice1Value(),
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
            throw new ExceptionUnexpectedError("PlayMongoServiceImpl", "GetAll");
        }

        //endregion ACTIONS


        // OUT
        return playCompletList;

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
                playsRepositoryMongo.deleteAllByUserId(userEntity.getId());

            }
        } catch (ExceptionUserNameEmpty ex) {
            throw new ExceptionUserNameEmpty();
        } catch (ExceptionUserNameNoExist ex) {
            throw new ExceptionUserNameNoExist();
        } catch (Exception ex) {
            throw new ExceptionUnexpectedError("PlayMongoServiceImpl", "DeleteAll");
        }

        //endregion ACTIONS

    }

    //endregion METHODS: OVERRIDE

}
