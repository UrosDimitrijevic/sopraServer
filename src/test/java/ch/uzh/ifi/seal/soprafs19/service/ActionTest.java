package ch.uzh.ifi.seal.soprafs19.service;


import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.ActionRepository;
import ch.uzh.ifi.seal.soprafs19.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.junit.Assert;
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

    @Test
    public void canCreateAndSaveActions() throws Exception {

        /*

        CREAtE A GAME AND PUT PLAYER 1 IN IT

         */
    }


    @Test
    public void canGetGocCards() throws Exception {
        User testUser1 = new User();
        testUser1.setUsername("testUsernameAction1");
        testUser1.setPassword("testPassowrdAction2");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernamection2");
        testUser2.setPassword("testPassowrd");
        testUser2 = userService.createUser(testUser2);

        Game testGame = new Game(testUser1, testUser2 );

        testGame.setStatus(GameStatus.CHOSING_GODCARDS);
        long id2 = 999;

        this.gameService.saveGame(testGame);

        long id = testGame.getId();

        Game retrivedGame = this.gameService.gameByID(id);

        retrivedGame.getPossibleActions(testUser1.getId());
        retrivedGame.getPossibleActions(testUser2.getId());
    }

    @Test
    public void canPlaceWorker() throws Exception {
        User testUser1 = new User();
        testUser1.setUsername("testUsernameAction1");
        testUser1.setPassword("testPassowrdAction2");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernamection2");
        testUser2.setPassword("testPassowrd");
        testUser2 = userService.createUser(testUser2);

        Game testGame = new Game(testUser1, testUser2 );

        testGame.setStatus(GameStatus.SettingFigurinesp1f1);
        long id2 = 999;

        this.gameService.saveGame(testGame);

        long id = testGame.getId();

        Game retrivedGame = this.gameService.gameByID(id);

        retrivedGame.getPossibleActions(testUser1.getId());
        retrivedGame.getPossibleActions(testUser2.getId());

    }



















}
