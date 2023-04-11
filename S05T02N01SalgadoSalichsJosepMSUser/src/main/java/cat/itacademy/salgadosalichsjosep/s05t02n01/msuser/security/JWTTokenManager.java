package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTokenManager {
    //region METHODS
    /**
     * Method to created a token
     * @param authenticationIn
     * @return
     */
    public String generateToken(Authentication authenticationIn){
        //region VARIABLES
        String token;
        String userName = authenticationIn.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime()+ SecurityConstants.getJWT_EXPIRATION());

        //endregion VARIABLES


        //region ACTIONS
        // GENERATE TOKEN
        token = Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.getJWT_SECRET())
                .compact();

        //endregion ACTIONS


        // OUT
        return token;

    }

    /**
     * Method to take UserName from token.
     * @param token Token with info
     * @return UserName
     */
    public String getUserNameFromJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SecurityConstants.getJWT_SECRET())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts
                    .parserBuilder()
                    .setSigningKey(SecurityConstants.getJWT_SECRET())
                    .build()
                    .parseClaimsJws(token);

            return true;
        }catch (Exception ex){
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }

    //endregion METHODS


}
