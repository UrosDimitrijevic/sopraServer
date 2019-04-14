package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Demeter extends GodCard {


    @Id
    @GeneratedValue
    private Long id;


    @Column(nullable = false)
    String name="Demeter"

    @Column(nullable= false)
    int godnumber=5;




    public Demeter(){
        super();
    }


    public Long getId() {
        return this.id;
    }


    public String getName(){
        return this.name;
    }

    public int getGodnumber(){
        return this.godnumber;

    }



    public void perfromAction(GameService gameservice){


    }



}
