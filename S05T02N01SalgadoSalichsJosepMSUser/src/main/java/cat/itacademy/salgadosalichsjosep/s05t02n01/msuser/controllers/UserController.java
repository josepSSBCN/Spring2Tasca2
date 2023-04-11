package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.controllers;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions.ExceptionUnexpectedError;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions.ExceptionUserErrorUpdate;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions.ExceptionUserInfoNoValid;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions.ExceptionUserNameNoExist;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser.UserBasicDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser.UserCompletDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser.UserSemiCompletDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.entity.UserEntity;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.repository.UserRepository;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.services.UserServiceImpl;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.security.JWTTokenManager;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping({"/user"})
public class UserController {
    //region ATTRIBUTES
    // Security
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTTokenManager jwtTokenManager;

    // Services
    @Autowired
    private UserServiceImpl userService;

    // Respoitories
    @Autowired
    private UserRepository userRepository;

    // Others
    private HttpHeaders httpHeaders;

    private ResponseEntity<?> responseEntity;

    //endregion ATTRIBUTES


    //region CONSTRUCTOR
    {
        httpHeaders = new HttpHeaders();
        httpHeaders.set("API NAME", "UserController");
        httpHeaders.set("API VERSION", "v1.0");

    }

    //endregion CONSTRUCTOR


    //region ENDPOINTS: CHANGE VIEW
    @GetMapping(value = {"/", ""})
    public String IndexForm() {
        return "/home";
    }

    //endregion ENDPOINTS: CHANGE VIEW


