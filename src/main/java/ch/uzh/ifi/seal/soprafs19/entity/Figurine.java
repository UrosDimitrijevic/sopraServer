package ch.uzh.ifi.seal.soprafs19.entity;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Building;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Moving;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class Figurine implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private boolean HasMoved;

    @Column(nullable = true, length = 2000  )
    private Board board;

    private Player player;
    int playerNumber;
    int figurineNumber;

    @Column(nullable = false, length = 100)
    int position[];

    @Column(nullable = true, length = 100)
    private Space space;


    public int getFigurineNumber() {
        return figurineNumber;
    }



    public void setBoard(Board board) {
        this.board = board;
    }

    public int retrivePlayerNumber(){
        return this.playerNumber;
    }

    public int[] getPosition() {
        return position;
    }

    public Board retirveBoard() {
        return board;
    }

    public Figurine(Player player, Board board, int figurineNumber){
        this.board = board;
        this.player = player;
        this.figurineNumber = figurineNumber;
        this.playerNumber = this.player.getPlayerNumber();
        board.setFigurine(this,this.playerNumber,this.figurineNumber);
        this.HasMoved = false;
    }

    public void setPosition(int row, int column){
        this.position = new int [2];
        this.position[0] = row;
        this.position[1] = column;
        board.getSpaces()[row][column].setFigurine(this);
        space = board.getSpaces()[row][column];
    }

    public void changePosition(int row, int column){
        board.getSpaces()[this.position[0]][this.position[1]].setFigurine(null);
        this.position[0] = row;
        this.position[1] = column;
        board.getSpaces()[row][column].setFigurine(this);
        space = board.getSpaces()[row][column];
    }


    public ArrayList<Action> getPossibleMovingActions(Game game) {
        ArrayList<Action> possibleActions = new ArrayList<>();
        if(board.isWalkeable(this.position[0]-1,this.position[1]-1,this.space.getLevel()) ){
            possibleActions.add(new Moving(game, this, this.position[0]-1, this.position[1]-1 ) );
        }
        if(board.isWalkeable(this.position[0]-1,this.position[1],this.space.getLevel()) ){
            possibleActions.add(new Moving(game, this, this.position[0]-1, this.position[1] ) );
        }
        if(board.isWalkeable(this.position[0]-1,this.position[1]+1,this.space.getLevel()) ){
            possibleActions.add(new Moving(game, this, this.position[0]-1, this.position[1]+1 ) );
        }
        if(board.isWalkeable(this.position[0]+1,this.position[1]-1,this.space.getLevel()) ){
            possibleActions.add(new Moving(game, this, this.position[0]+1, this.position[1]-1 ) );
        }
        if(board.isWalkeable(this.position[0]+1,this.position[1],this.space.getLevel()) ){
            possibleActions.add(new Moving(game, this, this.position[0]+1, this.position[1] ) );
        }
        if(board.isWalkeable(this.position[0]+1,this.position[1]+1,this.space.getLevel()) ){
            possibleActions.add(new Moving(game, this, this.position[0]+1, this.position[1]+1 ) );
        }
        if(board.isWalkeable(this.position[0],this.position[1]-1,this.space.getLevel()) ){
            possibleActions.add(new Moving(game, this, this.position[0], this.position[1]-1 ) );
        }
        if(board.isWalkeable(this.position[0],this.position[1]+1,this.space.getLevel()) ){
            possibleActions.add(new Moving(game, this, this.position[0], this.position[1]+1 ) );
        }
        return possibleActions;
    }


    public ArrayList<Action> getPossibleBuildingActions(Game game) {
        ArrayList<Action> possibleActions = new ArrayList<>();

        if(board.isBuildiable(this.position[0]-1,this.position[1]-1) ){
            possibleActions.add(new Building(game, this.position[0]-1, this.position[1]-1 ) );
        }
        if(board.isBuildiable(this.position[0]-1,this.position[1]) ){
            possibleActions.add(new Building(game, this.position[0]-1, this.position[1] ) );
        }
        if(board.isBuildiable(this.position[0]-1,this.position[1]+1) ){
            possibleActions.add(new Building(game, this.position[0]-1, this.position[1]+1 ) );
        }
        if(board.isBuildiable(this.position[0]+1,this.position[1]-1) ){
            possibleActions.add(new Building(game, this.position[0]+1, this.position[1]-1 ) );
        }
        if(board.isBuildiable(this.position[0]+1,this.position[1]) ){
            possibleActions.add(new Building(game, this.position[0]+1, this.position[1] ) );
        }
        if(board.isBuildiable(this.position[0]+1,this.position[1]+1) ){
            possibleActions.add(new Building(game, this.position[0]+1, this.position[1]+1 ) );
        }
        if(board.isBuildiable(this.position[0],this.position[1]-1) ){
            possibleActions.add(new Building(game, this.position[0], this.position[1]-1 ) );
        }
        if(board.isBuildiable(this.position[0],this.position[1]+1) ){
            possibleActions.add(new Building(game, this.position[0], this.position[1]+1 ) );
        }

        return possibleActions;
    }

    public boolean didWin(){
        if( this.space == null){ return false; }
        if(this.space.getLevel() == 3){ return true; }
        else{ return false; }
    }

    public boolean didLoose(){
        if( this.space == null){ return false; }
        if(
                !this.board.isWalkeable(this.position[0]-1,this.position[1]-1,this.space.getLevel()) &&
                        !this.board.isWalkeable(this.position[0]-1,this.position[1],this.space.getLevel()) &&
                        !this.board.isWalkeable(this.position[0]-1,this.position[1]+1,this.space.getLevel()) &&
                        !this.board.isWalkeable(this.position[0],this.position[1]-1,this.space.getLevel()) &&
                        !this.board.isWalkeable(this.position[0],this.position[1]+1,this.space.getLevel()) &&
                        !this.board.isWalkeable(this.position[0]+1,this.position[1]-1,this.space.getLevel()) &&
                        !this.board.isWalkeable(this.position[0]+1,this.position[1],this.space.getLevel()) &&
                        !this.board.isWalkeable(this.position[0]+1,this.position[1]+1,this.space.getLevel())
        ){ return true; }
        else{ return false; }
    }

    public Board retriveBoard(){
        return this.board;
    }

    public Space retriveSpace(){
        return this.space;
    }

}
