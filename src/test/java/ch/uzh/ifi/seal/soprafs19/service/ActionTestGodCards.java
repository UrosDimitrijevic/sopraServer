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


    /**
     * Apollo actions test
     * **/

    @Test
    public void ApolloActions() throws Exception{
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

        //get actions
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

        //looking that only allowed moves are here
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


    /**
     * Artemis actions test
     * **/

    @Test
    public void ArtemisActions() throws Exception {
        //setting the figurines
        testGame.getStartingPlayer().getFigurine1().setPosition(1, 1);
        testGame.getStartingPlayer().getFigurine2().setPosition(3, 3);
        testGame.getNonStartingPlayer().getFigurine1().setPosition(1, 2);
        testGame.getNonStartingPlayer().getFigurine2().setPosition(4, 4);

        //assign god cards
        testGame.setStatus(GameStatus.PICKING_GODCARDS);
        testGame.getStartingPlayer().setAssignedGod(new Artemis(testGame));
        testGame.getNonStartingPlayer().setAssignedGod(new Pan(testGame));
        testGame.setStatus(GameStatus.MOVING_STARTINGPLAYER);

        gameRepository.save(testGame);

        //get actions
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

        //looking that only allowed moves are here
        int movements1 [][] = new int [5][5];
        int movements2 [][] = new int [5][5];

        Response toPerform = null;

        for( i = 0; i < actions.size(); ++i){
            Assert.assertTrue(actions.get(i).figurine == 1 || actions.get(i).figurine == 2 );
            int row = actions.get(i).row;
            int col = actions.get(i).column;
            if(actions.get(i).figurine == 1){
                movements1[row][col]++;
            } else if(actions.get(i).figurine == 2){
                movements2[row][col]++;
            }
            if(actions.get(i).withGod && actions.get(i).figurine == 2){
                toPerform = actions.get(i);
            }
        }

        //check if correct movements are saved in movements matrix
        Assert.assertEquals(movements2[2][2], 2);
        Assert.assertEquals(movements2[2][3], 2);
        Assert.assertEquals(movements2[2][4], 2);
        Assert.assertEquals(movements2[3][2], 2);
        Assert.assertEquals(movements2[3][3], 0); //own position
        Assert.assertEquals(movements2[3][4], 2);
        Assert.assertEquals(movements2[4][2], 2);
        Assert.assertEquals(movements2[4][3], 2);
        Assert.assertEquals(movements2[4][4], 0); //opponent figurine's position

        //perform saved action
        mockMvc.perform(MockMvcRequestBuilders.put( "http://localhost:8080/game/actions/" + Long.toString(toPerform.id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        //check again all possible actions
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get( "http://localhost:8080/game/actions/" + Long.toString(this.player1id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        content = mvcResult.getResponse().getContentAsString();

        //making sure we get the right return-code
        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);

        ArrayList<Response> actions2 = new ArrayList<Response>();

        //(again) creating and checking the actions for consistency
        i= 0;
        while( content.charAt(i) != ']') {
            i += 1;
            Response response = new Response();
            Assert.assertEquals("{\"id\":", content.substring(i,i+6) ); i += 6;
            String number = "";
            while(content.charAt(i) != ','){ number += content.charAt(i); i += 1; } i += 1;
            response.id = Integer.parseInt(number);
            Assert.assertEquals("\"name\":\"movingAsArthemis\",\"row\":", content.substring(i,i+32 ) );i+=32;
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
            actions2.add(response);
        }

        //set movements to 0 again
        int j;
        for (i = 0; i < 5; ++i){
            for ( j = 0; j < 5; ++j){
                movements1[i][j] = 0;
                movements2[i][j] = 0;
            }
        }

        //looking that only allowed moves are here
        for( i = 0; i < actions2.size(); ++i){
            Assert.assertTrue(actions2.get(i).figurine == 1 || actions2.get(i).figurine == 2 );
            int row = actions2.get(i).row;
            int col = actions2.get(i).column;
            if(actions2.get(i).figurine == 1){
                movements1[row][col]++;
            } else if(actions2.get(i).figurine == 2){
                movements2[row][col]++;
            }
        }


        //check if correct movements are saved in movements matrix
        Assert.assertEquals(movements2[2][3], 1);
        Assert.assertEquals(movements2[2][4], 1);
        Assert.assertEquals(movements2[3][3], 0); //old position
        Assert.assertEquals(movements2[3][4], 0); //own position
        Assert.assertEquals(movements2[4][3], 1);
        Assert.assertEquals(movements2[4][4], 0); //opponent figurine's position

    }

    /**
     * Demeter actions test
     * **/

    /**
     * Atlas actions test
     * **/

    @Test
    public void AtlasActions() throws Exception {
        //setting the figurines
        testGame.getStartingPlayer().getFigurine1().setPosition(1, 1);
        testGame.getStartingPlayer().getFigurine2().setPosition(3, 3);
        testGame.getNonStartingPlayer().getFigurine1().setPosition(1, 2);
        testGame.getNonStartingPlayer().getFigurine2().setPosition(4, 4);

        //assign god cards
        testGame.setStatus(GameStatus.PICKING_GODCARDS);
        testGame.getStartingPlayer().setAssignedGod(new Atlas(testGame));
        testGame.getNonStartingPlayer().setAssignedGod(new Pan(testGame));
        testGame.setStatus(GameStatus.MOVING_STARTINGPLAYER);

        gameRepository.save(testGame);

        //get actions
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

        //looking that only allowed moves are here
        int movements1 [][] = new int [5][5];
        int movements2 [][] = new int [5][5];

        Response toPerform = null;

        for( i = 0; i < actions.size(); ++i){
            Assert.assertTrue(actions.get(i).figurine == 1 || actions.get(i).figurine == 2 );
            int row = actions.get(i).row;
            int col = actions.get(i).column;
            if(actions.get(i).figurine == 1){
                movements1[row][col]++;
            } else if(actions.get(i).figurine == 2){
                movements2[row][col]++;
            }
            if(row == 3 && col == 4 && actions.get(i).figurine == 2){
                toPerform = actions.get(i);
            }
        }

        //check if correct movements are saved in movements matrix
        Assert.assertEquals(movements2[2][2], 1);
        Assert.assertEquals(movements2[2][3], 1);
        Assert.assertEquals(movements2[2][4], 1);
        Assert.assertEquals(movements2[3][2], 1);
        Assert.assertEquals(movements2[3][3], 0); //own position
        Assert.assertEquals(movements2[3][4], 1);
        Assert.assertEquals(movements2[4][2], 1);
        Assert.assertEquals(movements2[4][3], 1);
        Assert.assertEquals(movements2[4][4], 0); //opponent figurine's position

        //perform saved action
        mockMvc.perform(MockMvcRequestBuilders.put( "http://localhost:8080/game/actions/" + Long.toString(toPerform.id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        //get changed game
        this.testGame = gameService.gameByID(this.gameId);

        //change to building mode
        testGame.setStatus(GameStatus.BUILDING_STARTINGPLAYER);

        gameRepository.save(testGame);

        //get actions
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/game/actions/" + Long.toString(this.player1id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        content = mvcResult.getResponse().getContentAsString();

        //making sure we get the right return-code
        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);


        ArrayList<Response> actions2 = new ArrayList<Response>();

        //creating and checking the actions for consistency
        i = 0;
        while (content.charAt(i) != ']') {
            i += 1;
            Response response = new Response();
            Assert.assertEquals("{\"id\":", content.substring(i, i + 6));
            i += 6;
            String number = "";
            while (content.charAt(i) != ',') {
                number += content.charAt(i);
                i += 1;
            }
            i += 1;
            response.id = Integer.parseInt(number);
            Assert.assertEquals("\"name\":\"Building", content.substring(i, i + 16));
            i+=16;
            if (content.substring(i, i + 7).equals("AsAtlas")) {
                i+=7;
            }
            Assert.assertEquals("\",\"row\":", content.substring(i, i + 8));
            i += 8;
            number = "";
            while (content.charAt(i) != ',') {
                number += content.charAt(i);
                i += 1;
            }
            response.row = Integer.parseInt(number);
            Assert.assertEquals(",\"column\":", content.substring(i, i + 10));
            i += 10;
            number = "";
            while (content.charAt(i) != ',') {
                number += content.charAt(i);
                i += 1;
            }
            response.column = Integer.parseInt(number);
            Assert.assertEquals(",\"useGod\":", content.substring(i, i + 10));
            i += 10;
            if (content.substring(i, i + 4).equals("true")) {
                i += 5;
                response.withGod = true;
            } else {
                i += 6;
                response.withGod = false;
            }
            actions2.add(response);
        }

        //looking that only allowed moves are here  [1][1]  [3][1]
        int building[][] = new int[5][5];

        toPerform = null;

        for (i = 0; i < actions2.size(); ++i) {
            int row = actions2.get(i).row;
            int col = actions2.get(i).column;
            building[row][col]++;
            if (actions2.get(i).withGod && row == 4 && col == 3) {
                toPerform = actions2.get(i);
            }
        }

        //check if correct building actions are saved in building matrix - for some fields two building actions
        Assert.assertEquals(building[2][3], 2);
        Assert.assertEquals(building[2][4], 2);
        Assert.assertEquals(building[3][3], 2); //old position
        Assert.assertEquals(building[3][4], 0); //own position
        Assert.assertEquals(building[4][3], 2);
        Assert.assertEquals(building[4][4], 0); //opponent figurine's position

        //build a dome
        mockMvc.perform(MockMvcRequestBuilders.put( "http://localhost:8080/game/actions/" + Long.toString(toPerform.id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        //get changed game
        this.testGame = gameService.gameByID(this.gameId);

        //change to moving mode, nonstarting player
        testGame.setStatus(GameStatus.MOVING_NONSTARTINGPLAYER);

        gameRepository.save(testGame);

        //get actions
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/game/actions/" + Long.toString(this.player2id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        content = mvcResult.getResponse().getContentAsString();

        //making sure we get the right return-code
        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);


        ArrayList<Response> actions3 = new ArrayList<Response>();

        //creating and checking the actions for consistency
        i = 0;
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
            actions3.add(response);
        }

        //looking that only allowed moves are here
        int movements12 [][] = new int [5][5];
        int movements22 [][] = new int [5][5];

        for( i = 0; i < actions3.size(); ++i){
            Assert.assertTrue(actions3.get(i).figurine == 1 || actions3.get(i).figurine == 2 );
            int row = actions3.get(i).row;
            int col = actions3.get(i).column;
            if(actions3.get(i).figurine == 1){
                movements12[row][col]++;
            } else if(actions3.get(i).figurine == 2){
                movements22[row][col]++;
            }
        }

        //check if it worked (can't move to [4][3])
        Assert.assertEquals(movements22[3][3], 1);
        Assert.assertEquals(movements22[3][4], 0); //starting player position
        Assert.assertEquals(movements22[4][3], 0); //dome position
        Assert.assertEquals(movements22[4][4], 0); //own position
    }

    /**
     * Hermes actions test
     * **/

    @Test
    public void HermesActions() throws Exception {
        //building some structures
        testGame.getBoard().getSpaces()[0][1].build();
        testGame.getBoard().getSpaces()[1][1].build();
        testGame.getBoard().getSpaces()[2][1].build();
        testGame.getBoard().getSpaces()[2][2].build();
        testGame.getBoard().getSpaces()[2][3].build();
        testGame.getBoard().getSpaces()[1][2].build();
        testGame.getBoard().getSpaces()[1][2].build();

        testGame.getBoard().getSpaces()[3][1].build();
        testGame.getBoard().getSpaces()[3][2].build();

        //setting the figurines
        testGame.getStartingPlayer().getFigurine1().setPosition(0, 1);
        testGame.getStartingPlayer().getFigurine2().setPosition(2, 3);
        testGame.getNonStartingPlayer().getFigurine1().setPosition(3, 1);
        testGame.getNonStartingPlayer().getFigurine2().setPosition(3, 2);

        //assign god cards
        testGame.setStatus(GameStatus.PICKING_GODCARDS);
        testGame.getStartingPlayer().setAssignedGod(new Hermes(testGame));
        testGame.getNonStartingPlayer().setAssignedGod(new Artemis(testGame));
        //testGame.checkIfGameOver();
        testGame.setStatus(GameStatus.MOVING_STARTINGPLAYER);

        gameRepository.save(testGame);

        //get actions
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/game/actions/" + Long.toString(this.player1id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/game/actions/" + Long.toString(this.player1id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

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

            Response() {
            }
        }
        ArrayList<Response> actions = new ArrayList<Response>();

        //creating and checking the actions for consistency
        int i = 0;
        while (content.charAt(i) != ']') {
            i += 1;
            Response response = new Response();
            Assert.assertEquals("{\"id\":", content.substring(i, i + 6));
            i += 6;
            String number = "";
            while (content.charAt(i) != ',') {
                number += content.charAt(i);
                i += 1;
            }
            i += 1;
            response.id = Integer.parseInt(number);
            if (content.substring(i, i + 30).equals("\"name\":\"movingAsHermes\",\"row\":")) {
                Assert.assertEquals("\"name\":\"movingAsHermes\",\"row\":", content.substring(i,i+30 ) );i+=30;
                i += 30;
            } else {
                Assert.assertEquals("\"name\":\"Choose Moving Mode\",\"row\":", content.substring(i, i + 34));
                i += 34;
            }
            number = "";
            while (content.charAt(i) != ',') {
                number += content.charAt(i);
                i += 1;
            }
            response.row = Integer.parseInt(number);
            Assert.assertEquals(",\"column\":", content.substring(i, i + 10));
            i += 10;
            number = "";
            while (content.charAt(i) != ',') {
                number += content.charAt(i);
                i += 1;
            }
            response.column = Integer.parseInt(number);
            Assert.assertEquals(",\"figurineNumber\":", content.substring(i, i + 18));
            i += 18;
            number = "";
            while (content.charAt(i) != ',') {
                number += content.charAt(i);
                i += 1;
            }
            response.figurine = Integer.parseInt(number);
            Assert.assertEquals(",\"useGod\":", content.substring(i, i + 10));
            i += 10;
            if (content.substring(i, i + 4).equals("true")) {
                i += 5;
                response.withGod = true;
            } else {
                i += 6;
                response.withGod = false;
            }
            actions.add(response);
        }

        //looking that only allowed moves are here
        int movements1[][] = new int[5][5];
        int movements2[][] = new int[5][5];

        Response toPerform = null;

        for (i = 0; i < actions.size(); ++i) {
            Assert.assertTrue(actions.get(i).figurine == 1 || actions.get(i).figurine == 2);
            int row = actions.get(i).row;
            int col = actions.get(i).column;
            if (actions.get(i).figurine == 1) {
                movements1[row][col]++;
            } else if (actions.get(i).figurine == 2) {
                movements2[row][col]++;
            }
            if (actions.get(i).figurine == 2 && actions.get(i).withGod && row == 2 && col == 2) {
                toPerform = actions.get(i);
            }
        }

        //check if correct movements are saved in movements1 matrix
        Assert.assertEquals(movements1[0][0], 1);
        Assert.assertEquals(movements1[0][1], 0); //own position
        Assert.assertEquals(movements1[0][2], 1);
        Assert.assertEquals(movements1[1][0], 1);
        Assert.assertEquals(movements1[1][1], 2); //same level -> god move possible
        Assert.assertEquals(movements1[1][2], 1);

        //check if correct movements are saved in movements2 matrix
        Assert.assertEquals(movements2[1][2], 1);
        Assert.assertEquals(movements2[1][3], 1);
        Assert.assertEquals(movements2[1][4], 1);
        Assert.assertEquals(movements2[2][2], 2); //same level -> god move possible
        Assert.assertEquals(movements2[2][3], 0); //own position
        Assert.assertEquals(movements2[2][4], 1);
        Assert.assertEquals(movements2[3][2], 0); //opponent figurine
        Assert.assertEquals(movements2[3][3], 1);
        Assert.assertEquals(movements2[3][4], 1);

        //perform saved action
        mockMvc.perform(MockMvcRequestBuilders.put( "http://localhost:8080/game/actions/" + Long.toString(toPerform.id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        this.testGame = gameService.gameByID(this.gameId); //get changed game

        /*testGame.setStatus(GameStatus.GODMODE_STATE_STARTINGPLAYER);

        gameRepository.save(testGame);*/


        //get new actions
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get( "http://localhost:8080/game/actions/" + Long.toString(this.player1id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        content = mvcResult.getResponse().getContentAsString();

        //making sure we get the right return-code
        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);

        ArrayList<Response> actions2 = new ArrayList<Response>();

        //creating and checking the actions for consistency
        i = 0;
        while (content.charAt(i) != ']') {
            i += 1;
            Response response = new Response();
            Assert.assertEquals("{\"id\":", content.substring(i, i + 6));
            i += 6;
            String number = "";
            while (content.charAt(i) != ',') {
                number += content.charAt(i);
                i += 1;
            }
            i += 1;
            response.id = Integer.parseInt(number);
            if (content.substring(i, i + 30).equals("\"name\":\"movingAsHermes\",\"row\":")) {
                Assert.assertEquals("\"name\":\"movingAsHermes\",\"row\":", content.substring(i,i+30 ) );
                i += 30;
            } else {
                Assert.assertEquals("\"name\":\"Choose Moving Mode\",\"row\":", content.substring(i, i + 34));
                i += 34;
            }
            number = "";
            while (content.charAt(i) != ',') {
                number += content.charAt(i);
                i += 1;
            }
            response.row = Integer.parseInt(number);
            Assert.assertEquals(",\"column\":", content.substring(i, i + 10));
            i += 10;
            number = "";
            while (content.charAt(i) != ',') {
                number += content.charAt(i);
                i += 1;
            }
            response.column = Integer.parseInt(number);
            Assert.assertEquals(",\"figurineNumber\":", content.substring(i, i + 18));
            i += 18;
            number = "";
            while (content.charAt(i) != ',') {
                number += content.charAt(i);
                i += 1;
            }
            response.figurine = Integer.parseInt(number);
            Assert.assertEquals(",\"useGod\":", content.substring(i, i + 10));
            i += 10;
            if (content.substring(i, i + 4).equals("true")) {
                i += 5;
                response.withGod = true;
            } else {
                i += 6;
                response.withGod = false;
            }
            actions2.add(response);
        }

        //looking that only allowed moves are here
        int movements12[][] = new int[5][5];
        int movements22[][] = new int[5][5];


        for (i = 0; i < actions2.size(); ++i) {
            Assert.assertTrue(actions2.get(i).figurine == 1 || actions2.get(i).figurine == 2);
            int row = actions2.get(i).row;
            int col = actions2.get(i).column;
            if (actions2.get(i).figurine == 1) {
                movements12[row][col]++;
            } else if (actions2.get(i).figurine == 2) {
                movements22[row][col]++;
            }
            if (!(actions2.get(i).withGod )){
                toPerform = actions2.get(i);
            }
        }

        //check if correct movements are saved in movements12 matrix
        Assert.assertEquals(movements12[0][0], 0);
        Assert.assertEquals(movements12[0][1], 0); //own position
        Assert.assertEquals(movements12[0][2], 0);
        Assert.assertEquals(movements12[1][0], 0);
        Assert.assertEquals(movements12[1][1], 2); //same level
        Assert.assertEquals(movements12[1][2], 0);

        //check if correct movements are saved in movements22 matrix
        Assert.assertEquals(movements22[1][1], 2); //same level
        Assert.assertEquals(movements22[1][2], 0);
        Assert.assertEquals(movements22[1][3], 0);
        Assert.assertEquals(movements22[2][1], 2); //same level
        Assert.assertEquals(movements22[2][2], 0); //own position
        Assert.assertEquals(movements22[2][3], 2); //same level
        Assert.assertEquals(movements22[3][1], 0); //opponent figurine
        Assert.assertEquals(movements22[3][2], 0); //opponent figurine
        Assert.assertEquals(movements22[3][3], 0);

        //perform saved action
        mockMvc.perform(MockMvcRequestBuilders.put( "http://localhost:8080/game/actions/" + Long.toString(toPerform.id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        this.testGame = gameService.gameByID(this.gameId); //get changed game

        Assert.assertEquals(testGame.getStatus(), GameStatus.BUILDING_STARTINGPLAYER);
        /*
        //get new actions
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get( "http://localhost:8080/game/actions/" + Long.toString(this.player1id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        content = mvcResult.getResponse().getContentAsString();

        //making sure we get the right return-code
        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);

        Assert.assertEquals(content, "[]");
        //actions sollten == []*/


    }

    /**
     * Pan actions test
     * **/

    @Test
    public void PanActions() throws Exception {
        //building some structures
        testGame.getBoard().getSpaces()[0][0].build();
        testGame.getBoard().getSpaces()[0][0].build();

        //setting the figurines
        testGame.getStartingPlayer().getFigurine1().setPosition(0, 0);
        testGame.getStartingPlayer().getFigurine2().setPosition(3, 1);
        testGame.getNonStartingPlayer().getFigurine1().setPosition(1, 2);
        testGame.getNonStartingPlayer().getFigurine2().setPosition(4, 3);

        //assign god cards
        testGame.setStatus(GameStatus.PICKING_GODCARDS);
        testGame.getStartingPlayer().setAssignedGod(new Pan(testGame));
        testGame.getNonStartingPlayer().setAssignedGod(new Apollo(testGame));
        testGame.checkIfGameOver();
        testGame.setStatus(GameStatus.MOVING_STARTINGPLAYER);

        gameRepository.save(testGame);

        //get actions
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/game/actions/" + Long.toString(this.player1id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/game/actions/" + Long.toString(this.player1id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

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

        //looking that only allowed moves are here
        int movements1 [][] = new int [5][5];
        int movements2 [][] = new int [5][5];

        Response toPerform = null;

        for( i = 0; i < actions.size(); ++i){
            Assert.assertTrue(actions.get(i).figurine == 1 || actions.get(i).figurine == 2 );
            int row = actions.get(i).row;
            int col = actions.get(i).column;
            if(actions.get(i).figurine == 1){
                movements1[row][col]++;
                toPerform = actions.get(i);
            } else if(actions.get(i).figurine == 2){
                movements2[row][col]++;
            }
        }

        //check if correct movements are saved in movements matrix
        Assert.assertEquals(movements1[0][1], 1);

        //perform saved action
        mockMvc.perform(MockMvcRequestBuilders.put( "http://localhost:8080/game/actions/" + Long.toString(toPerform.id)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        //check if Pan-User has won
        this.testGame = gameService.gameByID(this.gameId); //get changed game
        Assert.assertEquals(testGame.getStatus(), GameStatus.STARTINGPLAYER_WON);


    }

}