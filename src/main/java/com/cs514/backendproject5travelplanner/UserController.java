package com.cs514.backendproject5travelplanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;


@RestController
public class UserController {

    //constant
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/saveUser")
    @CrossOrigin(origins = "*")
    public User saveUser(@RequestBody User user) {
        System.out.println("Received User: " + user);
//        if (user == null) {
//            return "The User is invalid";
//        }
        return this.userRepository.save(user);
        //return "success"+user.toString();
    }


    @GetMapping("/findByInterest")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public List<User> findByInterest(@RequestParam String Interest) {
        Iterable<User> Users = this.userRepository.findByInterest(Interest);
        List<User> UserList = new ArrayList<>();
        Users.forEach(UserList::add);
        return UserList;
    }

    @GetMapping("/findByUserID")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public List<User> findByUserID(@RequestParam String userID) {
        Iterable<User> Users = this.userRepository.findByUserID(userID);
        List<User> UserList = new ArrayList<>();
        Users.forEach(UserList::add);
        return UserList;
    }

    @GetMapping("/findByAge")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public List<User> findByAge(@RequestParam int age) {
        Iterable<User> Users = this.userRepository.findByAge(age);
        List<User> UserList = new ArrayList<>();
        Users.forEach(UserList::add);
        return UserList;
    }

    @GetMapping("/findAllUsers")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public List<User> findAllUsers() {
        Iterable<User> Users = this.userRepository.findAll();
        List<User> UserList = new ArrayList<>();
        Users.forEach(UserList::add);
        return UserList;
    }
    @PutMapping("/updateByUserID")
    @CrossOrigin(origins = "*")
    /**
     * update user ID
     */
    public ResponseEntity<User> updateByUserID(@RequestBody User user) {
        if (user.getUserID() == null || user.getUserID().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        List<User> existingUsers = userRepository.findByUserID(user.getUserID());
        if (existingUsers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User existingUser = existingUsers.get(0);

        if (user.getInterest() != null && !user.getInterest().isEmpty()) {
            existingUser.setInterest(user.getInterest());
        }
        if (user.getAge() > 0) {
            existingUser.setAge(user.getAge());
        }
        if (user.getLocation() != null && !user.getLocation().isEmpty()) {
            existingUser.setLocation(user.getLocation());
        }

        User updatedUser = userRepository.save(existingUser);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/deleteByUserID")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> deleteByUserID(@RequestParam String userID) {
        if (userID == null || userID.isEmpty()) {
            return ResponseEntity.badRequest().body("UserID is required");
        }

        // Check if the user exists
        List<User> users = userRepository.findByUserID(userID);
        if (users.isEmpty()) {
            return ResponseEntity.status(404).body("User with UserID " + userID + " not found");
        }

        // Assuming userID is unique, delete the first matching user
        userRepository.delete(users.get(0));

        return ResponseEntity.ok("User with UserID " + userID + " deleted successfully");
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteByUserID(@RequestBody Map<String, String> payload) {
        String userID = payload.get("userID");

        if (userID == null || userID.isEmpty()) {
            return ResponseEntity.badRequest().body("UserID is required");
        }

        // check is user is exist
        List<User> users = userRepository.findByUserID(userID);
        if (users.isEmpty()) {
            return ResponseEntity.status(404).body("User with UserID " + userID + " not found");
        }

        // assume userID is unique，delete the first match user
        userRepository.delete(users.get(0));

        return ResponseEntity.ok("User with UserID " + userID + " deleted successfully");
    }


}