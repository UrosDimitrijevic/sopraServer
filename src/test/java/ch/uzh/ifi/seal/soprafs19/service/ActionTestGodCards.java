package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.*;
import ch.uzh.ifi.seal.soprafs19.entity.actions.*;

import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.entity.actions.ChooseMode;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Moving;
import ch.uzh.ifi.seal.soprafs19.repository.ActionRepository;
import ch.uzh.ifi.seal.soprafs19.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;

import javax.validation.constraints.AssertTrue;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class ActionTestGodCards {

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
    private Game testGame;
    private long gameId;

    private RandomString randString;

    private void createUserAndGame() {
        this.randString = new RandomString();
        String username = randString.nextString();

        //board setUp
        User testUser1 = new User();
        testUser1.setUsername(username);
        testUser1.setPassword("TestPassword1");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        username = randString.nextString();

        User testUser2 = new User();
        testUser2.setUsername(username);
        testUser2.setPassword("TestPassword2");
        testUser2 = userService.createUser(testUser2);

        this.testGame = new Game(testUser1, testUser2);
        testUser1 = userService.createUser(testUser1);
        testUser2 = userService.createUser(testUser2);

        //setting up game mode
        testGame.setStatus(GameStatus.MOVING_STARTINGPLAYER);
        testGame.setPlayWithGodCards(true);

        gameRepository.save(testGame);
        this.player1id = testUser1.getId();
        this.player2id = testUser2.getId();
        this.gameId = testGame.getId();
    }
    @Before //wird vor jedem Test durchgeführt
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.createUserAndGame();
    }

    @Test
    public void AppolloActions() throws Exception{
        //setting the figurines
        testGame.getStartingPlayer().getFigurine1().setPosition(1,1);
        testGame.getStartingPlayer().getFigurine2().setPosition(3,1);
        testGame.getNonStartingPlayer().getFigurine1().setPosition(1,2);
        testGame.getNonStartingPlayer().getFigurine2().setPosition(4,3);

        //assign god cards
        testGame.setStatus(GameStatus.PICKING_GODCARDS);
        testGame.getStartingPlayer().setAssignedGod(new Apollo(testGame));
        testGame.getNonStartingPlayer().setAssignedGod(new Pan(testGame));
        testGame.setStatus(GameStatus.MOVING_STARTINGPLAYER);

        gameRepository.save(testGame);

        //MvcResult result = this.mockMvc.perform(get("/users/100")).andExpect(status().isNotFound() );
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get( "http://localhost:8080/game/actions/" + Long.toString(this.player1id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get( "http://localhost:8080/game/actions/" + Long.toString(this.player1id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        //making sure we get the right return-code
        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);

        //checking content
        class Response {
            public int id;
            public int row;
            public int column;
            public int figurine;
            public boolean withGod;
            Response(){}
        }
        ArrayList<Response> actions = new ArrayList<Response>();

        //creating and checking the actions for consistency
        int i = 0;
        while( content.charAt(i) != ']') {
            i += 1;
            Response response = new Response();
            Assert.assertEquals("{\"id\":", content.substring(i,i+6) ); i += 6;
            String number = "";
            while(content.charAt(i) != ','){ number += content.charAt(i); i += 1; } i += 1;
            response.id = Integer.parseInt(number);
            Assert.assertEquals("\"name\":\"Choose Moving Mode\",\"row\":", content.substring(i,i+34 ) );i+=34;
            number = "";
            while(content.charAt(i) != ','){ number += content.charAt(i); i += 1; }
            response.row = Integer.parseInt(number);
            Assert.assertEquals(",\"column\":", content.substring(i,i+10 ) ); i += 10;
            number = "";
            while(content.charAt(i) != ','){ number += content.charAt(i); i += 1; }
            response.column = Integer.parseInt(number);
            Assert.assertEquals(",\"figurineNumber\":", content.substring(i,i+18 ) ); i += 18;
            number = "";
            while(content.charAt(i) != ','){ number += content.charAt(i); i += 1; }
            response.figurine = Integer.parseInt(number);
            Assert.assertEquals(",\"useGod\":", content.substring(i,i+10 )); i += 10;
            if(content.substring(i,i+4 ).equals( "true") ){
                i += 5;
                response.withGod = true;
            }else{
                i += 6;
                response.withGod = false;
            }
            actions.add(response);
        }

        //looking that only allowed moves are here  [1][1]  [3][1]
        int movements1 [][] = new int [3][3];
        int movements2 [][] = new int [3][3];

        Response toPerform = null;

        for( i = 0; i < actions.size(); ++i){
            Assert.assertTrue(actions.get(i).figurine == 1 || actions.get(i).figurine == 2 );
            int row = actions.get(i).row;
            int col = actions.get(i).column;
            if(actions.get(i).figurine == 1){
                movements1[row][col]++;
            } else if(actions.get(i).figurine == 2){
                movements2[row-2][col]++;
            }
            if(actions.get(i).withGod){
                toPerform = actions.get(i);
            }
        }

        //can move to the field with the opponent figurine
        Assert.assertEquals(movements1[1][2],1);

        //perform Appollo action
        mockMvc.perform(MockMvcRequestBuilders.put( "http://localhost:8080/game/actions/" + Long.toString(toPerform.id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        //control if Appollo action was performed correctly
        //mvcResult = mockMvc.perform(MockMvcRequestBuilders.get( "http://localhost:8080/game/Board/" + Long.toString(gameId)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        //retrieve changed game
        this.testGame = gameService.gameByID(this.gameId);

        //check if player1.figurine1 and player2.figurine1 changed positions
        int[] position = testGame.getStartingPlayer().getFigurine1().getPosition();
        Assert.assertEquals(position[0], 1);
        Assert.assertEquals(position[1], 2);
        position = testGame.getNonStartingPlayer().getFigurine1().getPosition();
        Assert.assertEquals(position[0], 1);
        Assert.assertEquals(position[1], 1);

    }
}