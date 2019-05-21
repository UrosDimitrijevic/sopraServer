package ch.uzh.ifi.seal.soprafs19.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;

import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.*;
import ch.uzh.ifi.seal.soprafs19.entity.Player;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    public void testBuilding() throws Throwable {
        User testUser1 = new User();
        testUser1.setUsername("testUsernamesfgnsffgn");
        testUser1.setPassword("testPasswordplrtohk");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernamebxccbeqeth");
        testUser2.setPassword("testPasswordasctbuz2");
        testUser2 = userService.createUser(testUser2);

        Game testGame1 = new Game(testUser1, testUser2);
        gameService.saveGame(testGame1);

        testGame1.setStatus(GameStatus.BUILDING_STARTINGPLAYER);
        Building tower = new Building(testGame1, 0, 4);// (0,4) randomly chosen
        tower.perfromAction(gameService);

        testGame1 = gameService.gameByID(testGame1.getId());
        assertEquals(testGame1.getBoard().getSpaces()[0][4].getLevel(), 1);
        //assertFalse(testGame1.getBoard().isEmpty(0,4));
    }

    @Test
    public void testChooseGodCards() throws Throwable {
        User testUser1 = new User();
        testUser1.setUsername("testUsernameAction1");
        testUser1.setPassword("testPassowrdAction2");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernamection2");
        testUser2.setPassword("testPassowrd");
        testUser2 = userService.createUser(testUser2);

        Game testGame2 = new Game(testUser1, testUser2);
        gameService.saveGame(testGame2);
        testGame2.setStatus(GameStatus.CHOSING_GODCARDS);
        Athena ath = new Athena(testGame2);
        Artemis art = new Artemis(testGame2);
        ChooseGod chGod = new ChooseGod(testGame2, ath, art);
        chGod.perfromAction(gameService);
        testGame2 = gameService.gameByID(testGame2.getId());
        assertNotNull(testGame2.retrivePlayers()[0].getAssignedGod());
    }

    @Test
    public void testPlaceWorker() throws Throwable {
        User testUser1 = new User();
        testUser1.setUsername("testUsernamesdfvewt");
        testUser1.setPassword("testPassowrdqwrvqfvq");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernamectionqreveqrvreq");
        testUser2.setPassword("testPassowrd");
        testUser2 = userService.createUser(testUser2);

        Game testGame3 = new Game(testUser1, testUser2);
        gameService.saveGame(testGame3);
        testGame3.setStatus(GameStatus.SettingFigurinesp1f1);
        PlaceWorker placing = new PlaceWorker(testGame3, testGame3.retrivePlayers()[0].getFigurine1(), 0, 1);
        placing.perfromAction(gameService);
        testGame3 = gameService.gameByID(testGame3.getId());
        assertFalse(testGame3.getBoard().isEmpty(0, 1));
    }

    @Test
    public void testPlaceWorker2() throws Throwable {
        User testUser1 = new User();
        testUser1.setUsername("testUsernameasCascaSC");
        testUser1.setPassword("testPassowrdasceqcwce a");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernameasdcqwrf");
        testUser2.setPassword("testPassowrdwfsadca");
        testUser2 = userService.createUser(testUser2);

        Game testGame4 = new Game(testUser1, testUser2);
        gameService.saveGame(testGame4);
        testGame4.setStatus(GameStatus.SettingFigurinesp1f2);
        PlaceWorker placing = new PlaceWorker(testGame4, testGame4.retrivePlayers()[0].getFigurine2(), 0, 2);
        placing.perfromAction(gameService);
        testGame4 = gameService.gameByID(testGame4.getId());
        assertFalse(testGame4.getBoard().isEmpty(0, 2));
    }

    @Test
    public void testMoving() throws Throwable {
        User testUser1 = new User();
        testUser1.setUsername("testUsernameTestMoving1");
        testUser1.setPassword("testPassowrdnuvenqvl");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernameTestMoving2");
        testUser2.setPassword("testPassowrdkeqrubh");
        testUser2 = userService.createUser(testUser2);

        Game testGame5 = new Game(testUser1, testUser2);
        gameService.saveGame(testGame5);
        PlaceWorker placing = new PlaceWorker(testGame5, testGame5.retrivePlayers()[0].getFigurine1(), 0, 1);
        PlaceWorker2 placing1 = new PlaceWorker2(testGame5, testGame5.retrivePlayers()[0].getFigurine2(), 0, 4);
        PlaceWorker placing2 = new PlaceWorker(testGame5, testGame5.retrivePlayers()[1].getFigurine1(), 4, 0);
        PlaceWorker2 placing3 = new PlaceWorker2(testGame5, testGame5.retrivePlayers()[1].getFigurine2(), 4, 3);
        placing.perfromAction(gameService);
        placing1.perfromAction(gameService);
        placing2.perfromAction(gameService);
        placing3.perfromAction(gameService);
        testGame5.setStatus(GameStatus.MOVING_STARTINGPLAYER);
        Figurine figurine = testGame5.retrivePlayers()[0].retirveFigurines()[0];

        Moving move = new Moving(testGame5, figurine, 0, 2);
        move.perfromAction(gameService);
        testGame5 = gameService.gameByID(testGame5.getId());
        assertFalse(testGame5.getBoard().isEmpty(0, 2));
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

        Game testGame2 = new Game(testUser1, testUser2);
        testGame2.setStatus(GameStatus.PICKING_GODCARDS);
        testGame2.getStartingPlayer().setAssignedGod(new Apollo(testGame2));
        testGame2.getNonStartingPlayer().setAssignedGod(new Pan(testGame2));

        //test if stay the same
        gameService.saveGame(testGame2);
        ChoseYourGod pickGod = new ChoseYourGod(testGame2, true);
        pickGod.perfromAction(gameService);
        testGame2 = gameService.gameByID(testGame2.getId());
        Assert.assertEquals("Apollo", testGame2.getNonStartingPlayer().getAssignedGod().getName());
        Assert.assertEquals("Pan", testGame2.getStartingPlayer().getAssignedGod().getName());

        //test if switch
        testGame2.setStatus(GameStatus.PICKING_GODCARDS);
        gameService.saveGame(testGame2);
        pickGod = new ChoseYourGod(testGame2, false);
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

        userService.updateProfile(testUser1, testUser1.getId(), gameService);
        testUser2 = userService.userByID(testUser2.getId());
        testUser2.setChallenging(testUser1.getId());
        userService.updateProfile(testUser2, testUser2.getId(), gameService);
        testUser1 = userService.userByID(testUser1.getId());
        testUser2 = userService.userByID(testUser2.getId());

        Game testGame2 = gameService.gameByPlaxerId(testUser1.getId());
        testGame2.setStatus(GameStatus.STARTINGPLAYER_WON);

        //end pl1
        gameService.saveGame(testGame2);
        Action endGame = new endTheGame(testGame2, 1);
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
        endGame = new endTheGame(testGame2, 2);
        endGame.perfromAction(gameService);
        testGame2 = gameService.gameByID(testGame2.getId());

        //Assert.assertNull();
        Assert.assertNull(testGame2);
    }


    @Test
    public void canChoseGameModeYes() throws Exception {
        User testUser1 = new User();
        testUser1.setUsername("testUsernameAction1ChoseGameMode");
        testUser1.setPassword("testPassowrdAction2");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernamection2ChoseGameMode");
        testUser2.setPassword("testPassowrd");
        testUser2 = userService.createUser(testUser2);

        Game testGame2 = new Game(testUser1, testUser2);
        gameService.saveGame(testGame2);

        Action chodeGameMode = new ChoseGameModeAction(testGame2, true);
        chodeGameMode.perfromAction(gameService);
        testGame2 = gameService.gameByID(testGame2.getId());
        Assert.assertTrue(testGame2.isPlayWithGodCards());
    }

    @Test
    public void canChoseGameModeNo() throws Exception {
        User testUser1 = new User();
        testUser1.setUsername("testUsernameAction1ChoseGameModeNo");
        testUser1.setPassword("testPassowrdAction2");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernamection2ChoseGameModeNo");
        testUser2.setPassword("testPassowrd");
        testUser2 = userService.createUser(testUser2);

        Game testGame2 = new Game(testUser1, testUser2);
        gameService.saveGame(testGame2);

        Action chodeGameMode = new ChoseGameModeAction(testGame2, false);
        chodeGameMode.perfromAction(gameService);
        testGame2 = gameService.gameByID(testGame2.getId());
        Assert.assertFalse(testGame2.isPlayWithGodCards());
    }

    @Ignore
    @Test
    public void canChoseMode() throws Exception {
        User testUser1 = new User();
        testUser1.setUsername("testUsernameAction1ChoseMode");
        testUser1.setPassword("testPassowrdAction2");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernamection2ChoseMode");
        testUser2.setPassword("testPassowrd");
        testUser2 = userService.createUser(testUser2);

        Game testGame = new Game(testUser1, testUser2);
        testGame.setStatus(GameStatus.PICKING_GODCARDS);
        testGame.getStartingPlayer().setAssignedGod(new Artemis(testGame));
        testGame.getNonStartingPlayer().setAssignedGod(new Apollo(testGame));
        testGame.setPlayWithGodCards(true);
        gameService.saveGame(testGame);
        PlaceWorker placing = new PlaceWorker(testGame, testGame.retrivePlayers()[0].getFigurine1(), 0, 1);
        PlaceWorker2 placing1 = new PlaceWorker2(testGame, testGame.retrivePlayers()[0].getFigurine2(), 0, 3);
        PlaceWorker placing2 = new PlaceWorker(testGame, testGame.retrivePlayers()[1].getFigurine1(), 0, 2);
        PlaceWorker2 placing3 = new PlaceWorker2(testGame, testGame.retrivePlayers()[1].getFigurine2(), 0, 4);
        placing.perfromAction(gameService);
        placing1.perfromAction(gameService);
        placing2.perfromAction(gameService);
        placing3.perfromAction(gameService);
        gameService.saveGame(testGame);

        Figurine fig = testGame.getNonStartingPlayer().getFigurine2();
        Action choseMode = new ChooseMode(testGame, fig, true, 0, 3);
        choseMode.perfromAction(gameService);
        testGame = gameService.gameByID(testGame.getId());
        Assert.assertFalse(testGame.getBoard().isEmpty(0, 2));
        Assert.assertFalse(testGame.getBoard().isEmpty(0, 3));
    }

    @Test
    public void testCreateChooseGodActions() throws Throwable {
        User testUser1 = new User();
        testUser1.setUsername("testUsername1CreateChooseGodActions");
        testUser1.setPassword("testPassowrduhuhuh");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsername2CreateChooseGodActions");
        testUser2.setPassword("testPassowrdeieiei");
        testUser2 = userService.createUser(testUser2);

        Game testGame7 = new Game(testUser1, testUser2);
        testGame7.setStatus(GameStatus.CHOSING_GODCARDS);
        testGame7.setPlayWithGodCards(true);
        gameService.saveGame(testGame7);
        Player player1 = testGame7.getStartingPlayer();
        ArrayList<Action> possActions = player1.getPossibleActions(testGame7);
        ArrayList<Action> chG = new ArrayList<>();
        //creating all gods
        GodCard allGods[] = new GodCard[10];
        allGods[0] = new Apollo(testGame7);
        allGods[1] = new Artemis(testGame7);
        allGods[2] = new Athena(testGame7);
        allGods[3] = new Atlas(testGame7);
        allGods[4] = new Demeter(testGame7);
        allGods[5] = new Hephastephus(testGame7);
        allGods[6] = new Hermes(testGame7);
        allGods[7] = new Minotaur(testGame7);
        allGods[8] = new Pan(testGame7);
        allGods[9] = new Prometheus(testGame7);

        //create the actions
        for (int i = 0; i < 10; ++i) {
            for (int j = i + 1; j < 10; ++j) {
                chG.add(new ChooseGod(testGame7, allGods[i], allGods[j]));
            }
        }

        testGame7 = gameService.gameByID(testGame7.getId());
        for( int a=0;a<chG.size();a++ ){
            ChooseGod godActoin1 = (ChooseGod)chG.get(a);  //tolf java that this is not any action, but a ChooseGod-action
            int counter =0;
            for( int b=0;b<possActions.size();b++){
                ChooseGod godAction2 = (ChooseGod) possActions.get(b);  //tolf java that this is not any action, but a ChooseGod-action

                if( (godActoin1.getGod1().getGodnumber() == godAction2.getGod1().getGodnumber() &&
                        godActoin1.getGod2().getGodnumber() == godAction2.getGod2().getGodnumber()) ||
                        (godActoin1.getGod2().getGodnumber() == godAction2.getGod1().getGodnumber() &&
                                godActoin1.getGod1().getGodnumber() == godAction2.getGod2().getGodnumber())){
                    counter += 1;
                }
            }
            Assert.assertEquals(1, counter); //assert that only one time, the gods in godAction1 were equal to the ones in GodAction2
        }

    }

    @Test
    public void testCreatePickGodActions() throws Throwable {
        User testUser1 = new User();
        testUser1.setUsername("testUsername1CreatePickGodActions");
        testUser1.setPassword("testPassowrduhuhuh");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsername2CreatePickGodActions");
        testUser2.setPassword("testPassowrdeieiei");
        testUser2 = userService.createUser(testUser2);

        Game testGame8 = new Game(testUser1, testUser2);
        gameService.saveGame(testGame8);
        testGame8.setStatus(GameStatus.PICKING_GODCARDS);
        Player player1 = testGame8.getNonStartingPlayer();
        ArrayList<Action> possActions = player1.getPossibleActions(testGame8);
        ArrayList<Action> possibleActions = new ArrayList<>();
        possibleActions.add(new ChoseYourGod(testGame8,false));
        possibleActions.add(new ChoseYourGod(testGame8,true));
        testGame8 = gameService.gameByID(testGame8.getId());

        for( int a=0;a<possActions.size();a++ ){
            ChoseYourGod godAction1 = (ChoseYourGod)possActions.get(a);  //tolf java that this is not any action, but a ChooseGod-action
            int counter =0;
            for( int b=0;b<possActions.size();b++){
                ChoseYourGod godAction2 = (ChoseYourGod) possibleActions.get(b);  //tolf java that this is not any action, but a ChooseGod-action
                if( godAction1.getStatus() == godAction2.getStatus() ){
                    counter += 1;
                }
            }
            Assert.assertEquals(1, counter); //assert that only one time, the gods in godAction1 were equal to the ones in GodAction2
        }

    }



    @Test
    public void testCreatePlaceWorkerActions() throws Throwable{
        User testUser1 = new User();
        testUser1.setUsername("testUsername1CreatePlaceWorkerActions");
        testUser1.setPassword("testPassowrduhuhuh");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsername2CreatePlaceWorkerActions");
        testUser2.setPassword("testPassowrdeieiei");
        testUser2 = userService.createUser(testUser2);

        Game testGame9 = new Game(testUser1, testUser2);
        gameService.saveGame(testGame9);
        testGame9.setStatus(GameStatus.SettingFigurinesp1f1);

        testGame9.getStartingPlayer().setAssignedGod(new Artemis(testGame9));
        testGame9.getNonStartingPlayer().setAssignedGod(new Apollo(testGame9));
        testGame9.setPlayWithGodCards(true);
        Player player2 = testGame9.getStartingPlayer();
        ArrayList<Action> possibleActions = player2.getPossibleActions(testGame9);
        ArrayList<Action> possActions= new ArrayList<>();

        for( int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                if( testGame9.getBoard().isEmpty(i,j) ) {
                    possActions.add(new PlaceWorker(testGame9, testGame9.getStartingPlayer().getFigurine1(), i, j));
                }
            }
        }

        for( int a=0;a<possActions.size();a++ ){
            PlaceWorker godAction1 = (PlaceWorker)possActions.get(a);  //tolf java that this is not any action, but a ChooseGod-action
            int counter =0;
            for( int b=0;b<possibleActions.size();b++){
                PlaceWorker godAction2 = (PlaceWorker) possibleActions.get(b);  //tolf java that this is not any action, but a ChooseGod-action
                if( (godAction1.getRow() == godAction2.getRow()) &&
                        (godAction1.getColumn() == godAction2.getColumn())){
                    counter += 1;
                }
            }
            Assert.assertEquals(1, counter); //assert that only one time, the gods in godAction1 were equal to the ones in GodAction2
        }
        gameService.saveGame(testGame9);
    }

    @Test
    public void testCreatePlaceWorker2Actions() throws Throwable{
        User testUser1 = new User();
        testUser1.setUsername("testUsername1CreatePlaceWorker2Actions");
        testUser1.setPassword("testPassowrduhuhuh");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsername2CreatePlaceWorker2Actions");
        testUser2.setPassword("testPassowrdeieiei");
        testUser2 = userService.createUser(testUser2);

        Game testGame9 = new Game(testUser1, testUser2);
        gameService.saveGame(testGame9);
        testGame9.setStatus(GameStatus.SettingFigurinesp1f2);

        testGame9.getStartingPlayer().setAssignedGod(new Artemis(testGame9));
        testGame9.getNonStartingPlayer().setAssignedGod(new Apollo(testGame9));
        testGame9.setPlayWithGodCards(true);
        Player player2 = testGame9.getStartingPlayer();
        ArrayList<Action> possibleActions = player2.getPossibleActions(testGame9);
        ArrayList<Action> possActions= new ArrayList<>();

        for( int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                if( testGame9.getBoard().isEmpty(i,j) ) {
                    possActions.add(new PlaceWorker2(testGame9, testGame9.getStartingPlayer().getFigurine2(), i, j));
                }
            }
        }

        for( int a=0;a<possActions.size();a++ ){
            PlaceWorker2 godAction1 = (PlaceWorker2)possActions.get(a);  //tolf java that this is not any action, but a ChooseGod-action
            int counter =0;
            for( int b=0;b<possibleActions.size();b++){
                PlaceWorker2 godAction2 = (PlaceWorker2) possibleActions.get(b);  //tolf java that this is not any action, but a ChooseGod-action
                if( (godAction1.getRow() == godAction2.getRow()) &&
                        (godAction1.getColumn() == godAction2.getColumn())){
                    counter += 1;
                }
            }
            Assert.assertEquals(1, counter); //assert that only one time, the gods in godAction1 were equal to the ones in GodAction2
        }
        gameService.saveGame(testGame9);
    }


    @Test
    public void testCreateBuildingActions() throws Throwable {
        User testUser1 = new User();
        testUser1.setUsername("testUsernameCreateBuilding1");
        testUser1.setPassword("testPassowrdnuvenqvl");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernameCreateBuilding2");
        testUser2.setPassword("testPassowrdkeqrubh");
        testUser2 = userService.createUser(testUser2);

        Game testGame5 = new Game(testUser1, testUser2);
        gameService.saveGame(testGame5);
        PlaceWorker placing = new PlaceWorker(testGame5, testGame5.retrivePlayers()[0].getFigurine1(), 0, 1);
        PlaceWorker2 placing1 = new PlaceWorker2(testGame5, testGame5.retrivePlayers()[0].getFigurine2(), 0, 4);
        PlaceWorker placing2 = new PlaceWorker(testGame5, testGame5.retrivePlayers()[1].getFigurine1(), 4, 0);
        PlaceWorker2 placing3 = new PlaceWorker2(testGame5, testGame5.retrivePlayers()[1].getFigurine2(), 4, 3);
        placing.perfromAction(gameService);
        placing1.perfromAction(gameService);
        placing2.perfromAction(gameService);
        placing3.perfromAction(gameService);
        testGame5.setStatus(GameStatus.MOVING_STARTINGPLAYER);
        Figurine figurine = testGame5.retrivePlayers()[0].retirveFigurines()[0];

        Moving move = new Moving(testGame5, figurine, 0, 2);
        move.perfromAction(gameService);

        testGame5 = gameService.gameByID(testGame5.getId()); //After the actions got performed, we need to load the new, better game

        ArrayList<Action> possActions = testGame5.retrivePlayers()[0].getPossibleActions(testGame5);

        ArrayList<Action> possibleActions = new ArrayList<>();
        //figuring out which figurine moved
        ArrayList<Action> oldActions = testGame5.retrivePerformedActions();
        int movedFigurine = 1;
        for( int i = oldActions.size()-1; i >= 0; --i){
            Action action = oldActions.get(i);
            //System.out.println("iterated over one action\n");
            if( action instanceof Moving ){
                //System.out.println("is a instance of moving with figurine: " + Integer.toString( ((Moving) action).getFigurineNumber() ));
                movedFigurine = ((Moving) action).getFigurineNumber();
                break;
            }
        }
        possibleActions.addAll(testGame5.retrivePlayers()[0].retirveFigurines()[movedFigurine-1].getPossibleBuildingActions(testGame5));



        for( int a=0;a<possActions.size();a++ ){
            Building godAction1 = (Building)possActions.get(a);  //tolf java that this is not any action, but a ChooseGod-action
            int counter =0;
            for( int b=0;b<possibleActions.size();b++){
                Building godAction2 = (Building) possibleActions.get(b);  //tolf java that this is not any action, but a ChooseGod-action
                if( (godAction1.getRow() == godAction2.getRow()) &&
                        (godAction1.getColumn() == godAction2.getColumn())){
                    counter += 1;
                }
            }
            Assert.assertEquals(1, counter); //assert that only one time, the gods in godAction1 were equal to the ones in GodAction2
        }


        testGame5 = gameService.gameByID(testGame5.getId());
    }


    @Test
    public void testCreateChooseModeMovementsActions() throws Throwable{
        User testUser1 = new User();
        testUser1.setUsername("testUsernameCreateChooseModeMove1");
        testUser1.setPassword("testPassowrdnuvenqvl");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernameCreateChooseModeMove2");
        testUser2.setPassword("testPassowrdkeqrubh");
        testUser2 = userService.createUser(testUser2);

        Game testGame5 = new Game(testUser1, testUser2);
        gameService.saveGame(testGame5);

        PlaceWorker placing = new PlaceWorker(testGame5, testGame5.retrivePlayers()[0].getFigurine1(), 0, 1);
        PlaceWorker2 placing1 = new PlaceWorker2(testGame5, testGame5.retrivePlayers()[0].getFigurine2(), 0, 4);
        PlaceWorker placing2 = new PlaceWorker(testGame5, testGame5.retrivePlayers()[1].getFigurine1(), 4, 0);
        PlaceWorker2 placing3 = new PlaceWorker2(testGame5, testGame5.retrivePlayers()[1].getFigurine2(), 4, 3);
        placing.perfromAction(gameService);
        placing1.perfromAction(gameService);
        placing2.perfromAction(gameService);
        placing3.perfromAction(gameService);

        testGame5 = gameService.gameByID(testGame5.getId());

        testGame5.setStatus(GameStatus.MOVING_STARTINGPLAYER);

        testGame5.getStartingPlayer().setAssignedGod(new Artemis(testGame5));
        testGame5.getNonStartingPlayer().setAssignedGod(new Apollo(testGame5));
        testGame5.setPlayWithGodCards(true);
        Player player2 = testGame5.getStartingPlayer();

        gameService.saveGame(testGame5);


        ArrayList<Action> possibleActions = player2.getPossibleActions(testGame5);

        testGame5 = gameService.gameByID(testGame5.getId());//After the actions got performed, we need to load the new, better game


        ArrayList<Action> possActions = new ArrayList<>();
        ArrayList<Action> movingActions = ActionCreater.createMovementActions(testGame5, player2);
        for( int i = 0; i < movingActions.size(); ++i){
            Action currentAction = movingActions.get(i);
            if(currentAction instanceof Moving){
                Figurine figurine = player2.retirveFigurines()[((Moving) currentAction).getFigurineNumber()-1];
                possActions.add(new ChooseMode(testGame5,figurine,false,((Moving) currentAction).getRow(), ((Moving) currentAction).getColumn() ) );
            }
        }
        ArrayList<Action> godMoving = player2.getAssignedGod().getActions(testGame5);
        for( int i = 0; i < godMoving.size(); ++i){
            Action currentAction = godMoving.get(i);
            if(currentAction instanceof GodMovingAction){
                Figurine figurine = player2.retirveFigurines()[((GodMovingAction) currentAction).getFigurineNumber()-1];
                possActions.add(new ChooseMode(testGame5,figurine,true,((GodMovingAction) currentAction).getRow(), ((GodMovingAction) currentAction).getColumn() ) );
            }
        }



        for( int a=0;a<possActions.size();a++ ){
            ChooseMode godAction1 = (ChooseMode)possActions.get(a);  //tolf java that this is not any action, but a ChooseGod-action
            int counter =0;
            for( int b=0;b<possibleActions.size();b++ ){
                ChooseMode godAction2 = (ChooseMode) possibleActions.get(b);  //tolf java that this is not any action, but a ChooseGod-action
                if( (godAction1.getRow() == godAction2.getRow()) &&
                        (godAction1.getColumn() == godAction2.getColumn())){
                    counter += 1;
                }
            }
            Assert.assertEquals(2, counter); //assert that only one time, the gods in godAction1 were equal to the ones in GodAction2
        }



        testGame5 = gameService.gameByID(testGame5.getId());


    }

    @Test
    public void testPerformActionOverActionService() throws Throwable {
        User testUser1 = new User();
        testUser1.setUsername("testUsernameACtionservice1");
        testUser1.setPassword("testPassowrdnuvenqvl");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernameActionservice2");
        testUser2.setPassword("testPassowrdkeqrubh");
        testUser2 = userService.createUser(testUser2);

        Game testGame5 = new Game(testUser1, testUser2);
        gameService.saveGame(testGame5);

        PlaceWorker placing = new PlaceWorker(testGame5, testGame5.retrivePlayers()[0].getFigurine1(), 0, 1);
        PlaceWorker2 placing1 = new PlaceWorker2(testGame5, testGame5.retrivePlayers()[0].getFigurine2(), 0, 4);
        PlaceWorker placing2 = new PlaceWorker(testGame5, testGame5.retrivePlayers()[1].getFigurine1(), 4, 0);
        PlaceWorker2 placing3 = new PlaceWorker2(testGame5, testGame5.retrivePlayers()[1].getFigurine2(), 4, 3);
        placing.perfromAction(gameService);
        placing1.perfromAction(gameService);
        placing2.perfromAction(gameService);
        placing3.perfromAction(gameService);

        testGame5 = gameService.gameByID(testGame5.getId());

        testGame5.setStatus(GameStatus.MOVING_STARTINGPLAYER);

        testGame5.getStartingPlayer().setAssignedGod(new Artemis(testGame5));
        testGame5.getNonStartingPlayer().setAssignedGod(new Apollo(testGame5));
        testGame5.setPlayWithGodCards(true);
        Player player2 = testGame5.getStartingPlayer();

        gameService.saveGame(testGame5);


        ArrayList<Action> possibleActions = player2.getPossibleActions(testGame5);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get( "http://localhost:8080/game/actions/"+Long.toString(testUser1.getId())).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get( "http://localhost:8080/game/actions/"+Long.toString(testUser1.getId())).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();


        testGame5 = gameService.gameByID(testGame5.getId());

        long id = testGame5.retriveActions1().get(0);
        ChooseMode action = (ChooseMode) actionService.getActionById(id);
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.put( "http://localhost:8080/game/actions/"+Long.toString(id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        testGame5 = gameService.gameByID(testGame5.getId());
        Assert.assertNull(testGame5.retriveActions1());
        Assert.assertFalse(testGame5.getBoard().isEmpty(action.getRow(),action.getColumn()));
    }

    @Test
    public void surrenderTest() throws Exception {
        long player1id;
        long player2id;
        Game testGame;
        long gameId;

        User testUser1 = new User();
        testUser1.setUsername("testUsernameAction1surrender");
        testUser1.setPassword("testPassowrdAction2");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsernamection2surrender");
        testUser2.setPassword("testPassowrd");
        testUser2 = userService.createUser(testUser2);

        testUser1.setChallenging(testUser2.getId());

        userService.updateProfile(testUser1, testUser1.getId(), gameService);
        testUser2 = userService.userByID(testUser2.getId());
        testUser2.setChallenging(testUser1.getId());
        userService.updateProfile(testUser2, testUser2.getId(), gameService);
        testUser1 = userService.userByID(testUser1.getId());
        testUser2 = userService.userByID(testUser2.getId());

        testGame = gameService.gameByPlaxerId(testUser1.getId());

        testGame.setStatus(GameStatus.MOVING_STARTINGPLAYER);

        gameRepository.save(testGame);

        player1id = testUser1.getId();
        player2id = testUser2.getId();
        gameId = testGame.getId();

        // Use player id as action id to surrender
        //mockMvc.perform(MockMvcRequestBuilders.put( "http://localhost:8080/game/actions/" + Long.toString(player1id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put( "http://localhost:8080/game/"+Long.toString(gameId)+"/actions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Long.toString(player1id)))
                .andReturn();

        //Assert.assertEquals("action was performed", mvcResult.getResponse().getContentAsString());
        Assert.assertEquals(200,mvcResult.getResponse().getStatus());
        //get changed game
        testGame = gameService.gameByID(gameId);

        //check if other player won
        Assert.assertEquals(testGame.getStatus(), GameStatus.NONSTARTINGPLAYER_WON);
    }

}