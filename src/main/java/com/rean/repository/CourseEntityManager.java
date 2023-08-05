package com.rean.repository;

import com.rean.model.Course;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public class CourseEntityManager {

    private final EntityManager entityManager;

    public Course findById(Long id) {
        return entityManager.find(Course.class, id);
    }

    public void deleteById(Long id) {
        Course course = findById(id);
        if(null != course) {
            entityManager.remove(course);
        }
    }

    public Course save(Course course) {
        if(null == course.getId()) {
            // save new record (insert
            entityManager.persist(course);
        } else {
            // update existing record
            entityManager.merge(course);
        }
        return course;
    }
}
