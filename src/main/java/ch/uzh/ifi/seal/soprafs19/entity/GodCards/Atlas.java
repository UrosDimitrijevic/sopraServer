package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Atlas extends GodCard {


    @Id
    @GeneratedValue
    private Long id;




    public Atlas(){
        super();
    }


    public Atlas(Game game){
        super(game);
        this.godnumber = 4;
        this.name = "Atlas";
    }




    public void perfromAction(GameService gameservice){


    }



}
