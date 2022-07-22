package com.example.app.TestRestUserH2.repository;

import com.example.app.TestRestUserH2.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

// Spring boot предоставляет аннотацию @DataJpaTest.
// Эта аннотация отключит полную автоматическую настройку
// и вместо этого применит только конфигурацию, относящуюся к
// тестам JPA. По умолчанию она будет использовать встроенную в памяти
// базу данных H2, для более быстрого выполнения теста.
//
// Аннотация @DataJpaTest не загружает другие компоненты Spring
// (@Component, @Controller, @Service) в ApplicationContext.
// Нужно указать порядок выполнения, потому что JUnit
// не запускает тестовые методы в том порядке, в котором
// они появляются в коде. Поэтому нужно использовать аннотации
// @TestMethodOrder и @Order для выполнения тестовых случаев в
// порядке возрастания.

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveUserTest() {

        User user = User.builder()
                .name("George")
                .email("manager")
                .phone("555 321-9876")
                .build();

        userRepository.save(user);

        Assertions.assertThat(user.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getUserByIdTest() {

        User user = userRepository.findById(1).get();

        Assertions.assertThat(user.getId()).isEqualTo(1);
    }

    @Test
    @Order(3)
    public void getListOfUsersTest() {

        List<User> users = userRepository.findAll();

        Assertions.assertThat(users.size()).isGreaterThan(0);
    }


    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateUserTest() {

        User user = userRepository.findById(1).get();

        user.setPhone("555 781-1234");

        User userUpdated = userRepository.save(user);

        Assertions.assertThat(userUpdated.getPhone()).isEqualTo("555 781-1234");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteUserTest() {

        User user = userRepository.findById(1).get();

        userRepository.delete(user);

        User user1 = null;

        Optional<User> optionalUser = userRepository.findByPhone("555 781-1234");

        if (optionalUser.isPresent()) {
            user1 = optionalUser.get();
        }

        Assertions.assertThat(user1).isNull();
    }

}
