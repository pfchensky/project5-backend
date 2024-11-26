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
    public List<String> generateTravelPlans(@RequestBody List<String> userNames) {
        List<String> travelPlans = new ArrayList<>();
        try {
            // Retrieve users by their userNames
            List<User> selectedUsers = new ArrayList<>();
            for (String userName : userNames) {
                List<User> users = userRepository.findByUserName(userName);
                if (!users.isEmpty()) {
                    selectedUsers.add(users.get(0));  // Assuming userName is unique
                }
            }

            // Generate travel plans for each selected user
            for (User user : selectedUsers) {
                // Retrieve the Trip object associated with the user
                Trip userTrip = getUserTrip(user);

                // Build the context string for OpenAI, now including destination and other trip details
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
                        user.getGender(),  // Include gender in the prompt
                        user.getInterest(),
                        userTrip.getDestination(),
                        userTrip.getDuration(),
                        userTrip.getMonth()
                );

                // Call OpenAI to generate the travel plan for the current user
                String travelPlan = conversation.askQuestion(context, "Provide a travel plan tailored for this user.");
                travelPlans.add(travelPlan);
            }

        } catch (Exception e) {
            travelPlans.add("Error generating travel plans: " + e.getMessage());
        }

        return travelPlans;
    }

    // This method retrieves the trip associated with the user (dummy method, needs to be implemented)
    private Trip getUserTrip(User user) {
        // You should retrieve the user's associated trip from the database or some other logic
        // For now, we return a dummy trip with the destination "Unknown" to simulate the behavior
        // Replace this logic with actual code to fetch the trip for the user from your data source

        // Example: fetch the trip using a trip repository or service
        return new Trip("Unknown Destination", 7, "July"); // Dummy trip, replace with real implementation
    }

    // DTO for the travel plan request
    public static class TravelPlanRequest {
        private int age;
        private String interest;
        private String destination;
        private int duration;
        private String gender;

        // Getters and setters
        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
}
