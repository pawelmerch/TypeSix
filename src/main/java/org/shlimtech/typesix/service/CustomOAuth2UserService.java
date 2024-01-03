package org.shlimtech.typesix.service;

import lombok.RequiredArgsConstructor;
import org.shlimtech.typesix.dto.UserDTO;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        UserDTO userDTO = new UserDTO();
        Map<String, Object> attributes = user.getAttributes();
        String email = user.getName();

        userDTO.setEmail(email);

        analyzeGithub(userDTO, attributes);
        analyzeYandex(userDTO, attributes);
        analyzeVK(userDTO, attributes);

        userService.createOrComplementUser(userDTO);
        return user;
    }

    private <T> void process(String key, Consumer<T> cons, Map<String, Object> attributes) {
        if (attributes.containsKey(key)) {
            cons.accept((T) attributes.get(key));
        }
    }

    private void analyzeYandex(UserDTO userDTO, Map<String, Object> attributes) {
        process("login", userDTO::setLogin, attributes);
        process("first_name", userDTO::setFirstName, attributes);
        process("last_name", userDTO::setLastName, attributes);
        process("birthday", userDTO::setBirthday, attributes);
    }

    private void analyzeGithub(UserDTO userDTO, Map<String, Object> attributes) {
        process("login", userDTO::setLogin, attributes);
        process("name", userDTO::setFirstName, attributes);
        process("bio", userDTO::setBiography, attributes);
        process("url", userDTO::setGithubLink, attributes);
    }

    private void analyzeVK(UserDTO userDTO, Map<String, Object> attributes) {

    }

}