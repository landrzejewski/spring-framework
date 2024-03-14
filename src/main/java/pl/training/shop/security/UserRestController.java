package pl.training.shop.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/users/me")
@RestController
public class UserRestController {

    @GetMapping
    public UserEntity get(/*Authentication authentication, Principal principal*/) {
        var userAuthentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) userAuthentication.getPrincipal();
    }

}
