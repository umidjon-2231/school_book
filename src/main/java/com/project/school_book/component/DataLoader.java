package com.project.school_book.component;

import com.project.school_book.entity.Group;
import com.project.school_book.entity.User;
import com.project.school_book.entity.enums.Role;
import com.project.school_book.repository.GroupRepository;
import com.project.school_book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    @Value("${spring.sql.init.mode}")
    private String intMode;
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final GroupRepository groupRepository;

    @Override
    public void run(String... args) {
        if (intMode.equalsIgnoreCase("always")) {
            userRepository.save(User.builder()
                            .username("admin")
                            .password(passwordEncoder.encode("1234"))
                            .role(Role.ADMIN)
                    .build());
            for (int i = 1; i <= 11; i++) {
                groupRepository.save(Group.builder()
                                .classNumber(i)
                        .build());
            }
        }
    }
}
