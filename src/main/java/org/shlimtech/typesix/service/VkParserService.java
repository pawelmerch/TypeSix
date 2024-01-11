package org.shlimtech.typesix.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.shlimtech.typesix.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log
@RequiredArgsConstructor
public class VkParserService {

    @Value("${type-6.type-6-d-url}")
    private String type6dUrl;
    private final UserService userService;

    public void parseVk(int id) {
        log.info("Parsing vk user with id: " + id + " using url: " + type6dUrl);
        RestTemplate restTemplate = new RestTemplate();
        log.info(restTemplate.getForEntity(type6dUrl + "/task?id=" + id, String.class).toString());
    }

    @Scheduled(fixedRate = 10000)
    public void regularParsingRunner() {
        UserDTO user = userService.getRandomUser();

        if (user != null) {
            parseVk(user.getId());
        }
    }

}
