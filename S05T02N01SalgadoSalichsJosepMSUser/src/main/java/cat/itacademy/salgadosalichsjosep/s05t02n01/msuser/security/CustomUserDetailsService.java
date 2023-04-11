package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.security;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.entity.RoleEntity;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.entity.UserEntity;
import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    //region ATTRIBUTES
    @Autowired
    private UserRepository userRepository;

    //endregion ATTRIBUTES


    //region METHODS: PUBLIC
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //region VARIABLES
        UserEntity user;

        //endregion VARIABLES


        //region ACTIONS
        user = userRepository.findByUserName(username);
        //todo s'ha de fer alguna cosa x llançar el UsernameNotFoundException

        //endregion ACTIONS


        // OUT
        return new User(user.getUserName(), user.getPass(), mapRolesToAuthorities(user.getRoles()));

    }

    //endregion METHODS: PUBLIC


    //region METHODS: PRIVATE
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<RoleEntity> rolesIn){
        return rolesIn.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    //endregion METHODS: PRIVATE

}
