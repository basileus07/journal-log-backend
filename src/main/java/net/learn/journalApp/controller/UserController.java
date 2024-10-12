package net.learn.journalApp.controller;

import net.learn.journalApp.entity.JournalEntry;
import net.learn.journalApp.entity.User;
import net.learn.journalApp.service.JournalEntryService;
import net.learn.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUser();
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveUser(user);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?>updateUser(@RequestBody User user, @PathVariable String userName){
          User userInDb =  userService.findByUserName(userName);
          if(userInDb != null){
              userInDb.setUserName(user.getUserName());
              userInDb.setPassword(user.getPassword());
              userService.saveUser(userInDb);
              return new ResponseEntity<>(HttpStatus.OK);
          }
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
