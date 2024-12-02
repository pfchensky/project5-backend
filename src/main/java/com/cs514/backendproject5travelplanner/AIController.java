package com.cs514.backendproject5travelplanner;

import org.springframework.web.bind.annotation.*;
import ai.peoplecode.OpenAIConversation;
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
    public String generateTravelPlan(@RequestBody List<UserWithTrip> usersWithTrips) {
        try {
            if (usersWithTrips == null || usersWithTrips.isEmpty()) {
                return "No users provided for travel planning.";
            }

            // Aggregating trip details
            Trip groupTrip = usersWithTrips.get(0).getTrip();
            StringBuilder context = new StringBuilder("You are a travel assistant AI. Generate a travel plan for this group:\n\n");
            context.append(String.format("Trip Details:\n- Destination: %s\n- Duration: %d days\n- Month: %s\n\n",
                    groupTrip.getDestination(), groupTrip.getDuration(), groupTrip.getMonth()));

            // Adding group members to context
            context.append("Group Members:\n");
            for (UserWithTrip userWithTrip : usersWithTrips) {
                User user = userRepository.findByUserName(userWithTrip.getUserName()).get(0);
                context.append(String.format("- Name: %s (Age: %d, Gender: %s, Interest: %s)\n",
                        user.getUserName(), user.getAge(), user.getGender(), user.getInterest()));
            }

            context.append("\nInstructions:\n");
            context.append("1. Provide a detailed day-by-day itinerary balancing the group's interests.\n");
            context.append("2. Include shared activities suitable for all members.\n");
            context.append("3. Suggest optional individual activities based on personal interests.\n");

            // Generate the travel plan using OpenAI
            String openAIResponse = conversation.askQuestion(context.toString(), "Generate a comprehensive travel plan for the group.");
            System.out.println("OpenAI response: " + openAIResponse);  // Log the OpenAI response

            return openAIResponse;  // Return as plain text (not JSON)
        } catch (Exception e) {
            return "Error generating group travel plan: " + e.getMessage();
        }
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


