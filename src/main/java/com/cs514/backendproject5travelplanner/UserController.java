package com.cs514.backendproject5travelplanner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Save a new user
    @PostMapping("/saveUser")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        if (user == null || user.getUserID() == null || user.getUserID().isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Validate user input
        }
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    // Login or register a user
    @PostMapping("/login")
    public ResponseEntity<User> loginOrRegister(@RequestBody Map<String, String> payload) {
        String userID = payload.get("userID");
        String userName = payload.get("userName");

        if (userID == null || userID.isEmpty() || userName == null || userName.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Optional<User> existingUser = userRepository.findByUserID(userID);
        if (existingUser.isPresent()) {
            return ResponseEntity.ok(existingUser.get());
        }

        User newUser = new User(userID, userName, "No interest", 0, "Unknown");
        User savedUser = userRepository.save(newUser);
        return ResponseEntity.ok(savedUser);
    }

    // Find user by interest
    @GetMapping("/findByInterest")
    public ResponseEntity<List<User>> findByInterest(@RequestParam String interest) {
        List<User> users = userRepository.findByInterest(interest);
        return ResponseEntity.ok(users);
    }

    // Find user by unique userID
    @GetMapping("/findByUserID")
    public ResponseEntity<User> findByUserID(@RequestParam String userID) {
        Optional<User> user = userRepository.findByUserID(userID);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(null)); // Return 404 if not found
    }

    // Find users by username
    @GetMapping("/findByUserName")
    public ResponseEntity<List<User>> findByUserName(@RequestParam String userName) {
        List<User> users = userRepository.findByUserName(userName);
        return ResponseEntity.ok(users);
    }

    // Find users by age
    @GetMapping("/findByAge")
    public ResponseEntity<List<User>> findByAge(@RequestParam int age) {
        List<User> users = userRepository.findByAge(age);
        return ResponseEntity.ok(users);
    }

    // Find all users
    @GetMapping("/findAllUsers")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = (List<User>) userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // Update user by username
    @PutMapping("/updateByUserName")
    public ResponseEntity<User> updateByUserName(@RequestBody User user) {
        if (user.getUserName() == null || user.getUserName().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        List<User> existingUsers = userRepository.findByUserName(user.getUserName());
        if (existingUsers.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        User existingUser = existingUsers.get(0);
        if (user.getInterest() != null) existingUser.setInterest(user.getInterest());
        if (user.getAge() > 0) existingUser.setAge(user.getAge());

        User updatedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete user by username
    @DeleteMapping("/deleteByUserName")
    public ResponseEntity<String> deleteByUserName(@RequestParam String userName) {
        List<User> users = userRepository.findByUserName(userName);
        if (users.isEmpty()) {
            return ResponseEntity.status(404).body("User with UserName " + userName + " not found");
        }

        userRepository.delete(users.get(0));
        return ResponseEntity.ok("User with UserName " + userName + " deleted successfully");
    }

    // Delete user by unique userID
    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteByUserID(@RequestBody Map<String, String> payload) {
        String userID = payload.get("userID");

        if (userID == null || userID.isEmpty()) {
            return ResponseEntity.badRequest().body("UserID is required");
        }

        Optional<User> user = userRepository.findByUserID(userID);
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body("User with UserID " + userID + " not found");
        }

        userRepository.delete(user.get());
        return ResponseEntity.ok("User with UserID " + userID + " deleted successfully");
    }

    // Delete users by gender
    @DeleteMapping("/deleteByGender")
    public ResponseEntity<String> deleteByGender(@RequestParam String gender) {
        if (gender == null || gender.isEmpty()) {
            return ResponseEntity.badRequest().body("Gender is required");
        }

        List<User> users = userRepository.findByGender(gender);
        if (users.isEmpty()) {
            return ResponseEntity.status(404).body("No users found with gender " + gender);
        }

        userRepository.deleteAll(users);
        return ResponseEntity.ok("Users with gender " + gender + " deleted successfully");
    }
}
