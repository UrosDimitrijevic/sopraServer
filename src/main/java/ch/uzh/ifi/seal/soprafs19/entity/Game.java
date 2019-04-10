package ch.uzh.ifi.seal.soprafs19.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Random;
import java.io.Serializable;

@Entity
public class Game  implements Serializable  {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true, nullable = false)
    private long player1id;

    @Column(unique = true, nullable = false)
    private long player2id;

    @Column(nullable = false)
    private int status;

    @Column(nullable = true)
    private boolean playWithGodCards;

    @Column(nullable = false, length = 2000)
    private Board board;

    @Column(nullable = false, length = 2000)
    private Player players[];

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

    public Game(User user1, User user2){
        this.status = 0;
        this.board = new Board();
        this.players = new Player[2];
        boolean doesP1start = this.DoesP1Start(user1,user2);
        this.playWithGodCards = false;

        this.players[0] =  new Player(user1,this.board, doesP1start );
        this.players[1] =  new Player(user2,this.board, !doesP1start );
        this.player1id = user1.getId();
        this.player2id = user2.getId();
    }

    public int getLvlAt(int column, int row){
        return this.board.getLvlAt(column, row);
    }

    public long getId(){
        return this.id;
    }
}
