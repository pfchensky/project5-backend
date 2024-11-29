package com.cs514.backendproject5travelplanner;

import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

@Entity(name = "users")
public class User {
    @Id
    Long id;
    String userID;
    String userName;
    String interest;
    int age;
    String gender;

    public User() {
    }

    public User(String userID,String userName,String interest, int age, String gender) {
        this.userID=userID;
        this.userName=userName;
        this.interest=interest;
        this.age = age;
        this.gender=gender;

    }

    public long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id=id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userID='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", interest='" + interest + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}