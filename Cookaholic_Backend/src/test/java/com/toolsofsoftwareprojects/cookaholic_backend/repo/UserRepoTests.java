package com.toolsofsoftwareprojects.cookaholic_backend.repo;

import com.toolsofsoftwareprojects.cookaholic_backend.model.CookaholicUser;
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
import java.util.UUID;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepoTests {


    @Autowired
    private CookaholicUserRepo cookaholicUserRepo;


    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveUser(){
        CookaholicUser cookaholicUser = CookaholicUser.builder()
                .email("daniel@a.com")
                .userTitle(CookaholicUser.UserTitle.ROLE_ADMIN)
                .name("Daniel Craig")
                .imageUrl("https://www.clipartmax.com/png/full/132-1327604_avatar-image-manager-color-icon-png.png")
                .phone("06303456789")
                .userCode(UUID.randomUUID().toString())
                .build();

        cookaholicUserRepo.save(cookaholicUser);

        Assertions.assertThat(cookaholicUser.getId()).isGreaterThan(0);
    }
    @Test
    @Order(2)
    public void getUsers(){
        CookaholicUser cookaholicUser = cookaholicUserRepo.findById(1L).get();

        Assertions.assertThat(cookaholicUser.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    public void getListOfUsersTest(){
        List<CookaholicUser> cookaholicUsers = cookaholicUserRepo.findAll();

        Assertions.assertThat(cookaholicUsers.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateUserTest(){

        CookaholicUser cookaholicUser = cookaholicUserRepo.findById(1L).get();

        cookaholicUser.setEmail("testing@gmail.com");

        CookaholicUser cookaholicUserUpdated =  cookaholicUserRepo.save(cookaholicUser);

        Assertions.assertThat(cookaholicUserUpdated.getEmail()).isEqualTo("testing@gmail.com");

    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteUserTest(){
        CookaholicUser cookaholicUser = cookaholicUserRepo.findById(1L).get();

        cookaholicUserRepo.delete(cookaholicUser);


        CookaholicUser cookaholicUser1 = null;

        Optional<CookaholicUser> optionalCookaholicUser = cookaholicUserRepo.findCookaholicUserById(1L);

        if(optionalCookaholicUser.isPresent()){
            cookaholicUser1 = optionalCookaholicUser.get();
        }

        Assertions.assertThat(cookaholicUser1).isNull();
    }





}
