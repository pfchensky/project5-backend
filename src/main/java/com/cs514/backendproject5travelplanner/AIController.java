package com.cs514.backendproject5travelplanner;

import org.springframework.web.bind.annotation.*;

import ai.peoplecode.OpenAIConversation;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AIController {

    private final OpenAIConversation conversation;

    public String getOpenAIKey() {
        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null)
        { throw new IllegalStateException("API Key not found in environment variables");
        }
        return apiKey;
    }

    public AIController() {

        conversation = new OpenAIConversation(getOpenAIKey(), "gpt-4o-mini"); // Or gpt-4
    }

    @PostMapping("/generate-travel-plan")
    @CrossOrigin(origins = "*")
    public String generateTravelPlan(@RequestBody TravelPlanRequest request) {
        try {
            // Build the context string
            String context = String.format(
                    "You are a travel assistant AI. Generate a travel plan for the following user:\n" +
                            "- Age: %d\n" +
                            "- Interest: %s\n" +
                            "- Location: %s\n" +
                            "Please create a detailed travel plan for them based on their interest, location, and age.",
                    request.getAge(),
                    request.getInterest(),
                    request.getLocation()
            );

            // Call OpenAI to generate the travel plan
            return conversation.askQuestion(context, "Provide a travel plan tailored for this user.");
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // DTO for the travel plan request
    public static class TravelPlanRequest {
        private int age;
        private String interest;
        private String location;

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

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
