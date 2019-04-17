package ch.uzh.ifi.seal.soprafs19.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Figurine implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private boolean HasMoved;

    private Board board;

    private Player player;
    int playerNumber;
    int figurineNumber;

    @Column(nullable = false, length = 100)
    int position[];


    public int getFigurineNumber() {
        return figurineNumber;

    }

    public int[] getPosition() {
        return position;
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
    }

    public void changePosition(int row, int column){
        board.getSpaces()[this.position[0]][this.position[1]].setFigurine(null);
        this.position[0] = row;
        this.position[1] = column;
        board.getSpaces()[row][column].setFigurine(this);
    }

}
