package ru.gb.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.auth.dto.UserDtoRequest;
import ru.gb.auth.entities.Role;
import ru.gb.auth.entities.User;
import ru.gb.auth.exceptions.UserAlreadyExistException;
import ru.gb.auth.repositories.RoleRepository;
import ru.gb.auth.repositories.UserRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    @Transactional
    public Optional<User> registerNewUserAccount(final UserDtoRequest userDto) {
        return Optional.ofNullable(userDto.getUsername()).filter(Predicate.not(userNameExists())).map(name -> {
            final User user = new User();
            user.setUsername(userDto.getUsername());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setEmail(userDto.getEmail());
            user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER").get()));

            return Optional.ofNullable(userRepository.save(user));
        }).orElseThrow(() -> new UserAlreadyExistException(userDto.getUsername()));
    }

    private Predicate<String> userNameExists() {
        return name -> userRepository.existsByUsername(name);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
