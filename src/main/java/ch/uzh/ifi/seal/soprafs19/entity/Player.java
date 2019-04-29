package ch.uzh.ifi.seal.soprafs19.entity;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.constant.PlayerStatus;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.GodCard;
import ch.uzh.ifi.seal.soprafs19.entity.actions.*;
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

    public int getPlayerNumber() {

        return playerNumber;
    }

    private int playerNumber;


    @Column
    private PlayerStatus  plstatus;


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

    public Figurine retirveFigurines() [] {
            Figurine figurines [] = new Figurine[2];
            figurines[0] = this.figurine1;
            figurines[1] = this.figurine2;
            return figurines;
    }

    public void setBoardforFigurines(Board board){
        this.figurine1.setBoard(board);
        this.figurine2.setBoard(board);
    }

    public Player(User me, Board board, boolean doIstart, int playerNumber){
        this.myUserID = me.getId();
        this.startingplayer = doIstart;
        this.playerNumber = playerNumber;
        this.figurine1 = new Figurine(this,board,1);
        this.figurine2 = new Figurine(this,board,2);
        this.activePlayer();
    }


    public boolean isStartingplayer() {
        return startingplayer;
    }

    private void activePlayer(){
        if(this.startingplayer){
            this.plstatus=PlayerStatus.ACTIVE;
        }else{
            this.plstatus=PlayerStatus.PASSIVE;
        }

    }

    public boolean didWin(){
        if( this.assignedGod == null) { return (this.figurine2.didWin() || this.figurine1.didWin() ); }
        else { return (this.figurine2.didWin() || this.figurine1.didWin() || this.assignedGod.didWin() ); }
    }

    public boolean didLoose(){
        if( this.assignedGod == null) { return (this.figurine2.didLoose() && this.figurine1.didLoose() ); }
        else { return (this.figurine2.didLoose() && this.figurine1.didLoose() && this.assignedGod.didLoose()); }
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
        else if( (game.getStatus() == GameStatus.MOVING_STARTINGPLAYER && this.startingplayer) || (game.getStatus() == GameStatus.MOVING_NONSTARTINGPLAYER && !this.startingplayer) ){
            //possibleActions.addAll(ActionCreater.createMovementActions(game, this));
            possibleActions.addAll(ActionCreater.createChooseModeMovementsActions(game, this));

        }
        else if( (game.getStatus() == GameStatus.BUILDING_STARTINGPLAYER && this.startingplayer) || (game.getStatus() == GameStatus.BUILDING_NONSTARTINGPLAYER && !this.startingplayer) ){
            possibleActions.addAll(ActionCreater.createBuildingActions(game, this));
        }
        else if( (game.getStatus() == GameStatus.SettingFigurinesp1f1 && this.startingplayer)|| (game.getStatus() == GameStatus.SettingFigurinesp2f1 && !this.startingplayer) ){
            possibleActions.addAll(ActionCreater.createPlaceWorkerActions(game,this));
        }
        else if( (game.getStatus() == GameStatus.SettingFigurinesp1f2 && this.startingplayer) || (game.getStatus() == GameStatus.SettingFigurinesp2f2 && !this.startingplayer) ){
            possibleActions.addAll(ActionCreater.createPlaceWorker2Actions(game,this));
        }


        return possibleActions;
    }

    public void setAssignedGod(GodCard assignedGod) {
        this.assignedGod = assignedGod;
    }

}