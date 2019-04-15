package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.entity.Board;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance
public abstract class Action implements Serializable{

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue
    public Long id;

    @Column(nullable = false, unique = false)
    String name;


    @Column(nullable = false)
    long myGameId;

    public void setName(String name) {
        this.name = name;
    }

    public void setMyGameId(long myGameId) {
        this.myGameId = myGameId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Action(){
        this.name = "testname";
    }

    public Action(Game game){
        this.myGameId = game.getId();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public abstract void perfromAction(GameService gameService);

    public long TheGameId(){
        return this.myGameId;
    }
}
