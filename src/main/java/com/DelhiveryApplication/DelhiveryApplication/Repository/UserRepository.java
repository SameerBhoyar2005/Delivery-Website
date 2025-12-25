package com.DelhiveryApplication.DelhiveryApplication.Repository;

import com.DelhiveryApplication.DelhiveryApplication.Data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
