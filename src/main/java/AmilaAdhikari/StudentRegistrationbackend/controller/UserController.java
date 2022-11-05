package AmilaAdhikari.StudentRegistrationbackend.controller;

import AmilaAdhikari.StudentRegistrationbackend.exception.UserNotFoundException;
import AmilaAdhikari.StudentRegistrationbackend.model.User;
import AmilaAdhikari.StudentRegistrationbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //Post method

    @PostMapping("/user")
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> VerifyUser(@RequestBody User body) {
        List<User> user = userRepository.findByUsername(body.getUsername());
        System.out.println(user.get(0).getEmail()+" : "+body.getPassword());

        if (user.get(0).getPassword().equals(body.getPassword())) {
            return new ResponseEntity<>(user.get(0), HttpStatus.OK);
        }
        return new ResponseEntity<>(user.get(0).getPassword()+" : "+body.getPassword(),HttpStatus.UNAUTHORIZED);
    }

    //Get method
    @GetMapping("/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id){
        return userRepository.findById(id)
                .map(user ->{
                    user.setUsername(newUser.getUsername());
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    return  userRepository.save(user);
                }).orElseThrow(()->new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    String deleteUser (@PathVariable Long id){
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "set with id" +id + "has been deleted success";
        }



}
