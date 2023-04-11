package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.services;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions.*;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser.UserBasicDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser.UserCompletDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser.UserNoPassDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser.UserRegisterDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser.UserSemiCompletDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.entity.RoleEntity;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.entity.UserEntity;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.repository.RoleRepository;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.repository.UserRepository;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserServiceInter {
    //region ATTRIBUTES
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    //endregion ATTRIBUTES


    //region METHODS: OVERRIDE
    @Override
    public UserBasicDTO Add(UserBasicDTO userBasicDTOIn) {
        //region VARIABLES
        UserEntity userEntityToSave, userEntitySaved;
        UserBasicDTO userBasicOut;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Transform from UserBasicDTO to UserEntity
            userEntityToSave = new UserEntity(Utils.modelMapper.map(userBasicDTOIn, UserEntity.class));

            // Add to DDBB
            userEntitySaved = userRepository.save(userEntityToSave);

            // Transform from UserEntity to UserBasicDTO
            userBasicOut = Utils.modelMapper.map(userEntitySaved, UserBasicDTO.class);

        } catch (Exception ex) {
            // Unexpected error.
            System.out.println(ex.getMessage());
            userBasicOut = null;
        }

        //endregion ACTIONS


        // OUT
        return userBasicOut;

    }

    @Override
    public UserCompletDTO GetLoser() throws ExceptionUnexpectedError {
        //region VARIABLES
        UserCompletDTO userCompletDTO;
        List<UserEntity> userEntityList;
        List<UserCompletDTO> userCompletDTOList = new ArrayList<>();

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Get all users.
            userEntityList = userRepository.findAll();

            // Transform from UserEntity to UserCompletDTO
            for (UserEntity ue : userEntityList) {
                userCompletDTO = Utils.modelMapper.map(ue, UserCompletDTO.class);
                userCompletDTO.setX100Vict((int) (((float) userCompletDTO.getWonPlays() /
                        (float) userCompletDTO.getTotalPlays()) * 100));
                userCompletDTOList.add(userCompletDTO);
            }

            // Find loser
            userCompletDTO = userCompletDTOList.stream()
                    .min(Comparator.comparing(UserCompletDTO::getX100Vict))
                    .get();

        } catch (Exception ex) {
            throw new ExceptionUnexpectedError("UserServiceImpl", "GetLoser");
        }

        //endregion ACTIONS


        // OUT
        return userCompletDTO;

    }

    @Override
    public UserCompletDTO GetWinner() throws ExceptionUnexpectedError {
        //region VARIABLES
        UserCompletDTO userCompletDTO;
        List<UserEntity> userEntityList;
        List<UserCompletDTO> userCompletDTOList = new ArrayList<>();

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Get all users.
            userEntityList = userRepository.findAll();

            // Transform from UserEntity to UserCompletDTO
            for (UserEntity ue : userEntityList) {
                userCompletDTO = Utils.modelMapper.map(ue, UserCompletDTO.class);
                userCompletDTO.setX100Vict((int) (((float) userCompletDTO.getWonPlays() /
                        (float) userCompletDTO.getTotalPlays()) * 100));
                userCompletDTOList.add(userCompletDTO);
            }

            // Find loser
            userCompletDTO = userCompletDTOList.stream()
                    .max(Comparator.comparing(UserCompletDTO::getX100Vict))
                    .get();

        } catch (Exception ex) {
            throw new ExceptionUnexpectedError("UserServiceImpl", "GetLoser");
        }

        //endregion ACTIONS


        // OUT
        return userCompletDTO;
    }

    @Override
    public void Update(UserSemiCompletDTO userSemiCompletDTOIn) throws ExceptionUserNameNoExist, ExceptionUnexpectedError,
            ExceptionUserInfoNoValid, ExceptionUserErrorUpdate {
        //region VARIABLES
        UserEntity userEntityToSave;
        UserEntity userEntitySaved;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Check int info is good
            if ((userSemiCompletDTOIn == null) || userSemiCompletDTOIn.getUserName().trim().isEmpty()) {
                throw new ExceptionUserInfoNoValid();
            } else {
                // Get User Info
                userEntityToSave = Utils.GetUser(userSemiCompletDTOIn.getUserName(), userRepository);

                // Copy info to update
                userEntityToSave.setWonPlays(userSemiCompletDTOIn.getWonPlays());
                userEntityToSave.setTotalPlays(userSemiCompletDTOIn.getTotalPlays());

                // Update the User
                userEntitySaved = userRepository.save(userEntityToSave);

                // Check the results
                if (userEntitySaved.getId() == 0) {
                    throw new ExceptionUserErrorUpdate();
                }
            }
        } catch (ExceptionUserNameNoExist ex) {
            throw new ExceptionUserNameNoExist();
        } catch (ExceptionUserInfoNoValid ex) {
            throw new ExceptionUserInfoNoValid();
        } catch (ExceptionUserErrorUpdate ex) {
            throw new ExceptionUserErrorUpdate();
        } catch (Exception ex) {
            throw new ExceptionUnexpectedError("UserServiceImpl", "Update");
        }

        //endregion ACTIONS

    }

    @Override
    public void UpdateUserName(String actualNameIn, String newNameIn) throws ExceptionUserNameNoExist,
            ExceptionUserInfoNoValid, ExceptionUserErrorUpdate, ExceptionUnexpectedError {
        //region VARIABLES
        UserEntity userEntityToSave;
        UserEntity userEntitySaved;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Check int info is good
            if (actualNameIn.trim().isEmpty() || newNameIn.trim().isEmpty()) {
                throw new ExceptionUserInfoNoValid();
            } else {
                // Get User Info
                userEntityToSave = Utils.GetUser(actualNameIn, userRepository);

                // Update userName
                userEntityToSave.setUserName(newNameIn);

                // Update the User
                userEntitySaved = userRepository.save(userEntityToSave);

                // Check the results
                if (userEntitySaved.getId() != 0) {
                    throw new ExceptionUserErrorUpdate();
                }
            }
        } catch (ExceptionUserNameNoExist ex) {
            throw new ExceptionUserNameNoExist();
        } catch (ExceptionUserInfoNoValid ex) {
            throw new ExceptionUserInfoNoValid();
        } catch (ExceptionUserErrorUpdate ex) {
            throw new ExceptionUserErrorUpdate();
        } catch (Exception ex) {
            throw new ExceptionUnexpectedError("UserServiceImpl", "UpdateUserName");
        }

        //endregion ACTIONS

    }

    @Override
    public void Register(UserRegisterDTO userRegisterDTOIn) throws ExceptionUnexpectedError, ExceptionUserNameEmpty,
            ExceptionUserNameNoExist, ExceptionUserErrorSave {
        //region ATTRIBUTES
        UserEntity userEntityToSave;
        UserEntity userEntitySaved;

        //endregion ATTRIBUTES


        //region METHODS
        try {
            // Check username have something.
            if ((userRegisterDTOIn == null) || userRegisterDTOIn.getUserName().trim().isEmpty() ||
                    userRegisterDTOIn.getPass().trim().isEmpty()) {
                // playBasicDTOIn no have all info necessary.
                throw new ExceptionUserNameEmpty();
            } else if (userRepository.existsByUserName(userRegisterDTOIn.getUserName())) {
                throw new ExceptionUserNameExist();
            } else {
                // Transform strucutre from to
                userEntityToSave = new UserEntity(Utils.modelMapper.map(userRegisterDTOIn, UserEntity.class));

                // Password is diferent, need codify it
                userEntityToSave.setPass(passwordEncoder.encode(userRegisterDTOIn.getPass()));

                // Created a role
                RoleEntity roleEntity = roleRepository.findByName("USER").get();
                userEntityToSave.setRoles(Collections.singletonList((roleEntity)));

                // Save to DDBB
                userEntitySaved = userRepository.save(userEntityToSave);

                // Chec result
                if (userEntitySaved.getId() == 0) {
                    throw new ExceptionUserErrorSave();
                }
            }
        } catch (ExceptionUserNameEmpty ex) {
            throw new ExceptionUserNameEmpty();
        } catch (ExceptionUserNameExist ex) {
            throw new ExceptionUserNameNoExist();
        } catch (ExceptionUserErrorSave ex) {
            throw new ExceptionUserErrorSave();
        } catch (Exception ex) {
            throw new ExceptionUnexpectedError("UserServiceImpl", "Register");
        }

        //endregion METHODS

    }

    //endregion METHODS: OVERRIDE

}
