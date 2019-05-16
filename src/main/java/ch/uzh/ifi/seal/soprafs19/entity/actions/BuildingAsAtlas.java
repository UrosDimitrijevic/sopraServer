package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.Space;
import ch.uzh.ifi.seal.soprafs19.service.GameService;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;

import javax.persistence.Entity;

@Entity
public class BuildingAsAtlas extends GodBuildingAction {
    public BuildingAsAtlas(){
        super();
        this.name = "BuildingAsAtlas";
    }

    public BuildingAsAtlas(Game game, int row, int column){
        super(game,row,column);
        this.name = "BuildingAsAtlas";
    }


    @java.lang.Override
    public void perfromAction(GameService gameService) {
        Game game = gameService.gameByID(this.myGameId);
        Space spaceToBuild = game.getBoard().getSpaces()[this.row][this.column];
        while(!spaceToBuild.isDome()){
            spaceToBuild.build();
        }
        if(game.getStatus() == GameStatus.BUILDING_NONSTARTINGPLAYER){
            game.setStatus(GameStatus.MOVING_STARTINGPLAYER);
        } else {
            game.setStatus(GameStatus.MOVING_NONSTARTINGPLAYER);
        }
        gameService.saveGame(game);
    }
}
