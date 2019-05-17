package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.actions.*;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;


@Entity
public class Hermes extends GodCard {


    @Id
    @GeneratedValue
    private Long id;

    private boolean buildingTime;

    public Hermes(){
        super();
    }


    public Hermes(Game game){
        super(game);
        this.godnumber = 7;
        this.name = "Hermes";
        buildingTime = false;
    }

    public void setBuildingTime(boolean buildingTime) {
        this.buildingTime = buildingTime;
    }

    @java.lang.Override
    public ArrayList<Action> getActions(Game game) {
        ArrayList<Action> godActions = new ArrayList<Action>();

        if(game.getStatus() == GameStatus.MOVING_STARTINGPLAYER || game.getStatus() == GameStatus.MOVING_NONSTARTINGPLAYER
            || game.getStatus() == GameStatus.GODMODE_STATE_STARTINGPLAYER || game.getStatus() == GameStatus.GODMODE_STATE_NONSTARTINGPLAYER) {
            //moving
            if(this.buildingTime == false) {
                this.buildingTime = true;
                boolean alreadyMoved = game.getStatus() == GameStatus.GODMODE_STATE_NONSTARTINGPLAYER || game.getStatus() == GameStatus.GODMODE_STATE_STARTINGPLAYER;

                Figurine figurines[] = game.retrivePlayers()[game.getStatus().player() - 1].retirveFigurines();

                ArrayList<Action> possibleMovements = game.retrivePlayers()[game.getStatus().player() - 1].getFigurine1().getPossibleMovingActions(game);
                possibleMovements.addAll(game.retrivePlayers()[game.getStatus().player() - 1].getFigurine2().getPossibleMovingActions(game));

                for (int i = 0; i < possibleMovements.size(); ++i) {
                    Moving action = (Moving) possibleMovements.get(i);
                    int newRow = action.getRow();
                    int newcolumn = action.getColumn();
                    int oldhight = figurines[action.getFigurineNumber() - 1].retriveSpace().getLevel();
                    int newhight = game.getBoard().getLvlAt(newRow, newcolumn);
                    if (oldhight == newhight) {
                        godActions.add(new MovingAsHermes(game, action));
                        if (alreadyMoved) {
                            godActions.add(new MovingAsHermes(game,action,false));
                        }
                    }
                }
            }
            //building
            else{
                int board [][] = new int [5][5];
                System.out.print("enters into special building\n");
                this.buildingTime = false;
                Figurine figurines [] = game.retrivePlayers()[game.getStatus().player()-1].retirveFigurines();
                ArrayList<Action> possibleBuildings = figurines[0].getPossibleBuildingActions(game);
                possibleBuildings.addAll(figurines[1].getPossibleBuildingActions(game));
                for(int i = 0; i < possibleBuildings.size(); ++i){
                    Building action = (Building)possibleBuildings.get(i);
                    int row = action.getRow();
                    int column = action.getColumn();
                    if(board[row][column] == 0) {
                        godActions.add(new BuildingAsHermes(game, row, column));
                        board[row][column]++;
                    }
                }
            }
        }
        return godActions;
    }

    @java.lang.Override
    public Action getAction(Game game, Figurine figurine, int row, int column) {
        return new MovingAsHermes(game,figurine,row,column);
    }
}
