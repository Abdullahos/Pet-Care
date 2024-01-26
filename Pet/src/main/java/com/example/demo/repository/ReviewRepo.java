package com.example.demo.repository;

import com.example.demo.entity.DepartmentRole;
import com.example.demo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
    @Query("select r from  Review r" +
            " where r.application.id = ?1 and r.skillId = ?2 ")
    List<Review> findReviewByApplicationAndSkillId(Long applicationId, Long skillId);

    @Query("DELETE FROM Review r WHERE r.application.id = ?1 ")
    @Modifying
    List<Review> findReviewByApplicationId(Long applicationId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Review r WHERE r.application.id = :appId AND r.skillId = :skillId AND r.role = :role")
    void deleteReviewByApplicationAndSkillIdAndRole(
            @Param("appId") Long appId,
            @Param("skillId") Long skillId,
            @Param("role") DepartmentRole role);

}
