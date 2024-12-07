# Backend Project 5 - Travel Planner
## URL:
### gcloud deployed URL: https://project5-frontend.uw.r.appspot.com
### github repo frontend URL:https://github.com/pfchensky/project5-frontend

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

## Video for app: https://www.youtube.com/watch?v=rsF7a_hXln4
## Video for test User: https://meeting.tencent.com/crm/NQQB6wdp38

## How to Run

### **For Backend**

1. **Clone the Repository**  
   Run the following commands to clone the repository and navigate to the project directory:
   ```bash
   git clone https://github.com/pfchensky/project5-backend.git
   cd project5-backend
   ```

2. **Set Up the Environment Variable (For macOS)**  
   a. Open your `.zshrc` file:
   ```bash
   nano ~/.zshrc
   ```

   b. Add the following line to export your OpenAI API Key:
   ```bash
   export OPENAI_API_KEY="your_openai_api_key"
   ```

   c. Save the file:
   - Press `CTRL + O` to save.
   - Press `ENTER` to confirm the filename.
   - Press `CTRL + X` to exit Nano.

   d. Reload the `.zshrc` file to apply the changes:
   ```bash
   source ~/.zshrc
   ```

3. **Verify Environment Variable**  
   Confirm that the `OPENAI_API_KEY` is set by running:
   ```bash
   echo $OPENAI_API_KEY
   ```
   You should see your API key printed in the terminal.

4. **Run the Backend Application**  
   Build and run the backend application using Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
   The backend should now be running at:
   ```
   http://localhost:8080
   ```

---

## How to Deploy

### **Deploy to Google Cloud**

1. **Authenticate with Google Cloud**  
   Run the following command to log in to your Google Cloud account:
   ```bash
   gcloud auth login
   ```

2. **Configure Google Cloud Project**  
   Initialize your Google Cloud project:
   ```bash
   gcloud init
   ```

3. **Deploy the Application**  
   Deploy the application to Google Cloud App Engine:
   ```bash
   gcloud app deploy
   ```

4. **Access the Application**  
   Once the deployment is complete, your application will be accessible at:  
   [https://project5-backend.uw.r.appspot.com](https://project5-backend.uw.r.appspot.com)

   

## How to Run

### **For Frontend**

1. **Clone the Repository**  
   Run the following commands to clone the repository and navigate to the project directory:
   ```bash
   git clone https://github.com/pfchensky/project5-frontend.git
   cd project5-frontend
   ```

2. **Set Up the Environment Variables**  
   a. Create a `.env` file in the root directory of the project.

   b. Add the following Firebase environment variables to the `.env` file **(replace the placeholder values with your own Firebase project configuration)**:
   ```env
   REACT_APP_FIREBASE_API_KEY=your_firebase_api_key
   REACT_APP_FIREBASE_AUTH_DOMAIN=your_firebase_auth_domain
   REACT_APP_FIREBASE_PROJECT_ID=your_firebase_project_id
   REACT_APP_FIREBASE_STORAGE_BUCKET=your_firebase_storage_bucket
   REACT_APP_FIREBASE_MESSAGING_SENDER_ID=your_firebase_messaging_sender_id
   REACT_APP_FIREBASE_APP_ID=your_firebase_app_id
   ```

   c. Save the file.

   **Note:** To find your Firebase project configuration, go to the Firebase Console, open your project, and navigate to **Project Settings > General > Your apps > Firebase SDK snippet > Config**.

3. **Install Dependencies**  
   Install all required dependencies:
   ```bash
   npm install
   ```

4. **Run the Frontend Application**  
   Start the development server:
   ```bash
   npm start
   ```
   The frontend should now be running at:
   ```
   http://localhost:3000
   ```

---

## How to Deploy

### **Deploy to Google Cloud**

1. **Build the Application**  
   Create an optimized production build:
   ```bash
   npm run build
   ```

2. **Authenticate with Google Cloud**  
   Log in to your Google Cloud account:
   ```bash
   gcloud auth login
   ```

3. **Configure Google Cloud Project**  
   Initialize your Google Cloud project:
   ```bash
   gcloud init
   ```

4. **Deploy the Application**  
   Deploy the application to Google Cloud App Engine:
   ```bash
   gcloud app deploy
   ```

5. **Access the Application**  
   Once the deployment is complete, your application will be accessible at:
   ```
   https://project5-frontend.uw.r.appspot.com
   ```
   **Note:** Remember to add this link to your Firebase Authorized Domains under the Firebase Console.Navigate to **Project Settings > Authentication > Setting > Authorized domains** to add the deployed domain. Without this step, you may encounter issues logging in with Firebase.

