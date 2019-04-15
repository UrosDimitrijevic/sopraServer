package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Apollo extends GodCard {


    @Id
    @GeneratedValue
    private Long id;



    public Apollo(){
        super();
    }

    public Apollo(Game game){
        super(game);
        this.godnumber = 1;
        this.name = "Apollo";
    }


    public Long getId() {
        return this.id;
    }




    public void perfromAction(GameService gameservice){


    }



}
