package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Hermes extends GodCard {


    @Id
    @GeneratedValue
    private Long id;



    public Hermes(){
        super();
    }


    public Hermes(Game game){
        super(game);
        this.godnumber = 7;
        this.name = "Hermes";
    }

    public Long getId() {

        return this.id;
    }


    public void perfromAction(GameService gameservice){


    }



}
