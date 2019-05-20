package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Building;
import ch.uzh.ifi.seal.soprafs19.entity.actions.BuildingAsAtlas;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Moving;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;


@Entity
public class Atlas extends GodCard {


    @Id
    @GeneratedValue
    private Long id;




    public Atlas(){
        super();
    }


    public Atlas(Game game){
        super(game);
        this.godnumber = 4;
        this.name = "Atlas";
    }


    @java.lang.Override
    public ArrayList<Action> getActions(Game game) {
        //check status
        if(game.getStatus() != GameStatus.BUILDING_NONSTARTINGPLAYER && game.getStatus() != GameStatus.BUILDING_STARTINGPLAYER){ return null; }

        //figuring out which figurine moved
        ArrayList<Action> oldActions = game.retrivePerformedActions();

        int movedFigurine = game.retriveBuildingFigurine();

        ArrayList<Action> buildingactions = game.retrivePlayers()[game.getStatus().player()-1].retirveFigurines()[movedFigurine-1].getPossibleBuildingActions(game);
        ArrayList<Action> AtlasBuilding = new ArrayList<Action>();
        for(int i = 0; i < buildingactions.size(); ++i){
            Building buildAction = (Building) buildingactions.get(i);
            AtlasBuilding.add(new BuildingAsAtlas(game,buildAction.getRow(),buildAction.getColumn()));
        }


        return AtlasBuilding;
    }

    @java.lang.Override
    public Action getAction(Game game, Figurine figurine, int row, int column) {
        Action action = new BuildingAsAtlas(game,row,column);
        return action;
    }
}
