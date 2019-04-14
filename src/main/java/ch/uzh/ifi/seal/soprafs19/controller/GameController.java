package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.service.ActionService;
import ch.uzh.ifi.seal.soprafs19.service.GameService;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Uros was here, server works

@RestController
public class GameController {

    private final UserService service;

    private final GameService gameService;

    private final ActionService actionService;

    GameController(UserService service, GameService gameService, ActionService actionService) {
        this.service = service;
        this.gameService = gameService;
        this.actionService = actionService;
    }

    @GetMapping("/game/actions/{id}")
    ResponseEntity getActions(@PathVariable long id) {
        Iterable<Action> performableActions;
        Game myGame = gameService.gameByPlaxerId(id);
        if( myGame == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player is not in a game");
        }
        performableActions = myGame.getPossibleActions(id);
        if(performableActions == null){
            return ResponseEntity.status(HttpStatus.OK).body("performable actions is null for some reason");
        }
        for(Action action:performableActions){
            this.actionService.saveAction(action);
        }
        return ResponseEntity.status(HttpStatus.OK).body(performableActions);
    }


    @GetMapping("/game/Board/{id}")
    ResponseEntity getBoardStatus(@PathVariable Long id) {
        Game game = this.gameService.gameByPlaxerId(id);
        if(game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player doesn't have a game");
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(game);
        }
    }

    @PutMapping("/game/actions/{actionId}")
    ResponseEntity performAction(@PathVariable Long actionId) {
        if( this.actionService.runActionByID(actionId) ){
            return ResponseEntity.status(HttpStatus.OK).body("Action performed");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request Not implemented");
    }

    @PutMapping("/game/justForTesting/{id}")
    ResponseEntity justForTesting(@PathVariable Long id) {
        Long secondId = 2L;
        User user1 = this.service.userByID(id);
        User user2 = this.service.userByID(secondId);
        if(user1 == null || user2 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("one of the users not found");
        }
        Game game = new Game(user1,user2);
        gameService.saveGame(game);
        return ResponseEntity.status(HttpStatus.OK).body("game was created" );
    }


}
