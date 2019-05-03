package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.ActionRepository;
import ch.uzh.ifi.seal.soprafs19.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class complexGameMoving {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Qualifier("gameRepository")
    @Autowired
    private GameRepository gameRepository;

    @Qualifier("actionRepository")
    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Autowired
    private ActionService actionService;

    //von mir hinzugefügt
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private long player1id;
    private long player2id;
    private long gameId;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        //board setUp
        User testUser1 = new User();
        testUser1.setUsername("complexTestUsername1");
        testUser1.setPassword("complexTestPassowrd");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("complextestUsername2");
        testUser2.setPassword("complextestPassowrd");
        testUser2 = userService.createUser(testUser2);

        Game testGame = new Game(testUser1, testUser2 );
        testUser1 = userService.createUser(testUser1);
        testUser2 = userService.createUser(testUser2);

        //setting up game mode
        testGame.setStatus(GameStatus.MOVING_STARTINGPLAYER);

        //setting the figurines
        testGame.getStartingPlayer().getFigurine1().setPosition(1,1);
        testGame.getStartingPlayer().getFigurine2().setPosition(3,1);
        testGame.getNonStartingPlayer().getFigurine1().setPosition(1,3);
        testGame.getNonStartingPlayer().getFigurine1().setPosition(3,3);

        gameRepository.save(testGame);
        this.player1id = testUser1.getId();
        this.player2id = testUser2.getId();
        this.gameId = testGame.getId();

    }

    @Test
    public void canCreateUserFromServerAndStore() throws Exception{
        User newUser = new User();
        newUser.setStatus(UserStatus.OFFLINE);

        return;
    }
}
