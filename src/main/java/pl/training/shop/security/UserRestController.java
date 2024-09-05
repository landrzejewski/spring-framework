package pl.training.shop.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequestMapping("api/users/me")
@RestController
public class UserRestController {

    @GetMapping
    public UserEntity getCurrentUser(Authentication authentication, Principal principal) {
        var userAuth = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) userAuth.getPrincipal();
    }

}
