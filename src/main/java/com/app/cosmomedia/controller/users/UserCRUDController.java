package com.app.cosmomedia.controller.users;

import com.app.cosmomedia.entity.Users;
import com.app.cosmomedia.service.users.UserCRUD;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserCRUDController {
    private final UserCRUD userCRUD;

    @GetMapping
    public Page<Users> listOfUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return userCRUD.getUsersList(pageable);
    }

    @PostMapping
    public String addUser(@RequestBody Users users) throws MessagingException, IOException {
        return userCRUD.addUser(users);
    }
}
