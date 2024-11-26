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
                    "You are a travel assistant AI. Generate a %d-day travel plan for the following user:\n" +
                            "- Age: %d\n" +
                            "- Interest: %s\n" +
                            "- Destination: %s\n" +
                            //"- Duration: %d days\n" +
                            "Please create a detailed travel plan for them based on their interest, destination, and age.",
                    request.getDuration(),
                    request.getAge(),
                    request.getInterest(),
                    request.getDestination()
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
        private String destination;
        private int duration;

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
    }
}
