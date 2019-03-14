package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
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

    private final Logger log = LoggerFactory.getLogger(UserService.class);

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

    public void updateProfile(User modelUser, long id){
        User gef = this.userByID(id);
        if( modelUser.getUsername() != null){
            gef.setUsername(modelUser.getUsername() );
        }
        if( modelUser.getBirthday() != null){
            gef.setBirthday(modelUser.getBirthday() );
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
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }
}
