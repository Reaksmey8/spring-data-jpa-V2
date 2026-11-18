package co.istad.springdatajpa.security;

import co.istad.springdatajpa.domain.Role;
import co.istad.springdatajpa.domain.User;
import co.istad.springdatajpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO:
        // find user from DB
        User foundUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found"
                ));

        // convert user to spring's user
        return org.springframework.security.core.userdetails.User.builder()
                .username(foundUser.getUsername())
                .password(foundUser.getEncryptedPassword())
                .roles(
                        // ROLE_ADMIN, ROLE_BUSINESS, ROLE_CUSTOMER
                        foundUser.getRoles().stream()
                                .map(Role::getName)
                                .collect(Collectors.joining(" "))
                )
                .build();
    }
}
