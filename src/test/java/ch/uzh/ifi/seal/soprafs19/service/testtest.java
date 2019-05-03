package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Building;
import ch.uzh.ifi.seal.soprafs19.repository.ActionRepository;
import ch.uzh.ifi.seal.soprafs19.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertFalse;

public class testtest {
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
        testUser1.setUsername("testUsername1suqdg");
        testUser1.setPassword("testPassword1");
        testUser1.setBirthday("2000-01-01");
        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setUsername("testUsername2iapgzf");
        testUser2.setPassword("testPassword2");
        testUser2.setBirthday("2000-01-03");
        testUser1.setChallenging(testUser2.getId());
        testUser2.setChallenging(testUser1.getId());

        testUser2 = userService.createUser(testUser2);

        //setting up game mode
        Game testGame1 = new Game(testUser1, testUser2 );


        testGame1.getStartingPlayer().getFigurine1().setPosition(1,1);
        testGame1.getStartingPlayer().getFigurine2().setPosition(3,1);
        testGame1.getNonStartingPlayer().getFigurine1().setPosition(1,3);
        testGame1.getNonStartingPlayer().getFigurine1().setPosition(3,3);

        testGame1.setStatus(GameStatus.BUILDING_STARTINGPLAYER);
        Building tower = new Building(testGame1,0,4);// (0,4) randomly chosen
        tower.perfromAction(gameService);
        assertFalse(testGame1.getBoard().isEmpty(0,4));

    }
}
