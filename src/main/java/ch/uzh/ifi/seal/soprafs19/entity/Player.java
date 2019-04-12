package ch.uzh.ifi.seal.soprafs19.entity;

import ch.qos.logback.core.util.COWArrayList;
import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.entity.actions.ChooseGod;
import ch.uzh.ifi.seal.soprafs19.entity.actions.ChoseGameModeAction;
import org.springframework.boot.context.properties.source.IterableConfigurationPropertySource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class Player implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private long myUserID;

    private boolean startingplayer;

    private boolean GodMode;

    private GodCard assignedGod;

    @Column(nullable = false, length = 300)
    private Figurine figurine1;

    @Column(nullable = false, length = 300)
    private Figurine figurine2;

    public long getMyUserID() {
        return myUserID;
    }

    public Figurine getFigurine1() {
        return figurine1;
    }

    public Figurine getFigurine2() {
        return figurine2;
    }


    public Player(User me, Board board, boolean doIstart){
        this.myUserID = me.getId();
        this.startingplayer = doIstart;
        this.figurine1 = new Figurine(this,board,1);
        this.figurine2 = new Figurine(this,board,2);
    }

    public boolean isStartingplayer() {
        return startingplayer;
    }

    public ArrayList<Action> getPossibleActions(Game game){
        ArrayList<Action> possibleActions = new ArrayList<>();
        if( game.getStatus() == GameStatus.CHOSING_GAME_MODE && startingplayer){
            possibleActions.add(new ChoseGameModeAction(game, true));
            possibleActions.add(new ChoseGameModeAction(game, false));
        }else if (game.getStatus() == GameStatus.CHOSING_GODCARDS && startingplayer){

            for(int i=1;i<10;i++){
                for(int j=i+1;j<=10;j++)
                possibleActions.add(new ChooseGod(game,i,j));
            }

        }

        return possibleActions;
    }


    public void setAssignedGod(GodCard assignedGod) {
        this.assignedGod = assignedGod;
    }

}
