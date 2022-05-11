package com.toolsofsoftwareprojects.cookaholic_backend.service;

import com.toolsofsoftwareprojects.cookaholic_backend.model.CookaholicUser;
import com.toolsofsoftwareprojects.cookaholic_backend.repo.CookaholicUserRepo;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTests{

    @Autowired
    private CookaholicUserRepo cookaholicUserRepo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Adding 2 Guest User with encoding the password, and an Admin. After that it checks the authentication,
     * create the token, and asking for all the user finding in the db with rest api call
     * @throws Exception
     */
    @Test
    @Order(0)
    @Rollback(value = false)
    public void testThePasswordEncoding_TheAdminRegistration_AndWithAuthenticationFindAllUserInRepo() throws Exception {
        // given - setup or precondition
        List<CookaholicUser> users =
                List.of(CookaholicUser.builder()
                            .email("daniel@a.com")
                            .userTitle(CookaholicUser.UserTitle.ROLE_USER)
                            .name("Daniel Craig")
                            .imageUrl("https://www.clipartmax.com/png/full/132-1327604_avatar-image-manager-color-icon-png.png")
                            .phone("06303456789")
                            .userCode(UUID.randomUUID().toString())
                            .password(passwordEncoder.encode("daniel"))
                            .username("daniel")
                            .build(),
                        CookaholicUser.builder()
                            .email("thomas@a.com")
                            .userTitle(CookaholicUser.UserTitle.ROLE_USER)
                            .name("Thomas Craig")
                            .imageUrl("https://www.clipartmax.com/png/full/132-1327604_avatar-image-manager-color-icon-png.png")
                            .phone("06303456789")
                            .userCode(UUID.randomUUID().toString())
                            .password(passwordEncoder.encode("thomas"))
                            .username("thomas")
                            .build());
        cookaholicUserRepo.saveAll(users);





        String stringJson=("{\n" +
                "    \"name\" : \"admin\",\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"admin\",\n" +
                "    \"email\" : \"admin@gmail.com\",\n" +
                "    \"phone\" : \"+36706763701\",\n" +
                "    \"imageUrl\" : \"admin\",\n" +
                "    \"userCode\" : \" 6969\"\n" +
                "}");

        mockMvc.perform(
                post("http://localhost:8080/CookaholicUser/register/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stringJson))
                .andExpect(status().isCreated())
                .andReturn();


        String stringJson2=("{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"admin\"\n" +
                "}");

        MvcResult result = this.mockMvc.perform(
                post("http://localhost:8080/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stringJson2)).andDo(print()).andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString().substring(10,(result.getResponse().getContentAsString().length()-2));







        mockMvc.perform(get("/CookaholicUser/find/all")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(cookaholicUserRepo.findAll().size())))
                .andReturn();

    }

    @Test
    @Order(1)
    public void givenNoToken_whenGetSecureRequest_thenIsForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/CookaholicUser/find/all"))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void deleteCheck() throws Exception {

        String stringJson2=("{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"admin\"\n" +
                "}");

        MvcResult result = this.mockMvc.perform(
                post("http://localhost:8080/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stringJson2)).andDo(print()).andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString().substring(10,(result.getResponse().getContentAsString().length()-2));

        MvcResult result1 = mockMvc.perform(delete("/CookaholicUser/delete")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(cookaholicUserRepo.findAll().size())))
                .andReturn();
    }





}