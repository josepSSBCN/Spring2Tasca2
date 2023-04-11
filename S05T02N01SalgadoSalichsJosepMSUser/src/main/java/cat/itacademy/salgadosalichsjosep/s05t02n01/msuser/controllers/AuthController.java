package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.controllers;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions.ExceptionUserErrorSave;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions.ExceptionUserNameEmpty;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions.ExceptionUserNameNoExist;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser.AuthResponseDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser.UserBasicDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser.UserLoginDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser.UserRegisterDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.services.UserServiceImpl;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.security.JWTTokenManager;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.utils.Utils;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping({"/user/auth"})
public class AuthController {
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
    private UserServiceImpl userService;

    // Others
    private ResponseEntity<?> responseEntity;

    private HttpHeaders httpHeaders;

    //endregion ATTRIBUTES


    //region CONSTRUCTOR
    {
        httpHeaders = new HttpHeaders();
        httpHeaders.set("API NAME", "AuthController");
        httpHeaders.set("API VERSION", "v1.0");

    }

    //endregion CONSTRUCTOR


    //region ENDPOINTS: CHANGE VIEWS
    @GetMapping(value = {"/", ""})
    public String IndexForm() {
        return "/home";

    }

    @GetMapping(value = {"/loginFrm"})
    public String LoginForm(Model model) {
        //region ACTIONS
        UserBasicDTO user = new UserBasicDTO();

        model.addAttribute("user", user);
        //endregion ACTIONS


        // OUT
        return "/views/login";

    }

    @GetMapping(value = {"/newUserFrm"})
    public String NewUserForm(Model model) {
        //region ACTIONS
        UserBasicDTO user = new UserBasicDTO();

        model.addAttribute("userNew", user);
        //endregion ACTIONS

        // OUT
        return "/views/newUser";

    }

    @GetMapping(value = {"/userFrm/{userName}"})
    public String UserForm(@PathVariable("userName") String userNameIn, Model model) {
        //region ACTIONS
        model.addAttribute("userNameAtt", userNameIn);

        //endregion ACTIONS

        // OUT
        return "/views/user";

    }

    //endregion ENDPOINTS: CHANGE VIEW


    //region ENDPOINTS: MAIN
    @PostMapping("login")
    public ResponseEntity<?> Login(@RequestBody UserLoginDTO userLoginDTO) {
        //region VARIABLES
        String token;
        ResponseEntity<?> responseEntity;
        Authentication authentication;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initialization
            httpHeaders.set("Endpoint name", "Login");
            httpHeaders.set("Endpoint version", "v1.0");

            // Check user
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDTO.getUserName(),
                            userLoginDTO.getPass()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generated token
            token = jwtTokenManager.generateToken(authentication);

            // Create response
            responseEntity = new ResponseEntity<>(new AuthResponseDTO(token), httpHeaders, HttpStatus.OK);

        } catch (BadCredentialsException ex) {
            // Unauthorized user & Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>("Password error", httpHeaders, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            // Unexpected error & Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(new AuthResponseDTO(""), httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //endregion ACTIONS


        // OUT
        return responseEntity;

    }

    @PostMapping("register")
    public ResponseEntity<?> Register(@RequestBody UserRegisterDTO userRegisterDTOIn) {
        //region VARIABLES
        String token;
        Authentication authentication;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initialization
            httpHeaders.set("Endpoint name", "Register");
            httpHeaders.set("Endpoint version", "v1.0");

            // Call service method
            userService.Register(userRegisterDTOIn);

            // Generated token
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRegisterDTOIn.getUserName(),
                            userRegisterDTOIn.getPass()));
            token = jwtTokenManager.generateToken(authentication);

            // Create response
            responseEntity = new ResponseEntity<>(new AuthResponseDTO(token), httpHeaders, HttpStatus.OK);

        } catch (ExceptionUserNameEmpty ex) {
            // Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.userNameSendNoValid, httpHeaders, HttpStatus.BAD_REQUEST);
        } catch (ExceptionUserNameNoExist ex) {
            // Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.userNameNoExist, httpHeaders, HttpStatus.BAD_REQUEST);
        } catch (ExceptionUserErrorSave ex) {
            // Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.userErrorWhenSave, httpHeaders, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            // Unexpected error & Created response
            System.out.println(ex.getMessage());
            responseEntity = new ResponseEntity<>(Utils.unexpectedError, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //endregion ACTIONS


        // OUT
        return responseEntity;

    }

    //endregion ENDPOINTS: MAIN

}

