package pl.training.shop.security;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserRestController {

    @GetMapping("api/users/me")
    public UserEntity get(Authentication authentication, Principal principal) {
        // var authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();
    }

}
