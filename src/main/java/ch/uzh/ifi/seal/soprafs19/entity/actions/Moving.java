package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Entity;

@Entity
public class Moving extends Action {

    int row;
    int column;
    int figurineNumber;

    int playerNumber;

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

    public int getFigurineNumber() {
        return figurineNumber;
    }

    public void setFigurineNumber(int figurineNumber) {
        this.figurineNumber = figurineNumber;
    }

    public Moving(){

    }

    public Moving (Game game, Figurine figurine, int row, int column){
        super(game);
        this.row = row;
        this.column = column;

        this.name = "Moving";
        this.figurineNumber = figurine.getFigurineNumber();
        this.playerNumber = figurine.retrivePlayerNumber();
    }

    @java.lang.Override
    public void perfromAction(GameService gameService) {
        Game myGame = gameService.gameByID(this.myGameId);
        Figurine figurine = myGame.getPlayers()[this.playerNumber-1].getFigurines()[this.figurineNumber-1];

        figurine.changePosition(this.row, this.column);

        if(myGame.getStatus() == GameStatus.MOVING_STARTINGPLAYER){ myGame.setStatus(GameStatus.BUILDING_STARTINGPLAYER); }
        else{ myGame.setStatus(GameStatus.BUILDING_NONSTARTINGPLAYER); }

        gameService.saveGame(myGame);
    }

}
