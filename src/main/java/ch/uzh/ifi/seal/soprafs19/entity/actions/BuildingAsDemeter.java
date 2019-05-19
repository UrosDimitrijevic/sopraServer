package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Demeter;
import ch.uzh.ifi.seal.soprafs19.entity.Space;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Entity;

@Entity
public class BuildingAsDemeter extends GodBuildingAction{

    boolean firstBuidl;

    public BuildingAsDemeter(){
        super();
        this.name = "BuildingAsDemeter";
    }

    public BuildingAsDemeter(Game game, int row, int column){
        super(game,row,column);
        this.name = "BuildingAsDemeter";
        this.firstBuidl = false;
    }

    public BuildingAsDemeter(Game game, int row, int column, boolean firstBuild){
        super(game,row,column);
        this.name = "BuildingAsDemeter";
        this.firstBuidl = firstBuild;
    }


    @java.lang.Override
    public void perfromAction(GameService gameService) {
        Game game = gameService.gameByID(this.myGameId);
        Space spaceToBuild = game.getBoard().getSpaces()[this.row][this.column];
        spaceToBuild.build();

        if( game.getStatus().player() == 2){
            if(this.firstBuidl){
                game.setStatus(GameStatus.GODMODE_STATE_NONSTARTINGPLAYER);
                ((Demeter)game.retrivePlayers()[1].getAssignedGod()).setSpace(this.row,this.column);
            }
            else{
                game.setStatus(GameStatus.MOVING_STARTINGPLAYER);
                ((Demeter)game.retrivePlayers()[1].getAssignedGod()).setSpace(-1,-1);
            }
        } else {
            if(this.firstBuidl){
                game.setStatus(GameStatus.GODMODE_STATE_STARTINGPLAYER);
                ((Demeter)game.retrivePlayers()[0].getAssignedGod()).setSpace(this.row,this.column);
            }
            else{
                game.setStatus(GameStatus.MOVING_NONSTARTINGPLAYER);
                ((Demeter)game.retrivePlayers()[0].getAssignedGod()).setSpace(this.row,this.column);
            }
        }
        gameService.saveGame(game);
    }
}
