package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.GodCard;
import ch.uzh.ifi.seal.soprafs19.entity.Player;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Iterator;


@Entity
public class ChooseGod extends Action {

    @Column(nullable = true, length = 800)
    private GodCard god1;

    @Column(nullable = true, length = 800)
    private GodCard god2;

    public ChooseGod(){

    }

    public ChooseGod(Game game, GodCard god1, GodCard god2 ){
        super();
        this.name = "ChosingGod";
        this.myGameId = game.getId();
        this.god1 = god1;
        this.god2 = god2;

    }

    public GodCard getGod1() {
        return god1;
    }

    public GodCard getGod2() {
        return god2;
    }

    @java.lang.Override
    public void perfromAction(GameService gameservice){
        Game myGame = gameservice.gameByID(this.myGameId);
        Player[] player = myGame.getPlayers();
        player[0].setAssignedGod(this.god1);
        player[1].setAssignedGod(this.god2);
        myGame.setStatus(GameStatus.PICKING_GODCARDS);
        gameservice.saveGame(myGame);
    }

}
