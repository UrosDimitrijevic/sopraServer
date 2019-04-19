package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Athena extends GodCard {


    @Id
    @GeneratedValue
    private Long id;




    public Athena(){
        super();
    }



    public Athena(Game game){
        super(game);
        this.godnumber = 3;
        this.name = "Athena";
    }


    public void perfromAction(GameService gameservice){


    }



}