package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Entity;

@Entity
public class MovingAsApollo extends GodMovingAction {
    public MovingAsApollo (){
        super();
    }

    public MovingAsApollo(Game game, Figurine figurine, int row, int column){
        super(game, figurine, row, column);
    }

    @java.lang.Override
    public void perfromAction(GameService gameService) {
        Game game = gameService.gameByID(this.myGameId);
        Figurine myFigurine = game.retrivePlayers()[this.playerNumber-1].retirveFigurines()[this.figurineNumber-1];
        int oldRow = myFigurine.getPosition()[0];
        int oldColumn = myFigurine.getPosition()[1];

        int enemy[] = game.getBoard().getSpaces()[this.row][this.column].retriveFigurine();
        if(enemy == null){ return; }

        Figurine enemyFigurine = game.retrivePlayers()[enemy[0]-1].retirveFigurines()[enemy[1]-1];

        myFigurine.changePosition(this.row, this.column);
        enemyFigurine.setPosition(oldRow,oldColumn);

        if(this.playerNumber == 1){game.setStatus(GameStatus.BUILDING_STARTINGPLAYER);}
        else{ game.setStatus(GameStatus.BUILDING_NONSTARTINGPLAYER); }
        gameService.saveGame(game);
    }
}
