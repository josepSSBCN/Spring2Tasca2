package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.controllers;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions.*;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoGame.PlayBasicDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoGame.PlayCompletDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.services.PlayMongoServiceImpl;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.security.JWTTokenManager;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping({"/game/mongo"})
public class GameControllerMongo {
    //region ATTRIBUTES
    // JWT Security
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTTokenManager jwtTokenManager;

    // Services
    @Autowired
    private PlayMongoServiceImpl playMongoService;

    // Others
    private ResponseEntity<?> responseEntity;

    private HttpHeaders httpHeaders;

    //endregion ATTRIBUTES


    //region CONSTRUCTOR
    {
        httpHeaders = new HttpHeaders();
        httpHeaders.set("API NAME", "GameControllerMongo");
        httpHeaders.set("API VERSION", "v1.0");

    }

    //endregion CONSTRUCTOR


    //region ENDPOINTS: CHANGE VIEW
    @GetMapping(value = {"/gameFrm/{userName}"})
    public String GameForm(@PathVariable("userName") String userNameIn, Model model) {
        //region ACTIONS
        model.addAttribute("userName", userNameIn);

        //endregion ACTIONS


        // OUT
        return "/views/game";

    }

    //endregion ENDPOINTS: CHANGE VIEW


    //region ENDPOINTS: MAIN
    @PostMapping("/add")
    public ResponseEntity<?> Add(@RequestBody PlayBasicDTO playBasicDTOIn) {
        //region ACTIONS
        try {
            // Initialization
            httpHeaders.set("Endpoint name", "add");
            httpHeaders.set("Endpoint version", "v1.0");

            // Call Service Method
            playMongoService.AddPlay(playBasicDTOIn);

            // Create response
            responseEntity = new ResponseEntity<>("OK", httpHeaders, HttpStatus.OK);

        } catch (ExceptionPlayInfoNoValid ex) {
            // Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.userNameSendNoValid, httpHeaders, HttpStatus.BAD_REQUEST);
        } catch (ExceptionUserNameNoExist ex) {
            // Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.userNameNoExist, httpHeaders, HttpStatus.BAD_REQUEST);
        } catch (ExceptionPlayErrorSave ex) {
            // Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.playErrorWhenSave, httpHeaders, HttpStatus.BAD_REQUEST);
        } catch (ExceptionUnexpectedError ex) {
            // Unexpected error & Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.unexpectedError, httpHeaders,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            // Unexpected error & Created response
            System.out.println("ERROR:" + ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.unexpectedError, httpHeaders,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //endregion ACTIONS


        // OUT
        return responseEntity;

    }

    @GetMapping(value = {"/{userName}/plays"})
    public ResponseEntity<?> GetAll(@PathVariable("userName") String userNameIn) {
        //region VARIABLES
        List<PlayCompletDTO> playCompletList;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initialization
            httpHeaders.set("Endpoint name", "GetAll");
            httpHeaders.set("Endpoint version", "v1.0");

            // Call Service Method
            playCompletList = new ArrayList<>(playMongoService.GetAll(userNameIn));

            // Create response
            responseEntity = new ResponseEntity<>(playCompletList, httpHeaders, HttpStatus.OK);

        } catch (ExceptionUserNameEmpty ex) {
            // Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.userNameSendNoValid, httpHeaders, HttpStatus.BAD_REQUEST);
        } catch (ExceptionUserNameNoExist ex) {
            // Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.userNameNoExist, httpHeaders, HttpStatus.BAD_REQUEST);
        } catch (ExceptionUnexpectedError ex) {
            // Unexpected error & Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.unexpectedError, httpHeaders,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            // Unexpected error & Created response
            System.out.println("ERROR:" + ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.unexpectedError, httpHeaders,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //endregion ACTIONS


        // OUT
        return responseEntity;

    }


    @DeleteMapping("/{userName}/plays")
    public ResponseEntity<?> DeleteAll(@PathVariable("userName") String userNameIn) {
        //region ACTIONS
        try {
            // Initialization
            httpHeaders.set("Endpoint name", "DeleteAll");
            httpHeaders.set("Endpoint version", "v1.0");

            // Call Service Method
            playMongoService.DeleteAll(userNameIn);

            // Created response
            responseEntity = new ResponseEntity<>("OK", httpHeaders, HttpStatus.OK);

        } catch (ExceptionUserNameEmpty ex) {
            // Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.userNameSendNoValid, httpHeaders, HttpStatus.BAD_REQUEST);
        } catch (ExceptionUserNameNoExist ex) {
            // Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.userNameNoExist, httpHeaders, HttpStatus.BAD_REQUEST);
        } catch (ExceptionUnexpectedError ex) {
            // Unexpected error & Created response
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
