package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.service.GameService;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Uros was here, server works

@RestController
public class    UserController {

    private final UserService service;

    private final GameService gameService;



    UserController(UserService service, GameService gameService) {

        this.service = service;
        this.gameService = gameService;
    }

    @GetMapping("/users")
    ResponseEntity all() {
        Iterable<User> allUsers = this.service.getUsers();
        for (User user: allUsers){
            user.removePassword();
        }
        return ResponseEntity.status(HttpStatus.OK).body(allUsers);
    }

    @GetMapping("/users/{id}")
    ResponseEntity userByID(@PathVariable Long id) {
        User gef = service.userByID(id);
        if( gef != null  ) {
            return ResponseEntity.status(HttpStatus.OK).body(gef.removePassword() );
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("-");
        }
    }

    @PutMapping("users/login")
    ResponseEntity loginUser(@RequestBody User loginUser) {
        User savedUser = this.service.userByUsername( loginUser.getUsername() );
        if( savedUser != null && savedUser.getPassword().equals( loginUser.getPassword() ) ) {
            this.service.setOnline(savedUser);
            return ResponseEntity.status(HttpStatus.OK).body(savedUser.removePassword() );
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UserName or Password wrong");
        }
    }

    //Used to create new accounts
    @PostMapping("/users")
    ResponseEntity createUser(@RequestBody User newUser) {
        User testUser = this.service.userByUsername( newUser.getUsername() );
        if( testUser == null) {
            User createdUser = this.service.createUser(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body( createdUser.removePassword() );
        }
        else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body(" Username already taken ");
        }
    }

    //used to ubdate the profile of a player
    @PutMapping("/users/{id}")
    ResponseEntity userByID(@PathVariable long id, @RequestBody User newUser) {
        User gef = service.userByID(id);
        if( gef == null || (gef != service.userByUsername(newUser.getUsername()) && service.userByUsername(newUser.getUsername()) != null) ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( "user with user-ID: " + id+" not found in Put-call" );
        }
        else{


            service.updateProfile(newUser, id, gameService);


            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
        }
    }

}
