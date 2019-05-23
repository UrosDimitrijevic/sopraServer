package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Prometheus;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Entity;

@Entity
public class MovingAsPrometheus extends GodMovingAction {

    public boolean useGod;

    @java.lang.Override
    public boolean isUseGod() {
        return useGod;
    }

    public MovingAsPrometheus(){
        super();
        this.name = "MovingAsPrometheus";
    }

    public MovingAsPrometheus(Game game, Figurine figurine, int row, int column){
        super(game,figurine,row,column);
        this.name = "MovingAsPrometheus";
        this.useGod = game.getStatus() == GameStatus.MOVING_NONSTARTINGPLAYER || game.getStatus() == GameStatus.MOVING_STARTINGPLAYER;
    }

    @java.lang.Override
    public void perfromAction(GameService gameService) {
        Game myGame = gameService.gameByID(this.myGameId);
        if(myGame.getStatus() == GameStatus.MOVING_NONSTARTINGPLAYER || myGame.getStatus() == GameStatus.MOVING_STARTINGPLAYER){
            myGame.getBoard().getSpaces()[this.row][this.column].build();
            if(myGame.getStatus().player() == 1){
                myGame.setStatus(GameStatus.GODMODE_STATE_STARTINGPLAYER);
            } else {
                myGame.setStatus(GameStatus.GODMODE_STATE_NONSTARTINGPLAYER);
            }
        } else {
            super.perfromAction(gameService);
            myGame = gameService.gameByID(this.myGameId);
            if(myGame.getStatus().player() == 1){
                //myGame.setStatus(GameStatus.GODMODE_STATE_STARTINGPLAYER);
                ((Prometheus)myGame.getStartingPlayer().getAssignedGod()).switchMode();
            } else {
                //myGame.setStatus(GameStatus.GODMODE_STATE_NONSTARTINGPLAYER);
                ((Prometheus)myGame.getNonStartingPlayer().getAssignedGod()).switchMode();
            }
        }
        gameService.saveGame(myGame);
    }
}
