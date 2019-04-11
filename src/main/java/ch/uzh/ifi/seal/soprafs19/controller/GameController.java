package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.GameService;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
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

    GameController(UserService service, GameService gameService) {
        this.service = service;
        this.gameService = gameService;
    }

    @GetMapping("/game/actions/{id}")
    ResponseEntity getActions(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request Not implemented");
    }

    @GetMapping("/game/Board/{id}")
    ResponseEntity getBoardStatus(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request Not implemented");
    }

    @PutMapping("/game/Actions/{actionId}")
    ResponseEntity performAction(@PathVariable Long actionId) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request Not implemented");
    }

}
