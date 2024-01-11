package org.shlimtech.typesix.debug;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DebugController {

    @GetMapping("/test")
    public ResponseEntity<?> testType6d() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForEntity("http://10.96.169.230:443/task", String.class);
        return ResponseEntity.ok().build();
    }

}
