package ch.uzh.ifi.seal.soprafs19.entity;
import ch.qos.logback.core.util.COWArrayList;
import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.entity.actions.ChoseGameModeAction;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Moving;
import ch.uzh.ifi.seal.soprafs19.service.ActionService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Random;
import java.io.Serializable;
import java.util.ArrayList;


@Entity
public class Game  implements Serializable  {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true, nullable = false)
    private long player1id;


    @Column(unique = true, nullable = false)
    private long player2id;

    @Column(nullable = true, length = 2000)
    ArrayList<Long> actions1;



    @Column(nullable = true, length = 2000)
    ArrayList<Long> actions2;

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    @Column(nullable = false)
    private GameStatus status;

    @Column(nullable = true)
    private boolean playWithGodCards;

    @Column(nullable = false, length = 8000)
    private Board board;

    @Column(nullable = false, length = 4000)
    private Player players[];

    @Column(nullable = false, length = 400000)
    private ArrayList<Action> performedActions;

    public ArrayList<Action> retrivePerformedActions() {
        return performedActions;
    }

    private boolean DoesP1Start(User u1, User u2){
        Random rand = new Random();
        if( u1.getBirthday() == null){
            return false;
        }
        if( u2.getBirthday() == null){
            return true;
        }
        if( u1.getBirthday().compareTo( u2.getBirthday() ) > 0){
            return true;
        }
        if (u1.getBirthday().compareTo( u2.getBirthday() ) < 0){
            return false;
        }
        return (rand.nextInt(2) == 1)?true:false;
    }

    public Game(){
    }

    public ArrayList<Long> retriveActions1() {
        return actions1;
    }

    public void setActions1(ArrayList<Long> actions1) {
        this.actions1 = actions1;
    }

    public ArrayList<Long> retriveActions2() {
        return actions2;
    }

    public void setActions2(ArrayList<Long> actions2) {
        this.actions2 = actions2;
    }

    public long getPlayer1id() {
        return player1id;
    }

    public long getPlayer2id() {
        return player2id;
    }

    public boolean isPlayWithGodCards() {
        return playWithGodCards;
    }

    public Board getBoard() {
        return board;
    }

    public Player[] retrivePlayers() {
        return players;
    }

    public void setBoardForFigurines(){
        this.players[0].setBoardforFigurines(this.board);
        this.players[1].setBoardforFigurines(this.board);
    }

    public Game(User user1, User user2){
        this.status = GameStatus.CHOSING_GAME_MODE;
        this.board = new Board();
        this.players = new Player[2];
        boolean doesP1start = this.DoesP1Start(user1,user2);
        this.playWithGodCards = false;

        if( doesP1start) {
            this.players[0] = new Player(user1, this.board, true, 1);
            this.players[1] = new Player(user2, this.board, false, 2);
            this.player1id = user1.getId();
            this.player2id = user2.getId();
        } else {
            this.players[0] = new Player(user2, this.board, true, 1);
            this.players[1] = new Player(user1, this.board, false, 2);
            this.player1id = user2.getId();
            this.player2id = user1.getId();
        }
        user1.setStatus(UserStatus.INGAME);
        user2.setStatus(UserStatus.INGAME);

        this.performedActions = new ArrayList<Action>();
    }

    public Player getStartingPlayer(){
        if (players[0].isStartingplayer()) {
            return players[0];
        }else{
            return players[1];
        }
    }

    public Player getNonStartingPlayer(){
        if (players[0].isStartingplayer()) {
            return players[1];
        }else{
            return players[0];
        }
    }

    public long getCurrentPlayer(){
        /*if( this.status == GameStatus.MOVING_NONSTARTINGPLAYER || this.status == GameStatus.BUILDING_NONSTARTINGPLAYER || this.status == GameStatus.PICKING_GODCARDS
          || this.status == GameStatus.SettingFigurinesp2f2 || this.status == GameStatus.SettingFigurinesp2f1){
            return this.player2id;
        }
        else{
            return this.player1id;
        }*/
        if(this.status.player() == 1){
            return this.player1id;
        }else{
            return this.player2id;
        }

    }

    public void setPlayWithGodCards(boolean playWithGodCards) {
        this.playWithGodCards = playWithGodCards;
    }

    public int getLvlAt(int column, int row){
        return this.board.getLvlAt(column, row);
    }

    public long getId(){
        return this.id;
    }


    public GameStatus getStatus(){ return this.status; }

    public Iterable<Action> getPossibleActions(long playerid){
        ArrayList<Action> possibleActions = new ArrayList<>();
        if(player1id == playerid){
            possibleActions.addAll( players[0].getPossibleActions(this) );
        }
        else{
            possibleActions.addAll( players[1].getPossibleActions(this) );
        }
        return possibleActions;
    }

    public int retriveBuildingFigurine(){
        int movedFigurine = 1;
        for( int i = performedActions.size()-1; i >= 0; --i){
            Action action = performedActions.get(i);
            //System.out.println("iterated over one action\n");
            if( action instanceof Moving){
                //System.out.println("is a instance of moving with figurine: " + Integer.toString( ((Moving) action).getFigurineNumber() ));
                movedFigurine = ((Moving) action).getFigurineNumber();
                break;
            }
        }
        return movedFigurine;
    }

    public void removeActions(ArrayList<Action> actions, int playerNumber){
        if(!playWithGodCards){
            return;
        }
        if(playerNumber == 1){
            this.players[1].getAssignedGod().removeEnemyActions(actions,this);
        } else {
            this.players[0].getAssignedGod().removeEnemyActions(actions,this);
        }
        return;
    }

    public void checkIfGameOver(){
        boolean didP1loose = this.players[0].didLoose(this);
        boolean didP2losse = this.players[1].didLoose(this);
        if(this.players[0].didWin(this) ){ this.setStatus(GameStatus.STARTINGPLAYER_WON); }
        else if(didP1loose && (!didP2losse || this.status.nextMovingPlayer() == 1)) {
            this.setStatus(GameStatus.NONSTARTINGPLAYER_WON);
        }
        else if(this.players[1].didWin(this) ){ this.setStatus(GameStatus.NONSTARTINGPLAYER_WON); }
        else if(didP2losse && (!didP2losse || this.status.nextMovingPlayer() == 2)) {
            this.setStatus(GameStatus.STARTINGPLAYER_WON); }
    }

    public void addAction(Action action){
        if( performedActions.size() > 100){
            
        }
        performedActions.add(action);
    }

    public long getGameId(){ return this.id;}

}
