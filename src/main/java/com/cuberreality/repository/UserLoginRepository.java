package com.cuberreality.repository;


import com.cuberreality.entity.UserLogin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserLoginRepository extends MongoRepository<UserLogin, String> {

    UserLogin findByPhoneNumber(String phone);

    UserLogin findByPhoneNumberAndUserType(String phone, String userType);

}
