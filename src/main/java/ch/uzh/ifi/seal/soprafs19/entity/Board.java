package ch.uzh.ifi.seal.soprafs19.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Board implements Serializable{
    private Space [][] spaces;

    @Column(nullable = false, length = 1500)
    private Figurine figurines[][]; //p1f1 = [0][0], p1f2 =[0][1], ...
    private Figurine p1f1;
    private Figurine p1f2;
    private Figurine p2f1;
    private Figurine p2f2;

    @Id
    @GeneratedValue
    private Long id;

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


}
