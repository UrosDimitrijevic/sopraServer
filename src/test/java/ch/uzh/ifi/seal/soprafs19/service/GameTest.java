package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Demeter;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Hephastephus;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Minotaur;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Prometheus;
import ch.uzh.ifi.seal.soprafs19.entity.Space;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class GameTest {


    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Qualifier("gameRepository")
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;


    //von mir hinzugefügt
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;


    @Test
    public void canCreateAndSaveGame() throws Exception {
        User testUser1 = new User();
        testUser1.setUsername("testUsername1");
        testUser1.setPassword("testPassowrd");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsername2");
        testUser2.setPassword("testPassowrd");
        testUser2 = userService.createUser(testUser2);

        Game testGame = new Game(testUser1, testUser2 );

        long id2 = 999;

        this.gameService.saveGame(testGame);

        long id = testGame.getId();

        Game retrivedGame = this.gameService.gameByID(id);

        Assert.assertNotNull(retrivedGame);
    }

    @Test
    public void justForTestingTest() throws Exception{
        User testUser1 = new User();
        testUser1.setUsername("testUsernameJustForTesting1");
        testUser1.setPassword("testPassowrd");
        testUser1 = userService.createUser(testUser1);
        testUser1.setBirthday("2000-01-01");


        User testUser2 = new User();
        testUser2.setUsername("testUsernameJustForTesting2");
        testUser2.setPassword("testPassowrd");
        testUser2 = userService.createUser(testUser2);
        testUser2.setBirthday("2002-01-01");

        mockMvc.perform(MockMvcRequestBuilders.put( "http://localhost:8080/game/justForTesting/" + Long.toString(testUser1.getId())).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        Game mygame= gameService.gameByPlaxerId(testUser1.getId());

        Assert.assertTrue(mygame.isPlayWithGodCards());
        Assert.assertEquals(mygame.getStatus(),GameStatus.MOVING_STARTINGPLAYER);
        Assert.assertEquals(mygame.getStartingPlayer().getAssignedGod(), new Hephastephus(mygame));
        Assert.assertEquals(mygame.getNonStartingPlayer().getAssignedGod(), new Demeter(mygame));
        Assert.assertFalse(mygame.getBoard().getSpaces()[0][1].checkIfEmtpy());
        Assert.assertFalse(mygame.getBoard().getSpaces()[1][0].checkIfEmtpy());
        Assert.assertFalse(mygame.getBoard().getSpaces()[1][1].checkIfEmtpy());
        Assert.assertFalse(mygame.getBoard().getSpaces()[4][3].checkIfEmtpy());
        Assert.assertFalse(mygame.getBoard().getSpaces()[3][4].checkIfEmtpy());
        Assert.assertFalse(mygame.getBoard().getSpaces()[3][3].checkIfEmtpy());
        Assert.assertFalse(mygame.getBoard().getSpaces()[2][4].checkIfEmtpy());
        Assert.assertFalse(mygame.getBoard().getSpaces()[2][3].checkIfEmtpy());
        Assert.assertFalse(mygame.getBoard().getSpaces()[4][4].checkIfEmtpy());

        Assert.assertEquals(mygame.getBoard().getSpaces()[0][1].getLevel(),4);
        Assert.assertEquals(mygame.getBoard().getSpaces()[1][0].getLevel(),4);
        Assert.assertEquals(mygame.getBoard().getSpaces()[1][1].getLevel(),4);
        Assert.assertEquals(mygame.getBoard().getSpaces()[4][3].getLevel(),4);
        Assert.assertEquals(mygame.getBoard().getSpaces()[3][4].getLevel(),3);
        Assert.assertEquals(mygame.getBoard().getSpaces()[3][3].getLevel(),2);
        Assert.assertEquals(mygame.getBoard().getSpaces()[2][4].getLevel(),2);
        Assert.assertEquals(mygame.getBoard().getSpaces()[2][3].getLevel(),1);
        Assert.assertEquals(mygame.getBoard().getSpaces()[4][4].getLevel(),1);

        Assert.assertArrayEquals(new int[]{0, 2},mygame.retrivePlayers()[0].getFigurine1().getPosition());
        Assert.assertEquals( new int[]{0,3}  ,mygame.retrivePlayers()[0].getFigurine2().getPosition());
        Assert.assertEquals(  new int[]{1,2},mygame.retrivePlayers()[1].getFigurine1().getPosition());
        Assert.assertEquals(new int[]{3,1},mygame.retrivePlayers()[1].getFigurine2().getPosition());

        Assert.assertNull(mygame.getActions2());
        Assert.assertNull(mygame.getActions1());



/*

        //setting up buildings
        game.getBoard().getSpaces()[0][1] = new Space();
        game.getBoard().getSpaces()[1][0] = new Space();
        game.getBoard().getSpaces()[1][1] = new Space();
        game.getBoard().getSpaces()[4][3] = new Space();
        game.getBoard().getSpaces()[3][4] = new Space();
        game.getBoard().getSpaces()[3][3] = new Space();
        game.getBoard().getSpaces()[2][4] = new Space();
        game.getBoard().getSpaces()[2][3] = new Space();
        game.getBoard().getSpaces()[4][4] = new Space();
        for(int i = 0; i < 4; ++i){
            game.getBoard().getSpaces()[0][1].build();
            game.getBoard().getSpaces()[1][0].build();
            game.getBoard().getSpaces()[1][1].build();
            game.getBoard().getSpaces()[4][3].build();
        }
        for(int i = 0; i < 3; ++i){ game.getBoard().getSpaces()[3][4].build(); }
        for( int i = 0; i < 2; ++i){
            game.getBoard().getSpaces()[3][3].build();
            game.getBoard().getSpaces()[2][4].build();
        }
        game.getBoard().getSpaces()[2][3].build();
        game.getBoard().getSpaces()[4][4].build();

        //setting figurines
        game.retrivePlayers()[0].getFigurine1().setPosition(0,2);
        game.retrivePlayers()[0].getFigurine2().setPosition(0,3);
        game.retrivePlayers()[1].getFigurine1().setPosition(1,2);
        game.retrivePlayers()[1].getFigurine2().setPosition(3,1);
        game.checkIfGameOver();

        //making all action-arrays null
        game.setActions2(null);
        game.setActions1(null);

        gameService.saveGame(game);
        return ResponseEntity.status(HttpStatus.OK).body("you cheater" );
*/



    }






}