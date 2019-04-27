package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Moving;
import ch.uzh.ifi.seal.soprafs19.repository.ActionRepository;
import ch.uzh.ifi.seal.soprafs19.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.Assert;
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
import java.util.ArrayList;

import javax.validation.constraints.AssertTrue;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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

    private RandomString randString;

    private void createUserAndGame(){

        this.randString = new RandomString();
        String username = randString.nextString();


        //board setUp
        User testUser1 = new User();
        testUser1.setUsername(username);
        testUser1.setPassword("complexTestPassowrd");
        testUser1 = userService.createUser(testUser1);

        username = randString.nextString();

        User testUser2 = new User();
        testUser2.setUsername(username);
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
        testGame.getNonStartingPlayer().getFigurine1().setPosition(1,2);
        testGame.getNonStartingPlayer().getFigurine1().setPosition(3,3);

        gameRepository.save(testGame);
        this.player1id = testUser1.getId();
        this.player2id = testUser2.getId();
        this.gameId = testGame.getId();

    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.createUserAndGame();
    }

    @Test
    public void canMoveFigurineFromFigurenClass() throws Exception{
        Game game = gameService.gameByID(this.gameId);
        Figurine figurine = game.getStartingPlayer().getFigurine2();
        assertTrue(figurine.getPosition()[0] == 3);
        assertTrue(figurine.getPosition()[1] == 1);

        figurine.changePosition(4,4);


        Assert.assertTrue(figurine.getPosition()[0] == 4);
        Assert.assertTrue(figurine.getPosition()[1] == 4);
        assertTrue(game.getBoard().getSpaces()[3][1].checkIfEmtpy() );
        assertFalse(game.getBoard().getSpaces()[4][4].checkIfEmtpy() );

        //gameRepository.save(game);
        return;
    }

    @Test
    public void canMoveFigurineFromAction() throws Exception{
        Game game = gameService.gameByID(this.gameId);
        Assert.assertNotNull(game);
        Figurine figurine = game.getStartingPlayer().getFigurine1();

        //creating and performing the Action
        Action moveAction = new Moving(game, figurine,3,4);
        moveAction.perfromAction(gameService);

        //checking if it worked
        game = gameService.gameByID(this.gameId);
        figurine = game.getStartingPlayer().getFigurine1();
        Assert.assertTrue(figurine.getPosition()[0] == 3);
        Assert.assertTrue(figurine.getPosition()[1] == 4);
        assertTrue(game.getBoard().getSpaces()[1][1].checkIfEmtpy() );
        assertFalse(game.getBoard().getSpaces()[3][4].checkIfEmtpy() );
    }


    @Test
    public void canGetAllPossiblePositons() throws Exception{
        Game game = gameService.gameByID(this.gameId);
        Assert.assertNotNull(game);
        Figurine figurine = game.getStartingPlayer().getFigurine1();

        ArrayList<Action>  movementArray = figurine.getPossibleMovingActions(game);

        for( int i = 0; i < movementArray.size(); ++i){
            Action thisAction = movementArray.get(i);
            if(thisAction instanceof  Moving){
                Moving movingAction = (Moving) thisAction;
                Assert.assertTrue(thisAction.getName().contains("Moving") );
                Assert.assertEquals(movingAction.getFigurineNumber(), 1);
                Assert.assertTrue(movingAction.getColumn() == 0 || movingAction.getColumn() == 1 || movingAction.getColumn() == 2);
                Assert.assertTrue(movingAction.getRow() == 0 || movingAction.getRow() == 1 || movingAction.getRow() == 2);
                Assert.assertFalse(movingAction.getRow() == 1 && movingAction.getColumn() == 1);
                Assert.assertFalse(movingAction.getRow() == 1 && movingAction.getColumn() == 2);
            }
            else { Assert.assertFalse(true); }
        }
    }
}
