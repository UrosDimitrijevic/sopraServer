package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.entity.Board;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public abstract class Action{

    @Id
    @GeneratedValue
    long id;

    @Column(nullable = false, unique = false)
    String name;

    @Column(nullable = false)
    long myGameId;

    public abstract void run(GameService gameService);
}
