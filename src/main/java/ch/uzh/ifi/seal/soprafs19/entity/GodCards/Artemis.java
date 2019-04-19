package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Artemis extends GodCard {


    @Id
    @GeneratedValue
    private Long id;



    public Artemis(){
        super();
    }


    public Artemis(Game game){
        super(game);
        this.godnumber = 2;
        this.name = "Artemis";
    }



    public void perfromAction(GameService gameservice){


    }



}
