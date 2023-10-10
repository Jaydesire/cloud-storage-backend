package com.cloud.cloudstorage;

import com.cloud.cloudstorage.entity.Users;
import com.cloud.cloudstorage.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
//https://howtodoinjava.com/spring-boot/command-line-runner-interface-example/
public class ApplicationStartupRunner implements CommandLineRunner {

    protected final Log logger = LogFactory.getLog(this.getClass());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        logger.info("ApplicationStartupRunner run method Started");
        userRepository.save(Users.builder().login("root").password(passwordEncoder.encode("root")).build());
        userRepository.save(Users.builder().login("elgovsky@yandex.ru").password(passwordEncoder.encode("1234")).build());
    }
}
