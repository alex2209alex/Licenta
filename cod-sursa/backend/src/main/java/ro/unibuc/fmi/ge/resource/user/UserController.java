package ro.unibuc.fmi.ge.resource.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.ge.shared.UserHelper;

import java.util.Map;

@RestController
@RequestMapping("/utilizatori")
public class UserController {
    private final UserHelper userHelper;

    public UserController(UserHelper userHelper) {
        this.userHelper = userHelper;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public Map<String, String> getUserName() {
        return Map.of("userName", userHelper.getUserName());
    }
}
