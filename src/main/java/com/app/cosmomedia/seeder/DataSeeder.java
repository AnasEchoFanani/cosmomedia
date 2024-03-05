package com.app.cosmomedia.seeder;

import com.app.cosmomedia.entity.Users;
import com.app.cosmomedia.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (usersRepository.count() == 0) {
            // Create and save three default users
            Users user1 = Users.builder()
                    .CIN("BW4297")
                    .firstName("Anas")
                    .lastName("Fanani")
                    .email("anas.fanani.pro@gmail.com")
                    .password(passwordEncoder.encode("AdminAnas123@@"))
                    .role("ADMIN")
                    .createdAt(new Date())
                    .modifiedAt(new Date())
                    .build();

            Users user2 = Users.builder()
                    .CIN("BW4258")
                    .firstName("Youssef")
                    .lastName("Salih")
                    .email("youssef-salih@cosmomedia.ma")
                    .password(passwordEncoder.encode("AdminYoussef123@@"))
                    .role("ADMIN")
                    .createdAt(new Date())
                    .modifiedAt(new Date())
                    .build();

            Users user3 = Users.builder()
                    .CIN("AZ4297")
                    .firstName("Najib")
                    .lastName("El Machmachi")
                    .email("najib-el-machmachi@cosmomedia.ma")
                    .password(passwordEncoder.encode("AdminNajib123@@"))
                    .role("ADMIN")
                    .createdAt(new Date())
                    .modifiedAt(new Date())
                    .build();

            // Save the users to the database
            usersRepository.saveAll(Arrays.asList(user1, user2, user3));
        }
    }
}