    //region ENDPOINTS: MAIN
    @PostMapping("getComplet")
    public ResponseEntity<?> GetComplet(@RequestBody UserBasicDTO userBasicDTOIn) {
        //region VARIABLES
        ResponseEntity<?> responseEntity;
        UserEntity userEntity;
        UserSemiCompletDTO userSemiCompletDTO;

        //endregion VARIABLES


        //region ACTIONS
        // CHECK IF USERNAME EXIST
        try {
            if (!userRepository.existsByUserName(userBasicDTOIn.getUserName())) {
                // Username no exist.
                responseEntity = new ResponseEntity<>(Utils.userNameNoExist, HttpStatus.BAD_REQUEST);

            } else {
                // EXIST
                // Get complet user info
                userEntity = userRepository.findByUserName(userBasicDTOIn.getUserName());
                userSemiCompletDTO = Utils.modelMapper.map(userEntity, UserSemiCompletDTO.class);

                // Created response
                responseEntity = new ResponseEntity<>(userSemiCompletDTO, httpHeaders, HttpStatus.OK);

            }
        } catch (Exception ex) {
            // Unexpected error & Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.unexpectedError, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //endregion ACTIONS


        // OUT
        return responseEntity;

    }

    @GetMapping("/getAll")
    public ResponseEntity<?> GetAllUsers() {
        //region VARIABLES
        List<UserEntity> userEntityList;
        List<UserCompletDTO> userCompletDTOList;
        responseEntity = null;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initialization
            httpHeaders.set("Endpoint name", "GetAllUsers");
            httpHeaders.set("Endpoint add version", "v1.0");

            // Check userName value.
            userEntityList = userRepository.findAll();

            // Transform from UserEntity to UserCompletDTO.
            userCompletDTOList = new ArrayList<>(userEntityList.stream() ////*(List<UserCompletDTO>)
                    .map(x -> Utils.modelMapper.map(x, UserCompletDTO.class))
                    .collect(Collectors.toList())
            );

            // Calculate
            userCompletDTOList.forEach((x) -> {
                if (x.getTotalPlays() != 0) {
                    x.setX100Vict((int) (((double) x.getWonPlays() / (double) x.getTotalPlays()) * 100));
                }
                x.setPass("");
            });

            // Sort list
            userCompletDTOList.addAll(Utils.SortUsersForVict(userCompletDTOList));

            // Created response
            responseEntity = new ResponseEntity<>(userCompletDTOList, httpHeaders, HttpStatus.OK);

        } catch (Exception ex) {
            // Unexpected error & Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.unexpectedError, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //endregion ACTIONS

        // OUT
        return responseEntity;

    }

    @GetMapping("/ranking")
    public ResponseEntity<?> GetRanking() {
        //region VARIABLES
        int ranking, winPlays, totalPlays;
        List<UserEntity> userEntityList;
        responseEntity = null;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initialization
            httpHeaders.set("Endpoint name", "GetRanking");
            httpHeaders.set("Endpoint add version", "v1.0");

            // Get all users.
            userEntityList = userRepository.findAll();

            // Calculate ranking
            winPlays = userEntityList.stream().mapToInt(UserEntity::getWonPlays).sum();
            totalPlays = userEntityList.stream().mapToInt(UserEntity::getTotalPlays).sum();
            ranking = (int) (100 * ((double) winPlays / (double) totalPlays));

            // Created response
            responseEntity = new ResponseEntity<>(ranking, httpHeaders, HttpStatus.OK);

        } catch (Exception ex) {
            // Unexpected error & Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.unexpectedError, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //endregion ACTIONS

        // OUT
        return responseEntity;
    }

    @GetMapping("/ranking/loser")
    public ResponseEntity<?> GetRankingLoser() {
        //region VARIABLES
        UserCompletDTO userCompletDTO;
        responseEntity = null;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initialization
            httpHeaders.set("Endpoint name", "GetRankingLoser");
            httpHeaders.set("Endpoint add version", "v1.0");

            // Call service method
            userCompletDTO = userService.GetLoser();

            // Created response
            responseEntity = new ResponseEntity<>(userCompletDTO.getUserName(), httpHeaders, HttpStatus.OK);

        } catch (ExceptionUnexpectedError ex) {
            // Unexpected error & Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            // Unexpected error & Created response
            System.out.println("ERROR: " + ex.getMessage());
            responseEntity = new ResponseEntity<>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //endregion ACTIONS

        // OUT
        return responseEntity;

    }

    @GetMapping("/ranking/winner")
    public ResponseEntity<?> GetRankingWinner() {
        //region VARIABLES
        UserCompletDTO userCompletDTO;
        responseEntity = null;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initialization
            httpHeaders.set("Endpoint name", "GetRankingWinner");
            httpHeaders.set("Endpoint add version", "v1.0");

            // Call service method
            userCompletDTO = userService.GetWinner();

            // Created response
            responseEntity = new ResponseEntity<>(userCompletDTO.getUserName(), httpHeaders, HttpStatus.OK);

        } catch (Exception ex) {
            // Unexpected error & Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.unexpectedError, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //endregion ACTIONS

        // OUT
        return responseEntity;

    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> Update(@RequestBody UserSemiCompletDTO userSemiCompletDTOIn) {
        //region ACTIONS
        try {
            // Initialization
            httpHeaders.set("Endpoint name", "Update");
            httpHeaders.set("Endpoint version", "v1.0");

            // Call service method
            userService.Update(userSemiCompletDTOIn);

            // User update successful
            responseEntity = new ResponseEntity<>("OK", httpHeaders, HttpStatus.OK);

        } catch (ExceptionUserNameNoExist ex) {
            // Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.userNameNoExist, httpHeaders, HttpStatus.BAD_REQUEST);
        } catch (ExceptionUserInfoNoValid ex) {
            // Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.userNameSendNoValid, httpHeaders, HttpStatus.BAD_REQUEST);
        } catch (ExceptionUserErrorUpdate ex) {
            // Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.unexpectedError, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            // Unexpected error & Created response
            System.out.println("ERROR: " + ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.unexpectedError, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //endregion ACTIONS


        // OUT
        return responseEntity;

    }

    @PutMapping(value = "/{actualName}/{newName}/update")
    public ResponseEntity<?> UpdateUserName(@PathVariable("actualName") String actualNameIn,
                                            @PathVariable("newName") String newNameIn) {
        //region ACTIONS
        try {
            // Initialization
            httpHeaders.set("Endpoint name", "Update");
            httpHeaders.set("Endpoint version", "v1.0");

            // Call service method
            userService.UpdateUserName(actualNameIn, newNameIn);

            // User update successful
            responseEntity = new ResponseEntity<>("OK", httpHeaders, HttpStatus.OK);

        } catch (ExceptionUserNameNoExist ex) {
            // Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.userNameNoExist, httpHeaders, HttpStatus.BAD_REQUEST);
        } catch (ExceptionUserInfoNoValid ex) {
            // Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.userNameSendNoValid, httpHeaders, HttpStatus.BAD_REQUEST);
        } catch (ExceptionUserErrorUpdate ex) {
            // Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.unexpectedError, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            // Unexpected error & Created response
            System.out.println("ERROR: " + ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.unexpectedError, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //endregion ACTIONS


        // OUT
        return responseEntity;

    }

    //endregion ENDPOINTS: MAIN

}

