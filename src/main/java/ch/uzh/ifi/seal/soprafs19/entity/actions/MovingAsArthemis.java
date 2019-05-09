package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Artemis;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Entity;

@Entity
public class MovingAsArthemis extends GodMovingAction {

    int oldrow;
    int oldcolumn;

    public MovingAsArthemis (){

    }

    public MovingAsArthemis(Game game, Figurine figurine, int row, int column){
        super(game,figurine,row, column);
        this.setName("movingAsArthemis");
        this.oldrow = game.retrivePlayers()[this.playerNumber-1].retirveFigurines()[this.figurineNumber-1].getPosition()[0];
        this.oldcolumn = game.retrivePlayers()[this.playerNumber-1].retirveFigurines()[this.figurineNumber-1].getPosition()[1];
    }

    public MovingAsArthemis(Game game, Moving movingAction){
        super(game, game.retrivePlayers()[movingAction.retrivePlayerNumber()-1].retirveFigurines()[movingAction.getFigurineNumber()-1],movingAction.getRow(), movingAction.getColumn());
        this.setName("movingAsArthemis");
        this.oldrow = game.retrivePlayers()[this.playerNumber-1].retirveFigurines()[this.figurineNumber-1].getPosition()[0];
        this.oldcolumn = game.retrivePlayers()[this.playerNumber-1].retirveFigurines()[this.figurineNumber-1].getPosition()[1];
    }

    @java.lang.Override
    public void perfromAction(GameService gameService) {
        super.perfromAction(gameService);

        Game mygame = gameService.gameByID(this.myGameId);
        if(playerNumber == 1){
            if(mygame.getStatus() == GameStatus.BUILDING_STARTINGPLAYER) {
                mygame.setStatus(GameStatus.GODMODE_STATE_STARTINGPLAYER);
                ((Artemis) mygame.retrivePlayers()[0].getAssignedGod()).setPrev_row(this.oldrow);
                ((Artemis) mygame.retrivePlayers()[0].getAssignedGod()).setPrev_column(this.oldcolumn);
                ((Artemis) mygame.retrivePlayers()[0].getAssignedGod()).setPrev_figurine(this.figurineNumber);
            }
            else{
                mygame.setStatus(GameStatus.BUILDING_STARTINGPLAYER);
                ((Artemis) mygame.retrivePlayers()[0].getAssignedGod()).setPrev_row(-1);
                ((Artemis) mygame.retrivePlayers()[0].getAssignedGod()).setPrev_column(-1);
                ((Artemis) mygame.retrivePlayers()[0].getAssignedGod()).setPrev_figurine(-1);
            }
        } else {
            if(mygame.getStatus() == GameStatus.BUILDING_NONSTARTINGPLAYER) {
                mygame.setStatus(GameStatus.GODMODE_STATE_NONSTARTINGPLAYER);
                ((Artemis) mygame.retrivePlayers()[1].getAssignedGod()).setPrev_row(this.oldrow);
                ((Artemis) mygame.retrivePlayers()[1].getAssignedGod()).setPrev_column(this.oldcolumn);
                ((Artemis) mygame.retrivePlayers()[1].getAssignedGod()).setPrev_figurine(this.figurineNumber);
            }
            else{
                mygame.setStatus(GameStatus.BUILDING_NONSTARTINGPLAYER);
                ((Artemis) mygame.retrivePlayers()[1].getAssignedGod()).setPrev_row(-1);
                ((Artemis) mygame.retrivePlayers()[1].getAssignedGod()).setPrev_column(-1);
                ((Artemis) mygame.retrivePlayers()[1].getAssignedGod()).setPrev_figurine(-1);
            }
        }
        gameService.saveGame(mygame);
    }
}