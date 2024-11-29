package com.cs514.backendproject5travelplanner;

import org.springframework.web.bind.annotation.*;
import ai.peoplecode.OpenAIConversation;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AIController {

    private final OpenAIConversation conversation;
    private final UserRepository userRepository;

    // Constructor to initialize OpenAIConversation and UserRepository
    public AIController(UserRepository userRepository) {
        this.userRepository = userRepository;
        conversation = new OpenAIConversation(getOpenAIKey(), "gpt-4o-mini"); // Use gpt-4 or gpt-3.5
    }

    // Helper method to get the OpenAI API key from the environment
    private String getOpenAIKey() {
        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null) {
            throw new IllegalStateException("API Key not found in environment variables");
        }
        return apiKey;
    }

    // Generate travel plans for multiple selected users
    @PostMapping("/generate-travel-plans")
    @CrossOrigin(origins = "*")
    public List<String> generateTravelPlans(@RequestBody List<UserWithTrip> usersWithTrips) {
        List<String> travelPlans = new ArrayList<>();
        try {
            // Generate travel plans for each selected user
            for (UserWithTrip userWithTrip : usersWithTrips) {
                // Fetch the user info from GCD using userName
                List<User> users = userRepository.findByUserName(userWithTrip.getUserName());

                if (!users.isEmpty()) {
                    User user = users.get(0); // Assuming userName is unique in the database

                    // Get the trip details from the request
                    Trip userTrip = userWithTrip.getTrip();  // trip info from frontend

                    // Build the context string for OpenAI, now including gender, age, and trip details
                    String context = String.format(
                            "You are a travel assistant AI. Generate a travel plan for the following user:\n" +
                                    "- Age: %d\n" +
                                    "- Gender: %s\n" +
                                    "- Interest: %s\n" +
                                    "- Destination: %s\n" +
                                    "- Duration: %d days\n" +
                                    "- Month: %s\n" +
                                    "Please create a detailed travel plan for them based on their age, gender, interest, destination, duration, and month.",
                            user.getAge(),
                            user.getGender(),
                            user.getInterest(),
                            userTrip.getDestination(),
                            userTrip.getDuration(),
                            userTrip.getMonth()
                    );

                    // Call OpenAI to generate the travel plan for the current user
                    String travelPlan = conversation.askQuestion(context, "Provide a travel plan tailored for this user.");
                    travelPlans.add(travelPlan);
                } else {
                    travelPlans.add("User with userName " + userWithTrip.getUserName() + " not found.");
                }
            }

        } catch (Exception e) {
            travelPlans.add("Error generating travel plans: " + e.getMessage());
        }

        return travelPlans;
    }

    // DTO for receiving user data with trip information
    public static class UserWithTrip {
        private String userName;
        private int age;
        private String gender;
        private String interest;
        private Trip trip;

        // Getters and setters
        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public Trip getTrip() {
            return trip;
        }

        public void setTrip(Trip trip) {
            this.trip = trip;
        }
    }
}

