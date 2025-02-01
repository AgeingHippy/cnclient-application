package com.ageinghippy.cnclient_application.service;

import com.ageinghippy.cnclient_application.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class UserService {

    @LoadBalanced
    private final RestTemplate restTemplate;

    private final PasswordEncoder passwordEncoder;

    public User getUser(String userName) {
        ResponseEntity<User> response =
                restTemplate.getForEntity("http://CN-USER-MICROSERVICE/user/username/" + userName, User.class);
        if (response.getStatusCode().is2xxSuccessful()
                && response.getBody() != null) {
            return response.getBody();
        } else {
            //todo throw exception
            return null;
        }
    }

    public User createUser(User user) {
        //todo - validate user details
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        ResponseEntity<User> response =
                restTemplate.postForEntity("http://CN-USER-MICROSERVICE/user", user, User.class);
        if (response.getStatusCode().is2xxSuccessful()
                && response.getBody() != null) {
            return response.getBody();
        } else {
            //todo throw exception
            return null;
        }
    }

}
