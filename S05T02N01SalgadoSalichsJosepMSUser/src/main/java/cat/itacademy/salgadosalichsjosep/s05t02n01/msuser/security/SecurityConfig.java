package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //region ATTRIBUTES
    @Autowired
    private JWTAuthEntryPoint jwtAuthEntryPoint;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    //endregion ATTRIBUTES


    //region METHODS
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpIn) throws Exception {
        httpIn
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                    .requestMatchers("/user/auth/**").permitAll()
                    ////*.requestMatchers("/user/**").permitAll()
                    .requestMatchers("/user/**").authenticated()
                    .requestMatchers("/**").permitAll()
                    ////*.requestMatchers("/game/**").permitAll()
                    .requestMatchers("/views/game/**").permitAll()
                    .anyRequest().authenticated()
                    ////*.anyRequest().permitAll()
                .and()
                .httpBasic();

        httpIn.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpIn.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfigurationIn) throws Exception {
        return authenticationConfigurationIn.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

    //endregion METHODS

}
