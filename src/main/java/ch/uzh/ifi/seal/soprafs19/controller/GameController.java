package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.GameService;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

//Uros was here, server works

@RestController
public class GameController {

    private final UserService service;

    private final GameService gameService;

    GameController(UserService service, GameService gameService) {
        this.service = service;
        this.gameService = gameService;
    }

    @GetMapping("/game/actions/{id}")
    ResponseEntity getActions(@PathVariable long id) {
        Iterable<Action> performableActions;
        Game myGame = gameService.gameByPlaxerId(id);
        performableActions = myGame.getPossibleActions(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request Not implemented");
    }

    @GetMapping("/game/Board/{id}")
    ResponseEntity getBoardStatus(@PathVariable Long id) {
        Game game = this.gameService.gameByPlaxerId(id);
        if(game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request Not implemented");
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(game);
        }
    }

    @PutMapping("/game/actions/{actionId}")
    ResponseEntity performAction(@PathVariable Long actionId) {
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
        return ResponseEntity.status(HttpStatus.OK).body(gameService.gameByPlaxerId(user1.getId()) );
    }


}
