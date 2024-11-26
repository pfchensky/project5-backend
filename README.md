# Backend Project 5 - Travel Planner

## Overview
The **Travel Planner** The application is designed for users to create a personalized travel plan. By applying OpenAI and providing user destination and travel duration, this application generates customized travel plans based on user preferences, such as age and interests. It offers an efficient and productive way to design a customized travel experience.

For extension, we propose to add more functional applications in this app, such as adding an agent to search and book for accommodation and flight tickets. We also consider to add a more efficient global database, such as special events for each date in each worldwide location. Once a user searches for a specific location on a specific date, a different user can reuse those database without calling the AI again.

---

## Database Description
Our database includes a table named `users`, which stores user information, including:
- **userID** (email)
- **userAge**
- **userInterest**
- **userDestination**

If a user provides this information, it is saved in the database. When the user logs in, they can view and manage their stored information. Specifically:
- **Edit:** Users can update individual fields (e.g., `userAge`, `userInterest`, `userDestination`).
- **Delete:** Users can remove all their stored information.
- **Add New:** Users can input new information, which will be stored as a new record in the database.

---

## AI Description
Users can provide a information (e.g., `userAge`, `userInterest`, `userDestination`) or choose to use their previously stored information from the database. Additionally, users must input the **travel duration (in days)** via the frontend, which is not stored in our database.

All collected information is will be passed to the AI, and the AI will generates a travel itinerary tailored to the user's preferences and trip duration.

---

## Frontend Repository URL:
[Frontend GitHub Repository](https://github.com/pfchensky/project5-frontend)

