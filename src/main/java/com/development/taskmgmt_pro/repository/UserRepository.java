package com.development.taskmgmt_pro.repository;

import com.development.taskmgmt_pro.model.Task;
import com.development.taskmgmt_pro.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAll(Pageable pageable);

    List<Task> findByUserId(Long userId);

    @Query("SELECT u FROM User u WHERE u.userName LIKE %:keyword%")
    List<User> searchUsersByUserName(@Param("keyword") String keyword);

    @Query("SELECT u FROM User u WHERE u.fullName LIKE %:keyword%")
    List<User> searchUsersByFullName(@Param("keyword") String keyword);

    @Modifying @Query("UPDATE User SET emailId = :emailId WHERE userId = :userId")
    int updateUserEmailIdByUserId(@Param("emailId") String emailId, @Param("userId") Long userId);

    //save(User user);
    //Optional<User> findById(Long userId);
    //void deleteById(userId);
    //void deleteByUsername(String userName);
    //Optional<User> findByUserName(String userName);
    //Optional<User> findByEmailID(String emailId);
    //List<User> findByEmailID(String emailId);
}
