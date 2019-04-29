package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.qos.logback.core.util.COWArrayList;
import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.entity.actions.MoveAsArthemis;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Moving;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;


@Entity
public class Artemis extends GodCard {


    @Id
    @GeneratedValue
    private Long id;

    public Artemis(){
        super();
    }

    int prev_row;
    int prev_column;
    int prev_figurine;

    public void setPrev_row(int prev_row) {
        this.prev_row = prev_row;
    }

    public void setPrev_column(int prev_column) {
        this.prev_column = prev_column;
    }

    public void setPrev_figurine(int prev_figurine) {
        this.prev_figurine = prev_figurine;
    }

    public Artemis(Game game){
        super(game);
        this.godnumber = 2;
        this.name = "Artemis";
        prev_row = -1;
        prev_column = -1;
        prev_figurine = -1;
    }


    @java.lang.Override
    public ArrayList<Action> getActions(Game game) {
        ArrayList<Action> possibleActions = new ArrayList<Action>();
        if(game.getStatus() == GameStatus.MOVING_NONSTARTINGPLAYER || game.getStatus() == GameStatus.MOVING_STARTINGPLAYER){

            ArrayList<Action> possibleMovements = game.retrivePlayers()[game.getStatus().player()-1].getFigurine1().getPossibleMovingActions(game);
            possibleMovements.addAll(game.retrivePlayers()[game.getStatus().player()-1].getFigurine2().getPossibleMovingActions(game));
            for(int i = 0; i < possibleMovements.size(); ++i){
                Moving currentAction = (Moving)possibleMovements.get(i);
                possibleActions.add(new MoveAsArthemis(game, currentAction));
            }
        }
        else{
            ArrayList<Action> possibleMovements = game.retrivePlayers()[game.getStatus().player()-1].retirveFigurines()[this.prev_figurine-1].getPossibleMovingActions(game);
            for(int i = 0; i < possibleMovements.size(); ++i){
                Moving currentAction = (Moving)possibleMovements.get(i);
                if( currentAction.getRow() != this.prev_row || currentAction.getColumn() != this.prev_column) {
                    possibleActions.add(new MoveAsArthemis(game, currentAction));
                }
            }

        }
        return possibleActions;
    }

    @java.lang.Override
    public Action getAction(Game game, Figurine figurine, int row, int column) {
        return new MoveAsArthemis(game,figurine,row,column);
    }

}
