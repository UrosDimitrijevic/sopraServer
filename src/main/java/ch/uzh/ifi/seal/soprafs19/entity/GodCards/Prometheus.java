package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.qos.logback.core.util.COWArrayList;
import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.Player;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Building;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Moving;
import ch.uzh.ifi.seal.soprafs19.entity.actions.MovingAsPrometheus;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;


@Entity
public class Prometheus extends GodCard {


    @Id
    @GeneratedValue
    private Long id;


    private boolean nowBuilding;

    public Prometheus(){

        super();
    }


    public Prometheus(Game game){
        super(game);
        this.godnumber = 10;
        this.name = "Prometheus";
        nowBuilding = false;
    }

    public void switchMode(){
        //nowBuilding = !nowBuilding;
    }

    public ArrayList<Action> sameLevelMovements(Game game, Figurine figurine) {
        ArrayList<Action> simpleMovements = figurine.getPossibleMovingActions(game);
        ArrayList<Action> sameLevelMoc = new ArrayList<Action>();
        int hight = figurine.retriveSpace().getLevel();
        for(int i = 0; i < simpleMovements.size(); ++i){
            Moving mov = (Moving) simpleMovements.get(i);
            int row = mov.getRow();
            int col = mov.getColumn();
            if(game.getBoard().getSpaces()[row][col].getLevel() == hight){
                sameLevelMoc.add(new MovingAsPrometheus(game,figurine,row,col));
            }
        }
        return sameLevelMoc;
    }

    @java.lang.Override
    public ArrayList<Action> getActions(Game game) {
        ArrayList<Action> possibleActions = new ArrayList<Action>();
        Player player = game.retrivePlayers()[game.getStatus().player()-1];
        if(game.getStatus() == GameStatus.MOVING_NONSTARTINGPLAYER || game.getStatus() == GameStatus.MOVING_STARTINGPLAYER){

            //Get normal buildings figurine 1
            Figurine figurine = player.getFigurine1();
            boolean canMove = this.sameLevelMovements(game,figurine).size() != 0;
            ArrayList<Action> simpleBuildings = figurine.getPossibleBuildingActions(game);
            for(int i = 0; i < simpleBuildings.size(); ++i){
                Building build = (Building) simpleBuildings.get(i);
                if( canMove) {
                    possibleActions.add(new MovingAsPrometheus(game, figurine, build.getRow(), build.getColumn()));
                } else {
                    int hight = figurine.retriveSpace().getLevel();
                    if(game.getBoard().getSpaces()[build.getRow()][build.getColumn()].getLevel() == hight-1){
                        possibleActions.add(new MovingAsPrometheus(game, figurine, build.getRow(), build.getColumn()));
                    }
                }
            }

            //Get normal buildings figurine 2
            figurine = player.getFigurine2();
            canMove = this.sameLevelMovements(game,figurine).size() != 0;
            simpleBuildings = figurine.getPossibleBuildingActions(game);
            for(int i = 0; i < simpleBuildings.size(); ++i){
                Building build = (Building) simpleBuildings.get(i);
                if( canMove) {
                    possibleActions.add(new MovingAsPrometheus(game, figurine, build.getRow(), build.getColumn()));
                } else {
                    int hight = figurine.retriveSpace().getLevel();
                    if(game.getBoard().getSpaces()[build.getRow()][build.getColumn()].getLevel() == hight-1){
                        possibleActions.add(new MovingAsPrometheus(game, figurine, build.getRow(), build.getColumn()));
                    }
                }
            }
        } else if(game.getStatus() == GameStatus.GODMODE_STATE_STARTINGPLAYER || game.getStatus() == GameStatus.GODMODE_STATE_NONSTARTINGPLAYER){
            int figNum = game.retriveBuildingFigurine();
            Figurine figurine = player.retirveFigurines()[figNum-1];
            possibleActions.addAll(this.sameLevelMovements(game,figurine));
        }
        return possibleActions;
    }

    @java.lang.Override
    public Action getAction(Game game, Figurine figurine, int row, int column) {
        return new MovingAsPrometheus(game,figurine,row,column);
    }
}
