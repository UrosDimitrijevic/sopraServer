package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.constant.PlayerStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.Player;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Entity;



@Entity
public class PlaceWorker2 extends Action {
    int row;
    int column;
    int figurineNumber;
    Player player;


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

    public PlaceWorker2(Game game, int i, int j){

    }

    public PlaceWorker2(Game game,Player player, int row, int column){
        super(game);
        this.row = row;
        this.column = column;
        this.player=player;

        this.name = "PlaceWorker";

    }

    @java.lang.Override
    public void perfromAction(GameService gameService) {
        Game myGame = gameService.gameByID(this.myGameId);
        Figurine figurine = this.player.retirveFigurines()[1];
        figurine.setPosition(this.row, this.column);

        if(myGame.getStatus() == GameStatus.SettingFigurinesp1f2){
            myGame.setStatus(GameStatus.SettingFigurinesp2f1);
        }

        if(myGame.getStatus() == GameStatus.SettingFigurinesp2f2){
            myGame.setStatus(GameStatus.MOVING_STARTINGPLAYER);
        }

        gameService.saveGame(myGame);
    }

}


