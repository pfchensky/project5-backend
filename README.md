# Backend Project 5 - Travel Planner

## Overview
The **Travel Planner** The application is designed for users to create a personalized travel plan. By applying OpenAI and providing user trip info, this application generates customized a group travel plan based on a list of users preferences, such as age and interests. A user can add and delete his group members and update their interest. Those changes will be saved in database. With providing trip info, the user can generate his group travel plan for selected members in his travel group. This App offers an efficient and productive way to design a customized travel experience.

For extension, we propose to add more functional applications for this app in future, such as adding an agent to search and book for accommodation and flight tickets. We also consider to add a more efficient global database, such as special events for each date in each worldwide location. Once a user searches for a specific location on a specific date, a different user can reuse those database without calling the AI again.

---

## Database Description
Our database includes a table named `users`, which stores user information, including:
- **userID** (email)
- **userName**(group member)
- **userAge**
- **userGender**
- **userInterest**

UserID and userName is one-to-many mapping. UserName to userAge, userGender, and userInterest is one-to-one mapping.

If a user provides this information, it is saved in the database. When the user logs in, they can view and manage their stored information. Specifically:
- **Update:** Users can update individual fields (`userInterest`).
- **Delete:** Users can remove all their stored group member.
- **Select:** Users can select his group member. Nothing changed in database.
- **Add New:** Users can input new information, which will be stored as a new record in the database.


---

## AI Description
Users first need to select member for his group to travel. After that, Users provide a trip information (e.g., `Duration`, `Destination`, `Month to travel`). users must input the **travel info** via the frontend, which is not stored in our database. After click submit, all collected information will be passed to the AI, and the AI will generates a travel itinerary tailored to user's travel group based on every members info. For group member whose age under 16, AI will give them individual plan with a adult accompanied.

---

## URL:
## Video for app:
## Video for test User:


