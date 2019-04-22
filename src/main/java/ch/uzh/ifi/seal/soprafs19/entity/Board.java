package ch.uzh.ifi.seal.soprafs19.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Board implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    private Space [][] spaces;

    @Column(nullable = false, length = 1500)
    private Figurine figurines[][]; //p1f1 = [0][0], p1f2 =[0][1], ...
    private Figurine p1f1;
    private Figurine p1f2;
    private Figurine p2f1;
    private Figurine p2f2;

    public Space[][] getSpaces() {
        return spaces;
    }

    public Board(){
        this.spaces = new Space[5][5];
        for( int i = 0; i < 5; ++i){
            for( int j = 0; j < 5; ++j){
               this.spaces[i][j] = new Space();
            }
        }
        this.figurines = new Figurine[2][2];
        updateFigs();
    }

    private void updateFigs(){
        this.p1f1 = this.figurines[0][0];
        this.p1f2 = this.figurines[0][1];
        this.p2f1 = this.figurines[1][0];
        this.p2f2 = this.figurines[1][1];
    }

    public void setFigurine(Figurine f, int player, int figNumber){
        this.figurines[player-1][figNumber-1] = f;
        updateFigs();
    }

    public int getLvlAt(int column, int row){

        return this.spaces[column][row].getLevel();
    }

    public boolean isWalkeable(int row, int collumn, int yourLevel){
        //System.out.println("row: " + Integer.toString(row) + " column: " + Integer.toString(collumn) + "\n");
        //System.out.println("\n\n\nthe current lvl is:" + Integer.toString(yourLevel) +"\n"  + "spaceLevel is:" + Integer.toString(this.spaces[row][collumn].getLevel())+"\n\n");

        if(row >= 5 || collumn >= 5 || row < 0 || collumn < 0){
            return false;
        }
        else{
            return this.spaces[row][collumn].checkIfWalkeable(yourLevel);
        }
    }

    public boolean isBuildiable(int row, int collumn){
        if(row >= 5 || collumn >= 5 || row < 0 || collumn < 0){
            return false;
        }
        else{
            return this.spaces[row][collumn].checkIfBuildiable();
        }
    }

    public boolean isEmpty(int row, int collumn){
        return this.spaces[row][collumn].checkIfEmtpy();
    }

}
