package com.rean.repository;

import com.rean.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByName(String name);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE Course c SET c.name = ?2 WHERE c.id = ?1")
    int updatedById(Long id, String name);

    @Query(name = "query_get_all_courses")
    List<Course> findAllWithSpringUsingNamingQuery();


}
