package Cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.services;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions.ExceptionUserNameNoExist;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.entity.UserEntity;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.repository.UserRepository;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.services.PlayMongoServiceImpl;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class PlayMongoServiceImplTest {
    //region ATTRIBUTES

    @Autowired
    PlayMongoServiceImpl playMongoServiceImpl;
    @Autowired
    UserRepository userRepository;

    //endregion ATTRIBUTES


    //region CONSTRUCTOR
    @BeforeEach
    void setUp() {

    }

    //endregion CONSTRUCTOR


    //region METHODS TEST
    @Test
    void addPlayTest() {
    }

    @Test
    void getAllTest() {
    }

    @Test
    void deleteAllTest() {
        //region VARIABLES
        UserEntity userEntity = null; ////* = new UserEntity();
        String userNameExist = "josep", userNameNoExist = "juancaElPanca";


        //endregion VARIABLES


        //region TESTS
        // Test 1
        try {
            userEntity = Utils.GetUser(userNameExist, userRepository);
            assertEquals(userNameExist, userEntity.getUserName());


        } catch (ExceptionUserNameNoExist e) {
            System.out.println("Test Failed!");
        }

        // Test 2
        assertThrows(ExceptionUserNameNoExist.class,
                ()->{
                    UserEntity userEntity2= new UserEntity();
                    userEntity2 = Utils.GetUser(userNameNoExist, userRepository);
        });

        //endregion TEST

    }

    @Test
    void getUserTest() {

    }

    //endregion METHODS TEST

}