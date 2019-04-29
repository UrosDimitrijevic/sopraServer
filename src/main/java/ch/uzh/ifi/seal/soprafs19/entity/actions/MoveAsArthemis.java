package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Artemis;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.GodCard;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Entity;

@Entity
public class MoveAsArthemis extends GodMovingAction {

    public MoveAsArthemis(Game game, Figurine figurine, int row, int column){
        super(game,figurine,row, column);
    }

    @java.lang.Override
    public void perfromAction(GameService gameService) {
        super.perfromAction(gameService);
        Game mygame = gameService.gameByID(this.myGameId);
        GodCard myGod = mygame.retrivePlayers()[this.playerNumber-1].getAssignedGod();
        if(myGod instanceof Artemis){
            if( ((Artemis) myGod).retriveDidUse() ){

            }
        }
    }
}
