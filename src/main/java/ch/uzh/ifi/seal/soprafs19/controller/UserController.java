package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Uros was here, server works

@RestController
public class UserController {

    private final UserService service;

    UserController(UserService service) {
        this.service = service;
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

    @PostMapping("users/login")
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

    @PutMapping("users/{id}/logout")
    ResponseEntity logoutUser(@PathVariable Long id) {
        User testUser = this.service.userByID( id );
        if( testUser != null) {
            this.service.setOffline(testUser);
            return ResponseEntity.status(HttpStatus.OK).body("logged-out");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\n \"message\": \"Username taken\"\n} ");
        }
    }

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

    //Used to create new accounts
    @PutMapping("/users/{id}")
    ResponseEntity userByID(@PathVariable long id, @RequestBody User newUser) {
        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body( newUser );

        User gef = service.userByID(id);
        if( gef == null || (gef != service.userByUsername(newUser.getUsername()) && service.userByUsername(newUser.getUsername()) != null) ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( "user with user-ID: " + id+" not found in Put-call" );
        }
        else{
            service.updateProfile(newUser, id);


            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
        }
    }

}
