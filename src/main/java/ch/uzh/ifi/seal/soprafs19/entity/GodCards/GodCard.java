package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.actions.ChooseGod;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

@Entity
public abstract class GodCard implements Serializable {



    @Id
    @GeneratedValue
    private Long id;


    @Column(nullable = false)
    String name;

    @Column(nullable= false)
    int godnumber;


    public GodCard(){

    }

    @Column(nullable = false)
    long myGameId;



    public Godcard(Game game){
        this.myGameId =game.getId();


    }

    public Long getId() {
        return this.id;
    }


    public String getName(){
        return this.name;
    }

    public abstract int getGodnumber(){
        return this.godnumber;

    }

    public abstract GodCard getGodwithNumber(int godnumber){
        return this;
    }

    public abstract void perfromAction(GameService gameservice){

    }

}
