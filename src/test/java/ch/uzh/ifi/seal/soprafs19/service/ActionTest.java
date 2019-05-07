package ch.uzh.ifi.seal.soprafs19.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;

import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Apollo;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Artemis;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Athena;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Pan;
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
    public void testBuilding() throws Throwable{
        User testUser1 = new User();
        testUser1.setUsername("testUsernamesfgnsffgn");
        testUser1.setPassword("testPasswordplrtohk");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernamebxccbeqeth");
        testUser2.setPassword("testPasswordasctbuz2");
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
        gameService.saveGame(testGame2);
        testGame2.setStatus(GameStatus.CHOSING_GODCARDS);
        Athena ath = new Athena(testGame2);
        Artemis art = new Artemis(testGame2);
        ChooseGod chGod = new ChooseGod(testGame2, ath,art);
        chGod.perfromAction(gameService);
        testGame2 = gameService.gameByID(testGame2.getId());
        assertNotNull(testGame2.retrivePlayers()[0].getAssignedGod());
    }

    @Test
    public void testPlaceWorker() throws Throwable{
        User testUser1 = new User();
        testUser1.setUsername("testUsernamesdfvewt");
        testUser1.setPassword("testPassowrdqwrvqfvq");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernamectionqreveqrvreq");
        testUser2.setPassword("testPassowrd");
        testUser2 = userService.createUser(testUser2);

        Game testGame3 = new Game(testUser1, testUser2 );
        gameService.saveGame(testGame3);
        testGame3.setStatus(GameStatus.SettingFigurinesp1f1);
        PlaceWorker  placing = new PlaceWorker(testGame3,testGame3.retrivePlayers()[0].getFigurine1(),0,1);
        placing.perfromAction(gameService);
        testGame3 = gameService.gameByID(testGame3.getId());
        assertFalse(testGame3.getBoard().isEmpty(0,1));
    }

    @Test
    public void testPlaceWorker2() throws Throwable{
        User testUser1 = new User();
        testUser1.setUsername("testUsernameasCascaSC");
        testUser1.setPassword("testPassowrdasceqcwce a");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernameasdcqwrf");
        testUser2.setPassword("testPassowrdwfsadca");
        testUser2 = userService.createUser(testUser2);

        Game testGame4 = new Game(testUser1, testUser2 );
        gameService.saveGame(testGame4);
        testGame4.setStatus(GameStatus.SettingFigurinesp1f2);
        PlaceWorker  placing = new PlaceWorker(testGame4,testGame4.retrivePlayers()[0].getFigurine2(),0,2);
        placing.perfromAction(gameService);
        testGame4 = gameService.gameByID(testGame4.getId());
        assertFalse(testGame4.getBoard().isEmpty(0,2));
    }

    @Test
    public void testMoving() throws Throwable{
        User testUser1 = new User();
        testUser1.setUsername("testUsernamepeqirnl");
        testUser1.setPassword("testPassowrdnuvenqvl");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernamelqierngzulev");
        testUser2.setPassword("testPassowrdkeqrubh");
        testUser2 = userService.createUser(testUser2);

        Game testGame5 = new Game(testUser1, testUser2 );
        gameService.saveGame(testGame5);
        PlaceWorker  placing = new PlaceWorker(testGame5,testGame5.retrivePlayers()[0].getFigurine1(),0,1);
        PlaceWorker2  placing1 = new PlaceWorker2(testGame5,testGame5.retrivePlayers()[0].getFigurine2(),0,4);
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
        testGame5 = gameService.gameByID(testGame5.getId());
        assertFalse(testGame5.getBoard().isEmpty(0,2));
    }

    @Test
    public void canChoseYourGod() throws Exception {
        User testUser1 = new User();
        testUser1.setUsername("testUsernameAction1ChoseYourGod");
        testUser1.setPassword("testPassowrdAction2");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernamection2ChoseYourGod");
        testUser2.setPassword("testPassowrd");
        testUser2 = userService.createUser(testUser2);

        Game testGame2 = new Game(testUser1, testUser2 );
        testGame2.setStatus(GameStatus.PICKING_GODCARDS);
        testGame2.getStartingPlayer().setAssignedGod(new Apollo(testGame2) );
        testGame2.getNonStartingPlayer().setAssignedGod(new Pan(testGame2) );

        //test if stay the same
        gameService.saveGame(testGame2);
        ChoseYourGod pickGod = new ChoseYourGod(testGame2,true);
        pickGod.perfromAction(gameService);
        testGame2 = gameService.gameByID(testGame2.getId());
        Assert.assertEquals("Apollo", testGame2.getNonStartingPlayer().getAssignedGod().getName());
        Assert.assertEquals("Pan", testGame2.getStartingPlayer().getAssignedGod().getName());

        //test if switch
        testGame2.setStatus(GameStatus.PICKING_GODCARDS);
        gameService.saveGame(testGame2);
        pickGod = new ChoseYourGod(testGame2,false);
        pickGod.perfromAction(gameService);
        testGame2 = gameService.gameByID(testGame2.getId());
        Assert.assertEquals("Apollo", testGame2.getNonStartingPlayer().getAssignedGod().getName());
        Assert.assertEquals("Pan", testGame2.getStartingPlayer().getAssignedGod().getName());
    }


    @Test
    public void canEndGame() throws Exception {
        User testUser1 = new User();
        testUser1.setUsername("testUsernameAction1EndGameGod");
        testUser1.setPassword("testPassowrdAction2");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernamection2EndGameGod");
        testUser2.setPassword("testPassowrd");
        testUser2 = userService.createUser(testUser2);

        testUser1.setChallenging(testUser2.getId());

        userService.updateProfile(testUser1,testUser1.getId(),gameService);
        testUser2 = userService.userByID(testUser2.getId());
        testUser2.setChallenging(testUser1.getId());
        userService.updateProfile(testUser2,testUser2.getId(),gameService);
        testUser1 = userService.userByID(testUser1.getId());
        testUser2 = userService.userByID(testUser2.getId());

        Game testGame2 = gameService.gameByPlaxerId(testUser1.getId());
        testGame2.setStatus(GameStatus.STARTINGPLAYER_WON);

        //end pl1
        gameService.saveGame(testGame2);
        Action endGame = new endTheGame(testGame2,1);
        endGame.perfromAction(gameService);
        testGame2 = gameService.gameByID(testGame2.getId());
        testUser1 = userService.userByID(testUser1.getId());
        testUser2 = userService.userByID(testUser2.getId());

        Assert.assertNull(testUser1.getGettingChallengedBy());
        Assert.assertNull(testUser2.getGettingChallengedBy());
        Assert.assertNull(testUser1.getChallenging());
        Assert.assertNull(testUser2.getChallenging());
        Assert.assertNull(testUser1.getChallenging());

        //end pl2
        gameService.saveGame(testGame2);
        endGame = new endTheGame(testGame2,2);
        endGame.perfromAction(gameService);
        testGame2 = gameService.gameByID(testGame2.getId());

        //Assert.assertNull();
        Assert.assertNull(testGame2);
    }
}
