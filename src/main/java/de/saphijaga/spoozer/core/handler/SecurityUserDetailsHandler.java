package de.saphijaga.spoozer.core.handler;

import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.persistence.domain.User;
import de.saphijaga.spoozer.persistence.service.UserPersistenceService;
import de.saphijaga.spoozer.web.details.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityUserDetailsHandler implements UserDetailsService {
    @Autowired
    private UserPersistenceService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isPresent()) {
            return new SecurityUserDetails(user.get().getUsername(), user.get().getPassword());
        } else {
            throw new UsernameNotFoundException(username);
        }
    }


}