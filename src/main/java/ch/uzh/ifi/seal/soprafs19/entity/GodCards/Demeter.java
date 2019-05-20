package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.Space;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Building;
import ch.uzh.ifi.seal.soprafs19.entity.actions.BuildingAsAtlas;
import ch.uzh.ifi.seal.soprafs19.entity.actions.BuildingAsDemeter;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;


@Entity
public class Demeter extends GodCard {


    @Id
    @GeneratedValue
    private Long id;


    int alreadyBuildRow;
    int alreadyBuildcolumn;


    public Demeter(){
        super();
    }


    public Demeter(Game game){
        super(game);
        this.godnumber = 5;
        this.name = "Demeter";
        alreadyBuildRow = -1;
        alreadyBuildcolumn = -1;
    }


    @java.lang.Override
    public ArrayList<Action> getActions(Game game) {
        //check status
        if(game.getStatus() != GameStatus.BUILDING_NONSTARTINGPLAYER && game.getStatus() != GameStatus.BUILDING_STARTINGPLAYER
            && game.getStatus() != GameStatus.GODMODE_STATE_STARTINGPLAYER && game.getStatus() != GameStatus.GODMODE_STATE_NONSTARTINGPLAYER
        ){ return null; }

        //figuring out which figurine moved
        ArrayList<Action> oldActions = game.retrivePerformedActions();

        int movedFigurine = game.retriveBuildingFigurine();

        ArrayList<Action> buildingactions = game.retrivePlayers()[game.getStatus().player()-1].retirveFigurines()[movedFigurine-1].getPossibleBuildingActions(game);
        ArrayList<Action> AtlasBuilding = new ArrayList<Action>();
        for(int i = 0; i < buildingactions.size(); ++i){
            Building buildAction = (Building) buildingactions.get(i);
            int row = buildAction.getRow();
            int column = buildAction.getColumn();
            if( !(row == this.alreadyBuildRow && column == this.alreadyBuildcolumn) ) {
                AtlasBuilding.add(new BuildingAsDemeter(game, row, column, this.alreadyBuildRow == -1));
            }
        }

        return AtlasBuilding;
    }



    public void setSpace(int row, int column){
            this.alreadyBuildRow = row;
            this.alreadyBuildcolumn = column;
    }

    @java.lang.Override
    public Action getAction(Game game, Figurine figurine, int row, int column) {
        Action action = new BuildingAsAtlas(game,row,column);
        return action;
    }
}
