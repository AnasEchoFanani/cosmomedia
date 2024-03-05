package com.app.cosmomedia.controller.users;

import com.app.cosmomedia.entity.Users;
import com.app.cosmomedia.service.users.UserCRUD;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserCRUDController {
    private final UserCRUD userCRUD;

    public UserCRUDController(UserCRUD userCRUD) {
        this.userCRUD = userCRUD;
    }

    @GetMapping
    public Page<Users> listOfUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return userCRUD.getUsersList(pageable);
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody Users users) {
        try {
            String result = userCRUD.addUser(users);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // Handle exceptions appropriately, log or return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding user: " + e.getMessage());
        }
    }

}
