package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.security;

import lombok.Getter;

public class SecurityConstants {
    //region ATTRIBUTES
    @Getter
    private static final long JWT_EXPIRATION = 700000;

    @Getter
    private static final String JWT_SECRET = "lacasaverdaquevaperlamuntanyaquecorriaperimesimes";

    //endregion ATTRIBUTES


}
