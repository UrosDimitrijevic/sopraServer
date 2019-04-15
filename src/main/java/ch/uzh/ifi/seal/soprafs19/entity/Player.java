package ch.uzh.ifi.seal.soprafs19.entity;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.GodCard;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.entity.actions.ActionCreater;
import ch.uzh.ifi.seal.soprafs19.entity.actions.ChooseGod;
import ch.uzh.ifi.seal.soprafs19.entity.actions.ChoseGameModeAction;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Apollo;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Artemis;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Athena;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Atlas;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Demeter;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Hephastephus;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Hermes;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Minotaur;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Pan;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.Prometheus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

@Entity
public class Player implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private long myUserID;

    private boolean startingplayer;

    private boolean GodMode;

    public GodCard getAssignedGod() {
        return assignedGod;
    }

    @Column(nullable = false, length = 800)
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
            /*ArrayList<GodCard> gods = new ArrayList<GodCard>();
            GodCard Apollo1 = new Apollo();
            Artemis Artemis1 = new Artemis();
            Athena Athena1 = new Athena();
            Atlas Atlas1 = new Atlas();
            Demeter Demeter1 = new Demeter();
            Hephastephus Hephastephus1 = new Hephastephus();
            Hermes Hermes1 = new Hermes();
            Minotaur Minotaur1 = new Minotaur();
            Pan Pan1 = new Pan();
            Prometheus Prometheus1 = new Prometheus();
            gods.add(Apollo1);
            gods.add(Artemis1);
            gods.add(Athena1);
            gods.add(Atlas1);
            gods.add(Demeter1);
            gods.add(Hephastephus1);
            gods.add(Hermes1);
            gods.add(Minotaur1);
            gods.add(Pan1);
            gods.add(Prometheus1);

            Iterator<GodCard> godIterator = gods.iterator();
            Iterator<GodCard> i= godIterator;
            Iterator<GodCard> j= (Iterator<GodCard>) i.next();
            while(i.hasNext()){
                while(j!=null){
                    GodCard v= (GodCard) i;
                    GodCard w= (GodCard) j;
                    possibleActions.add(new ChooseGod(game, v, w));
                }
            }*/
            possibleActions.addAll(ActionCreater.createChoseGodActions(game));
        }
        else if(game.getStatus() == GameStatus.PICKING_GODCARDS && !this.startingplayer){
            possibleActions.addAll(ActionCreater.createPickGodActions(game));
        }
        return possibleActions;
    }


    public void setAssignedGod(GodCard assignedGod) {
        this.assignedGod = assignedGod;
    }
}




