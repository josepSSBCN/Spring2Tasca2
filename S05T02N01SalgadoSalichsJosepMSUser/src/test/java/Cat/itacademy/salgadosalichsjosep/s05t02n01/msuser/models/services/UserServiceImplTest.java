package Cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.services;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.dto.dtoUser.UserBasicDTO;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;

import static org.assertj.core.api.Assertions. assertThat;

class UserServiceImplTest {
    //region ATTRIBUTES
    @Autowired
    private UserServiceImpl userServiceImp;

    //endregion ATTRIBUTES

    @BeforeEach
    void setUp() {
        userServiceImp = new UserServiceImpl();
    }

    @Test
    void TestAdd() {
        UserBasicDTO userBasicToSaved, userBasicSaved;
        userBasicToSaved = new UserBasicDTO();
        userBasicToSaved.setUserName("Paco");
        userBasicToSaved.setPass("1234");
        userBasicToSaved.setDateSignUp("01/04/2023");

        userBasicSaved = userServiceImp.Add(userBasicToSaved);

        assertThat(userBasicSaved)
                .as("TEST TO ADD A NEW USER")
                .withFailMessage("Don't add correctly")
                .usingComparator(Comparator
                        .comparing(UserBasicDTO::getId),"Will do comparing with Comparator")
                .isNotEqualTo(0);


    }

    @Test
    void TestCheckUserName() {
    }

    @Test
    void TestGetUserNoPass() {
    }
}