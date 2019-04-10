package ch.uzh.ifi.seal.soprafs19.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Figurine implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private Board board;
    private Player player;
    int playerNumber;
    int figurineNumber;

    public Figurine(Player player, Board board, int figurineNumber){
        this.board = board;
        this.player = player;
        this.figurineNumber = figurineNumber;
        this.playerNumber = (player.isStartingplayer())?1:2;
        board.setFigurine(this,this.playerNumber,this.figurineNumber);
    }
}
