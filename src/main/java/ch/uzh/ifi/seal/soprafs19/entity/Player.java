package ch.uzh.ifi.seal.soprafs19.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Player implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private Long myUserID;
    private boolean startingplayer;
    private boolean GodMode;
    private Figurine figurine1;
    private Figurine figurine2;

    public Player(User me, Board board, boolean doIstart){
        this.myUserID = me.getId();
        this.startingplayer = doIstart;
        this.figurine1 = new Figurine(this,board,1);
        this.figurine2 = new Figurine(this,board,2);
    }

    public boolean isStartingplayer() {
        return startingplayer;
    }
}
