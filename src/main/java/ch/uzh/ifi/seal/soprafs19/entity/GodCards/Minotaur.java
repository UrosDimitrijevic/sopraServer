package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Minotaur extends GodCard {


    @Id
    @GeneratedValue
    private Long id;




    public Minotaur(){

        super();
    }


    public Long getId() {

        return this.id;
    }


    public void perfromAction(GameService gameservice){


    }



}