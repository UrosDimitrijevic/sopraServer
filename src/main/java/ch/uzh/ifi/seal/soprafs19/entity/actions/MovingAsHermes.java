package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Hermes;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Entity;

@Entity
public class MovingAsHermes extends GodMovingAction {

    private boolean useGod;

    public MovingAsHermes (){
        this.setName("movingAsHermes");
    }

    public MovingAsHermes(Game game, Figurine figurine, int row, int column){
        super(game,figurine,row, column);
        this.setName("movingAsHermes");
        useGod = true;
    }


    public MovingAsHermes(Game game, Moving movingAction){
        super(game, game.retrivePlayers()[movingAction.retrivePlayerNumber()-1].retirveFigurines()[movingAction.getFigurineNumber()-1],movingAction.getRow(), movingAction.getColumn());
        this.setName("movingAsHermes");
        useGod = true;
    }

    public MovingAsHermes(Game game, Moving movingAction, boolean useGod){
        super(game, game.retrivePlayers()[movingAction.retrivePlayerNumber()-1].retirveFigurines()[movingAction.getFigurineNumber()-1],movingAction.getRow(), movingAction.getColumn());
        this.setName("movingAsHermes");
        this.useGod = useGod;
    }

    @java.lang.Override
    public boolean isUseGod(){
        return useGod;
    }

    @java.lang.Override
    public void perfromAction(GameService gameService) {
        super.perfromAction(gameService);
        Game game = gameService.gameByID(this.myGameId);
        if(game.getStatus().player() == 1){
            game.setStatus(GameStatus.GODMODE_STATE_STARTINGPLAYER);
            ((Hermes)game.getStartingPlayer().getAssignedGod()).setBuildingTime(!this.useGod);
        }
        else{
            game.setStatus(GameStatus.GODMODE_STATE_NONSTARTINGPLAYER);
            ((Hermes)game.getNonStartingPlayer().getAssignedGod()).setBuildingTime(!this.useGod);
        }
        gameService.saveGame(game);
    }
}
