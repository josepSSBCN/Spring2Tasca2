package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
    //region ATTRIBUTES
    @Autowired
    private JWTTokenManager jwtTokenManager;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    //endregion ATTRIBUTES


    //region METHODS
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getJWTFromRequest(request);
        if (StringUtils.hasText(token) && jwtTokenManager.validateToken(token)){
            String userName = jwtTokenManager.getUserNameFromJWT(token);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        filterChain.doFilter(request, response);

    }


    private String getJWTFromRequest(HttpServletRequest requestIn) {
        //region VARIABLES
        String resul = null, bearerToken;

        //endregion VARIABLES


        //region ACTIONS
        bearerToken = requestIn.getHeader("Autorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            resul = bearerToken.substring(7, bearerToken.length());
        }

        //endregion ACTIONS


        // OUT
        return resul;

    }

    //endregion METHODS

}
