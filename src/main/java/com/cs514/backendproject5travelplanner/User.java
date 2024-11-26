package com.cs514.backendproject5travelplanner;

import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

@Entity(name = "users")
public class User {
    @Id
    Long id;

    String userID;

    String interest;

    int age;

    String location;

    public User() {
    }

    public User(String userID,String interest, int age, String location) {
        this.userID=userID;
        this.interest=interest;
        this.age = age;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    @Override
    public String toString() {
        return "{" +
                "id:" + this.id +",userID:'" + this.userID + '\''+
                ", interest:'" + this.interest + '\'' +
                ", age:'" + this.age + '\'' +
                ", location:" + this.location +
                '}';
    }
}