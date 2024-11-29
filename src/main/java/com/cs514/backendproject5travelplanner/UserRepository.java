
package com.cs514.backendproject5travelplanner;

import java.util.List;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;


public interface UserRepository extends DatastoreRepository<User, Long> {

    List<User> findByInterest(String interest);

    List<User> findByAge(int age);

    List<User> findByUserID(String userID);

    List<User> findByUserName(String userName);

    List<User> findByGender(String gender);
}