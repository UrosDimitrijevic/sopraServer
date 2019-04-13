package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Atlas extends GodCard {


    @Id
    @GeneratedValue
    private Long id;


    @Column(nullable = false)
    String name="Atlas"

    @Column(nullable= false)
    int godnumber=4;



    public GodCard(){

    }
    public GodCard(){
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
    
    public GodCard getThis(){
        return this;
        
    }


    public GodCard getGodwithNumber(int godNumber){
        if(godnumber==this.getGodnumber()){
            return this;
        }
    }

    public void perfromAction(GameService gameservice){


    }



}
