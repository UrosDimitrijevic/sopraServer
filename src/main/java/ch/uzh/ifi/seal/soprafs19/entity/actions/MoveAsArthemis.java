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

    public MoveAsArthemis (){

    }

    public MoveAsArthemis(Game game, Figurine figurine, int row, int column){
        super(game,figurine,row, column);
        this.name = "movingAsArthemis";
    }

    public MoveAsArthemis(Game game, Moving movingAction){
        super(game, game.retrivePlayers()[movingAction.retrivePlayerNumber()-1].retirveFigurines()[movingAction.getFigurineNumber()-1],movingAction.getRow(), movingAction.getColumn());
        this.name = "movingAsArthemis";
    }

    @java.lang.Override
    public void perfromAction(GameService gameService) {
        super.perfromAction(gameService);
        Game mygame = gameService.gameByID(this.myGameId);
        if(playerNumber == 1){
            if(mygame.getStatus() == GameStatus.BUILDING_STARTINGPLAYER) {
                mygame.setStatus(GameStatus.GODMODE_STATE_STARTINGPLAYER);
                ((Artemis) mygame.retrivePlayers()[0].getAssignedGod()).setPrev_row(this.row);
                ((Artemis) mygame.retrivePlayers()[0].getAssignedGod()).setPrev_column(this.column);
                ((Artemis) mygame.retrivePlayers()[0].getAssignedGod()).setPrev_figurine(this.figurineNumber);
            }
            else{
                ((Artemis) mygame.retrivePlayers()[0].getAssignedGod()).setPrev_row(-1);
                ((Artemis) mygame.retrivePlayers()[0].getAssignedGod()).setPrev_column(-1);
                ((Artemis) mygame.retrivePlayers()[0].getAssignedGod()).setPrev_figurine(-1);
            }
        } else {
            if(mygame.getStatus() == GameStatus.BUILDING_NONSTARTINGPLAYER) {
                mygame.setStatus(GameStatus.GODMODE_STATE_NONSTARTINGPLAYER);
                ((Artemis) mygame.retrivePlayers()[1].getAssignedGod()).setPrev_row(this.row);
                ((Artemis) mygame.retrivePlayers()[1].getAssignedGod()).setPrev_column(this.column);
                ((Artemis) mygame.retrivePlayers()[0].getAssignedGod()).setPrev_figurine(this.figurineNumber);
            }
            else{
                ((Artemis) mygame.retrivePlayers()[1].getAssignedGod()).setPrev_row(-1);
                ((Artemis) mygame.retrivePlayers()[1].getAssignedGod()).setPrev_column(-1);
                ((Artemis) mygame.retrivePlayers()[0].getAssignedGod()).setPrev_figurine(-1);
            }
        }
        gameService.saveGame(mygame);
    }
}
