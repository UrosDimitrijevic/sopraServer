package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Hephastephus extends GodCard {


    @Id
    @GeneratedValue
    private Long id;


    public Hephastephus(){
        super();
    }


    public Hephastephus(Game game){
        super(game);
        this.godnumber = 6;
        this.name = "Hephastephus";
    }


    public void perfromAction(GameService gameservice){
    }



}
