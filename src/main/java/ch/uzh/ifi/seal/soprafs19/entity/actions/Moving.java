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

    public int getColumn() {
        return column;
    }

    public int getFigurineNumber() {
        return figurineNumber;
    }

    public boolean isUseGod() {
        return false;
    }

    public Moving(){super(); }
    public Moving(Game game){ super(game); }

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
        Figurine figurine = myGame.retrivePlayers()[this.playerNumber-1].retirveFigurines()[this.figurineNumber-1];

        figurine.changePosition(this.row, this.column);

        if(myGame.getStatus().player() == 1){ myGame.setStatus(GameStatus.BUILDING_STARTINGPLAYER); }
        else{ myGame.setStatus(GameStatus.BUILDING_NONSTARTINGPLAYER); }

        gameService.saveGame(myGame);

    }

    public int retrivePlayerNumber() {
        return playerNumber;
    }

}
