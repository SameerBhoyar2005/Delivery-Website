package com.DelhiveryApplication.DelhiveryApplication.Service;

import com.DelhiveryApplication.DelhiveryApplication.Data.User;
import com.DelhiveryApplication.DelhiveryApplication.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public @NotNull UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByEmail(username);
        if (userEntity != null){
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userEntity.getEmail())
                    .password(userEntity.getPassword())
                    .authorities("ROLE_USER")
                    .build();
        }
        throw new UsernameNotFoundException("User not found");
    }
}
