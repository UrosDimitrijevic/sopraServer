package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

public class DoubleBlockBuild extends Action {

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

    public boolean isUseGod(){
        return false;
    }

    public DoubleBlockBuild(){
        super();
    }


    public DoubleBlockBuild (Game game, int row, int column){
        super(game);
        this.row = row;
        this.column = column;

        this.name = "Double Block Build";
    }


    @java.lang.Override
    public void perfromAction(GameService gameService) {
        Game myGame = gameService.gameByID(this.myGameId);

        myGame.getBoard().getSpaces()[row][column].build();
        myGame.getBoard().getSpaces()[row][column].build();

        gameService.saveGame(myGame);


    }





}
