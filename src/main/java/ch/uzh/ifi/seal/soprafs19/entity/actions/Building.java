package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Entity;

@Entity
public class Building extends Action {

    int row;
    int column;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }


    public Building(){

    }


    public Building (Game game, int row, int column){
        super(game);
        this.row = row;
        this.column = column;

        this.name = "Building";
    }


    @java.lang.Override
    public void perfromAction(GameService gameService) {
        Game myGame = gameService.gameByID(this.myGameId);

        myGame.getBoard().getSpaces()[row][column].build();

        if(myGame.getStatus() == GameStatus.BUILDING_STARTINGPLAYER){
            myGame.setStatus(GameStatus.MOVING_NONSTARTINGPLAYER);
        } else {
            myGame.setStatus(GameStatus.MOVING_STARTINGPLAYER);
        }

        gameService.saveGame(myGame);
    }
}
