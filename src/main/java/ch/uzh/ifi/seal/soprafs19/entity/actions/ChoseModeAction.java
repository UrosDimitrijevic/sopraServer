package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

public class ChoseModeAction extends Action {

    private boolean withGodCards;

    public ChoseModeAction (Game game, boolean withGodCards){
        this.withGodCards = withGodCards;
        this.name = "ChoseMode";
        this.myGameId = game.getId();
    }

    @java.lang.Override
    public void run(GameService gameService) {
        System.out.println("\n\n\n\n\n\nI chose my godMode here\n\n\n\n\n\n");
    }
}
