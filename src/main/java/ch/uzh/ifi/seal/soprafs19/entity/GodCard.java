package ch.uzh.ifi.seal.soprafs19.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class GodCard  implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    public GodCard(){

    }

    public Long getId() {
        return id;
    }
}
