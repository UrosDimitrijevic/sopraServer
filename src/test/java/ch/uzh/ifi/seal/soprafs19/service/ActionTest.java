package ch.uzh.ifi.seal.soprafs19.service;


import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class ActionTest {


    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Qualifier("gameRepository")
    @Autowired
    private GameRepository gameRepository;

    @Qualifier("actionRepository")
    @Autowired
    private GameRepository actionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Autowired
    private GameService actionService;


    //von mir hinzugefügt
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Ignore
    @Test
    public void canCreateAndSaveActions() throws Exception {

        /*

        CREAtE A GAME AND PUT PLAYER 1 IN IT

         */

    }

}
