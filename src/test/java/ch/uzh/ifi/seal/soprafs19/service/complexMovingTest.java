package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
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
public class complexMovingTest {



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

    long user1Id;
    long user2Id;
    long gameId;

    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        User testUser1 = new User();
        testUser1.setUsername("complexTestUsernameAction1");
        testUser1.setPassword("testPassowrdAction2");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);
        this.user1Id = testUser1.getId();

        User testUser2 = new User();
        testUser2.setUsername("complexTestUsernamection2");
        testUser2.setPassword("testPassowrd");
        testUser2 = userService.createUser(testUser2);

        Game testGame = new Game(testUser1, testUser2 );
        this.user2Id = testUser2.getId();

        //setup the game
        testGame.setStatus(GameStatus.MOVING_NONSTARTINGPLAYER);
        testGame.getStartingPlayer().getFigurine1().setPosition(1,1);
        testGame.getStartingPlayer().getFigurine1().setPosition(3,1);
        testGame.getNonStartingPlayer().getFigurine2().setPosition(1,3);
        testGame.getNonStartingPlayer().getFigurine2().setPosition(3,3);
        testGame.setPlayWithGodCards(false);

        this.gameService.saveGame(testGame);

        this.gameId = testGame.getId();
    }

    @Test
    public void canMove() throws Exception {

        /*

        CREAtE A GAME AND PUT PLAYER 1 IN IT

         */
    }

}
