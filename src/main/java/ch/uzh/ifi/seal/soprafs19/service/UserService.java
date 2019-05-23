package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

import java.util.UUID;

@Service
@Transactional
public class UserService {




    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User userByUsername(String name ){
        return this.userRepository.findByUsername(name );
    }

    public void setOnline(User user){
        user.setStatus(UserStatus.ONLINE);
        userRepository.save(user);
    }

    public void setOffline(User user){
        user.setStatus(UserStatus.OFFLINE);
        userRepository.save(user);
    }

    public void updateProfile(User modelUser, long id, GameService gameService){
        User gef = this.userByID(id);
        if( modelUser.getUsername() != null){
            gef.setUsername(modelUser.getUsername() );
        }
        if( modelUser.getBirthday() != null){
            gef.setBirthday(modelUser.getBirthday() );
        }

        //logout
        if(modelUser.getStatus() == UserStatus.OFFLINE){
            gef.setStatus(UserStatus.OFFLINE);
        }
        //checking for challenge
        if( modelUser.getChallenging() != null){
            Long oponentId = modelUser.getChallenging();
            gef.setChallenging(oponentId);
            User oponent = this.userByID(oponentId);
            oponent.setGettingChallengedBy(gef.getId() );

            //checking if both players are into it
            if(gef.getGettingChallengedBy() == gef.getChallenging() && gef.getGettingChallengedBy() != null ){
                Game game = new Game(oponent,gef);
                gameService.saveGame(game);
                System.out.println("\n\n\nshould have created the game\n\n\n");
                oponent.setStatus(UserStatus.INGAME);
                gef.setStatus(UserStatus.INGAME);
            }
            userRepository.save(oponent);
        }

        //checking if a challenge got rejected
        else if( modelUser.getGettingChallengedBy() == null && gef.getGettingChallengedBy() != null){
            User oponent = this.userByID(gef.getGettingChallengedBy());
            oponent.setChallenging(null);
            this.userRepository.save(oponent);
            gef.setGettingChallengedBy(null);
        }
        userRepository.save(gef );
    }

    public User userByID(long id ){
        return this.userRepository.findById(id );
    }

    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.OFFLINE);
        newUser.initCreationDate();
        if( newUser.getBirthday() == null) {
            newUser.setBirthday("1800-01-01");
        }
        userRepository.save(newUser);
        return newUser;
    }
}
