import com.toolsofsoftwareprojects.cookaholic_backend.model.CookaholicUser;
import com.toolsofsoftwareprojects.cookaholic_backend.repo.CookaholicUserRepo;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

@RunWith( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTests{

    @Autowired
    private CookaholicUserRepo cookaholicUserRepo;

    @Autowired
    private MockMvc mockMvc;

    // given/when/then format - BDD style
    @Test
    public void givenUsers_whenGetAllUser_thenListOfUsers() throws Exception {
        // given - setup or precondition
        List<CookaholicUser> users =
                List.of(CookaholicUser.builder()
                            .email("daniel@a.com")
                            .userTitle("JavaScript")
                            .name("Daniel Craig")
                            .imageUrl("https://www.clipartmax.com/png/full/132-1327604_avatar-image-manager-color-icon-png.png")
                            .phone("06303456789")
                            .userCode(UUID.randomUUID().toString())
                            .build(),
                        CookaholicUser.builder()
                            .email("thomas@a.com")
                            .userTitle("BackEnd")
                            .name("Thomas Craig")
                            .imageUrl("https://www.clipartmax.com/png/full/132-1327604_avatar-image-manager-color-icon-png.png")
                            .phone("06303456789")
                            .userCode(UUID.randomUUID().toString())
                            .build());
        cookaholicUserRepo.saveAll(users);

        // when - action
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/CookaholicUser/find/all"));

        // then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(users.size())));
    }

}