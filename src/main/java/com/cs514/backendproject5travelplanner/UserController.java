package com.cs514.backendproject5travelplanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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



@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @PostMapping("/saveUser")
    @CrossOrigin(origins = "*")
    public User saveUser(@RequestBody User user) {
        System.out.println("Received User: " + user);
        return this.userRepository.save(user);

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
    @GetMapping("/findByUserName")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public List<User> findByUserName(@RequestParam String userName) {
        Iterable<User> Users = this.userRepository.findByUserName(userName);
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
    @PutMapping("/updateByUserName")
    @CrossOrigin(origins = "*")
    public ResponseEntity<User> updateByUserName(@RequestBody User user) {
        // Ensure the userName is provided and not empty
        if (user.getUserName() == null || user.getUserName().isEmpty()) {
            return ResponseEntity.badRequest().body(null);  // Return bad request if no userName
        }

        // Find the user by userName
        List<User> existingUsers = userRepository.findByUserName(user.getUserName());

        // If no users are found, return 404 Not Found
        if (existingUsers.isEmpty()) {
            return ResponseEntity.status(404).body(null);  // Return 404 if user not found
        }

        // Assuming userName is unique, we update the first matching user
        User existingUser = existingUsers.get(0);

        // Update fields if provided
        if (user.getInterest() != null && !user.getInterest().isEmpty()) {
            existingUser.setInterest(user.getInterest());
        }
        if (user.getAge() > 0) {
            existingUser.setAge(user.getAge());
        }

        // Save the updated user
        User updatedUser = userRepository.save(existingUser);

        // Return the updated user in the response
        return ResponseEntity.ok(updatedUser);  // 200 OK with updated user
    }


    @DeleteMapping("/deleteByUserName")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> deleteByUserName(@RequestParam String userName) {
        if (userName == null || userName.isEmpty()) {
            return ResponseEntity.badRequest().body("UserID is required");
        }

        // Check if the user exists
        List<User> users = userRepository.findByUserName(userName);
        if (users.isEmpty()) {
            return ResponseEntity.status(404).body("User with UserName " + userName + " not found");
        }

        // Assuming userID is unique, delete the first matching user
        userRepository.delete(users.get(0));

        return ResponseEntity.ok("User with UserName " + userName + " deleted successfully");
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
    @DeleteMapping("/deleteByGender")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> deleteByGender(@RequestParam String gender) {
        if (gender == null || gender.isEmpty()) {
            return ResponseEntity.badRequest().body("Gender is required");
        }

        // Find users by gender
        List<User> users = userRepository.findByGender(gender);

        // Check if users with the given gender exist
        if (users.isEmpty()) {
            return ResponseEntity.status(404).body("No users found with gender " + gender);
        }

        // Delete all users with the given gender
        userRepository.deleteAll(users);

        return ResponseEntity.ok("Users with gender " + gender + " deleted successfully");
    }

}