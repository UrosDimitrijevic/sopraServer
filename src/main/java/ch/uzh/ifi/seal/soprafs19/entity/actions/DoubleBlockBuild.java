package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Entity;

@Entity
public class DoubleBlockBuild extends GodBuildingAction {


    public DoubleBlockBuild(){
        super();
    }


    public DoubleBlockBuild (Game game, int row, int column){
        super(game, row, column);

        this.name = "Double Block Build";
    }


    @java.lang.Override
    public void perfromAction(GameService gameService) {
        Game myGame = gameService.gameByID(this.myGameId);

        myGame.getBoard().getSpaces()[row][column].build();
        myGame.getBoard().getSpaces()[row][column].build();

        if(myGame.getStatus().player() == 1){
            myGame.setStatus(GameStatus.MOVING_NONSTARTINGPLAYER);
        } else {
            myGame.setStatus(GameStatus.MOVING_STARTINGPLAYER);
        }

        gameService.saveGame(myGame);


    }





}
