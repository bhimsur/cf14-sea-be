package io.bhimsur.cf14seabe.service.implementation;

import io.bhimsur.cf14seabe.entity.UserProfile;
import io.bhimsur.cf14seabe.exception.DataNotFoundException;
import io.bhimsur.cf14seabe.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserProfile> userProfileOptional = userProfileRepository.getUserProfileByUserId(Integer.parseInt(username));
        if (userProfileOptional.isEmpty()) {
            throw new DataNotFoundException("user not found");
        } else {
            UserProfile userProfile = userProfileOptional.get();
            return new User(String.valueOf(userProfile.getUserId()), userProfile.getPassword(), new ArrayList<>());
        }
    }
}
