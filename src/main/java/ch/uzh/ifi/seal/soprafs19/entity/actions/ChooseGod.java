package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.GodCard;
import ch.uzh.ifi.seal.soprafs19.entity.Player;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

public class ChooseGod extends Action {

    public ChooseGod(){
    }

    private GodCard god1;

    private GodCard god2;


    public ChooseGod(Game game, GodCard god1, GodCard god2 ){
        super();
        this.myGameId = game.getId();
        this.god1 = god1;
        this.god2 = god2;

    }


    public void perfromAction(GameService gameservice){
        Game myGame = gameservice.gameByID(this.myGameId);
        Player[] player = myGame.getPlayers();
        player[1].setAssignedGod(this.god1);
        player[2].setAssignedGod(this.god2);
        gameservice.saveGame(myGame);


    }

}
