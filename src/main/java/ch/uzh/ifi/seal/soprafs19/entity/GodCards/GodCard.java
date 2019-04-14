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

    @Column(nullable = false)
    long myGameId;




    public GodCard(){

    }

    public GodCard(Game game){
        this.myGameId =game.getId();


    }

    public Long getId() {
        return this.id;
    }




    public abstract void perfromAction(GameService gameservice);

}
