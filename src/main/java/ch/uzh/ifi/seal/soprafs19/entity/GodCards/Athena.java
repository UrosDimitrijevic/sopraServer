package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.Player;
import ch.uzh.ifi.seal.soprafs19.entity.Space;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Moving;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;


@Entity
public class Athena extends GodCard {


    @Id
    @GeneratedValue
    private Long id;


    private boolean canEnemyMoveUp;
    private int previousHight[];

    public Athena(){
        super();
        canEnemyMoveUp = true;
        previousHight = new int[2];
        previousHight[0] = 0;
        previousHight[1] = 0;
    }

    public boolean didWin(Game game, Player player) {
        if(game.retrivePerformedActions().size() != 0){
            Action lastAction = game.retrivePerformedActions().get(game.retrivePerformedActions().size()-1);
            if(lastAction instanceof Moving && ((Moving)lastAction).retrivePlayerNumber() == player.getPlayerNumber() ){
                int figNumber = ((Moving) lastAction).getFigurineNumber() -1;
                Figurine figurine = player.retirveFigurines()[figNumber];
                if(figurine.retriveSpace().getLevel() > previousHight[figNumber]){
                    this.canEnemyMoveUp = false;
                }
                else{
                    this.canEnemyMoveUp = true;
                }
            }
            if(previousHight == null){System.out.print("previousHight is null\n");}
            //else if(player == null){System.out.print("player is null\n");}
            //else if(player.getFigurine1() == null){System.out.print("player.getFigrurine1 is null\n");}
            //else if(player.getFigurine1().retriveSpace() == null){System.out.print("player.getFigrurine1.retriveSpace is null\n");}
            previousHight[0] = player.getFigurine1().retriveSpace().getLevel();
            previousHight[1] = player.getFigurine2().retriveSpace().getLevel();
        }

        return false;
    }


    public Athena(Game game){
        super(game);
        this.godnumber = 3;
        this.name = "Athena";
        canEnemyMoveUp = true;
        previousHight = new int[2];
        previousHight[0] = 0;
        previousHight[1] = 0;
    }


    @java.lang.Override
    public ArrayList<Action> getActions(Game game) {
        return null;
    }

    @java.lang.Override
    public Action getAction(Game game, Figurine figurine, int row, int column) {
        return null;
    }

    @java.lang.Override
    public void removeEnemyActions(ArrayList<Action> list,Game game){

        if(!this.canEnemyMoveUp) {
            for (int i = 0; i < list.size(); ++i) {
                Action action = list.get(i);
                if (action instanceof Moving) {
                    Player player = game.retrivePlayers()[((Moving) action).retrivePlayerNumber() - 1];
                    Figurine figurine = player.retirveFigurines()[((Moving) action).getFigurineNumber() - 1];
                    Space oldSpace = figurine.retriveSpace();
                    Space newSpace = game.getBoard().getSpaces()[((Moving) action).getRow()][((Moving) action).getColumn()];
                    if(oldSpace.getLevel() < newSpace.getLevel()){
                        list.remove(i);
                        --i;
                    }
                }
            }
        }
        return;
    }
}