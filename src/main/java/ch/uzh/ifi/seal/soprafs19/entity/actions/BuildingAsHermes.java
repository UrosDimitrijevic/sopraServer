package ch.uzh.ifi.seal.soprafs19.entity.actions;
import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.Space;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Entity;

@Entity
public class BuildingAsHermes extends GodBuildingAction{

    public BuildingAsHermes(){
        super();
        this.name = "BuildingAsHermes";
    }

    public BuildingAsHermes(Game game, int row, int column){
        super(game,row,column);
        this.name = "BuildingAsHermes";
    }


    @java.lang.Override
    public void perfromAction(GameService gameService) {
        Game game = gameService.gameByID(this.myGameId);
        Space spaceToBuild = game.getBoard().getSpaces()[this.row][this.column];
        spaceToBuild.build();

        if( game.getStatus().player() == 2){
                game.setStatus(GameStatus.MOVING_STARTINGPLAYER);
        } else {
                game.setStatus(GameStatus.MOVING_NONSTARTINGPLAYER);
        }
        gameService.saveGame(game);
    }


    @java.lang.Override
    public boolean isUseGod(){
        return false;
    }
}
