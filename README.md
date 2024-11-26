# Backend Project 5 - Travel Planner

## Overview
The **Travel Planner** application is designed to assist users in creating personalized travel itineraries. By using AI, the application customized travel plans based on user preferences, such as age, interests, location, and travel duration. This helps users save time and effort in planning trips, offering a more seamless and customized travel experience.

After the class, we plan to enhance the project by adding specific travel data features, enabling the AI to generate even more detailed and customized itineraries. These updates will provide users with richer travel suggestions and a broader range of options.

---

## Database Description
Our database includes a table named `users`, which stores user information, including:
- **userID** (email)
- **userAge**
- **userInterest**
- **userLocation**

If a user provides this information, it is saved in the database. When the user logs in, they can view and manage their stored information. Specifically:
- **Edit:** Users can update individual fields (e.g., `userAge`, `userInterest`, `userLocation`).
- **Delete:** Users can remove all their stored information.
- **Add New:** Users can input new information, which will be stored as a new record in the database.

---

## AI Description
Users can provide new information (e.g., `userAge`, `userInterest`, `userLocation`) or choose to use their previously stored information from the database. Additionally, users must input the **travel duration (in days)** via the frontend, although this field is not stored in the database.

All this information is passed to the AI as input context. Based on this combined data, the AI generates a travel itinerary tailored to the user's preferences and trip duration.

---

## Frontend Repository URL:
[Frontend GitHub Repository](https://github.com/pfchensky/project5-frontend)

