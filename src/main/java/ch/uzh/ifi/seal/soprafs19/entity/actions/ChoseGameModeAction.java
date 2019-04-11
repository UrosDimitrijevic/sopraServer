package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

public class ChoseGameModeAction extends Action {

    private boolean withGodCards;

    public ChoseGameModeAction (Game game, boolean withGodCards){
        this.withGodCards = withGodCards;
        this.name = "ChoseMode";
        this.myGameId = game.getId();
    }

    @java.lang.Override
    public void perfromAction(GameService gameService) {
        System.out.println("\n\n\n\n\n\nI chose my godMode here\n\n\n\n\n\n");
    }
}
