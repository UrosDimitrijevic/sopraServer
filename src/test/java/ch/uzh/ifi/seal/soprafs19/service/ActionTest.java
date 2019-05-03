package ch.uzh.ifi.seal.soprafs19.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;

import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Artemis;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Athena;
import ch.uzh.ifi.seal.soprafs19.entity.actions.*;
import org.junit.Test;

import org.junit.*;
import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.ActionRepository;
import ch.uzh.ifi.seal.soprafs19.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void canCreateAndSaveActions() throws Exception {

        /*

        CREAtE A GAME AND PUT PLAYER 1 IN IT

         */
    }


    @Test
    public void testBuilding() throws Throwable{
        User testUser1 = new User();
        testUser1.setUsername("testUsername1");
        testUser1.setPassword("testPassword1");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsername2");
        testUser2.setPassword("testPassword2");
        testUser2 = userService.createUser(testUser2);

        Game testGame1 = new Game(testUser1, testUser2 );
        gameService.saveGame(testGame1);

        testGame1.setStatus(GameStatus.BUILDING_STARTINGPLAYER);
        Building tower = new Building(testGame1,0,4);// (0,4) randomly chosen
        tower.perfromAction(gameService);

        testGame1 = gameService.gameByID(testGame1.getId());
        assertEquals(testGame1.getBoard().getSpaces()[0][4].getLevel(),1);
        //assertFalse(testGame1.getBoard().isEmpty(0,4));
    }

    @Test
    public void testChooseGodCards() throws Throwable{
        User testUser1 = new User();
        testUser1.setUsername("testUsernameAction1");
        testUser1.setPassword("testPassowrdAction2");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernamection2");
        testUser2.setPassword("testPassowrd");
        testUser2 = userService.createUser(testUser2);

        Game testGame2 = new Game(testUser1, testUser2 );
        testGame2.setStatus(GameStatus.CHOSING_GODCARDS);
        Athena ath = new Athena(testGame2);
        Artemis art = new Artemis(testGame2);
        ChooseGod chGod = new ChooseGod(testGame2, ath,art);
        chGod.perfromAction(gameService);
        assertNotNull(testGame2.retrivePlayers()[0].getAssignedGod());

    }

    @Test
    public void testPlaceWorker() throws Throwable{
        User testUser1 = new User();
        testUser1.setUsername("testUsernameAction1");
        testUser1.setPassword("testPassowrdAction2");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernamection2");
        testUser2.setPassword("testPassowrd");
        testUser2 = userService.createUser(testUser2);

        Game testGame3 = new Game(testUser1, testUser2 );
        testGame3.setStatus(GameStatus.SettingFigurinesp1f1);
        PlaceWorker  placing = new PlaceWorker(testGame3,testGame3.retrivePlayers()[0].getFigurine1(),0,1);
        placing.perfromAction(gameService);
        assertFalse(testGame3.getBoard().isEmpty(0,1));

    }

    @Test
    public void testPlaceWorker2() throws Throwable{
        User testUser1 = new User();
        testUser1.setUsername("testUsernameAction1");
        testUser1.setPassword("testPassowrdAction2");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernamection2");
        testUser2.setPassword("testPassowrd");
        testUser2 = userService.createUser(testUser2);

        Game testGame4 = new Game(testUser1, testUser2 );
        testGame4.setStatus(GameStatus.SettingFigurinesp1f2);
        PlaceWorker  placing = new PlaceWorker(testGame4,testGame4.retrivePlayers()[0].getFigurine2(),0,2);
        placing.perfromAction(gameService);
        assertFalse(testGame4.getBoard().isEmpty(0,2));

    }

    @Test
    public void testMoving() throws Throwable{
        User testUser1 = new User();
        testUser1.setUsername("testUsernameAction1");
        testUser1.setPassword("testPassowrdAction2");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernamection2");
        testUser2.setPassword("testPassowrd");
        testUser2 = userService.createUser(testUser2);

        Game testGame5 = new Game(testUser1, testUser2 );
        PlaceWorker  placing = new PlaceWorker(testGame5,testGame5.retrivePlayers()[0].getFigurine1(),0,1);
        PlaceWorker2  placing1 = new PlaceWorker2(testGame5,testGame5.retrivePlayers()[0].getFigurine2(),0,5);
        PlaceWorker  placing2 = new PlaceWorker(testGame5,testGame5.retrivePlayers()[1].getFigurine1(),4,0);
        PlaceWorker2  placing3 = new PlaceWorker2(testGame5,testGame5.retrivePlayers()[1].getFigurine2(),4,3);
        placing.perfromAction(gameService);
        placing1.perfromAction(gameService);
        placing2.perfromAction(gameService);
        placing3.perfromAction(gameService);
        testGame5.setStatus(GameStatus.MOVING_STARTINGPLAYER);
        Figurine figurine = testGame5.retrivePlayers()[0].retirveFigurines()[0];

        Moving move = new Moving(testGame5,figurine,0,2);
        move.perfromAction(gameService);
        assertFalse(testGame5.getBoard().isEmpty(0,2));

    }



    @Test
    public void canGetGocCards() throws Exception {
        User testUser1 = new User();
        testUser1.setUsername("testUsernameAction1qejpgo");
        testUser1.setPassword("testPassowrdAction2");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernamection2einbü");
        testUser2.setPassword("testPassowrd");
        testUser2 = userService.createUser(testUser2);

        Game testGame = new Game(testUser1, testUser2 );

        testGame.setStatus(GameStatus.CHOSING_GODCARDS);
        long id2 = 999;

        this.gameService.saveGame(testGame);

        long id = testGame.getId();

        Game retrivedGame = this.gameService.gameByID(id);

        //retrivedGame.getPossibleActions(testUser1.getId());
        //retrivedGame.getPossibleActions(testUser2.getId());

        this.mockMvc.perform(get("/users/{id}",testUser1.getId() )).andExpect(status().isOk() );
        this.mockMvc.perform(get("/users/{id}",testUser2.getId() )).andExpect(status().isOk() );
    }

}
