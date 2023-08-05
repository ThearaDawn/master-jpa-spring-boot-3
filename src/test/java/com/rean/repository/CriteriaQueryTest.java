package com.rean.repository;

import com.rean.MasterJpaSpringBoot3Application;
import com.rean.model.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MasterJpaSpringBoot3Application.class)
public class CriteriaQueryTest {

    @Autowired
    EntityManager entityManager;


    // Create a query using Java Criteria Builder to retrieve all courses
    @Test
    public void findAllCourses() {

        // 1. Use Criteria Builder to create a Criteria Query returning the expected result object
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);

        // 2. Define roots for tables which are involved in the query
        Root<Course> courseRoot = criteriaQuery.from(Course.class);

        // 3. Build the TypedQuery using the entity manager and criteria query
        TypedQuery<Course> courseTypedQuery = entityManager.createQuery(criteriaQuery.select(courseRoot));

        // 4. Execute query and get result list
        List<Course> courses = courseTypedQuery.getResultList();
        log.info("courses: {}", courses);

    }


    // Query where condition
    @Test
    public void findCourseWithCertainPattern() {
        // 1. Use Criteria Builder to create a Criteria Query returning the expected result object
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);

        // 2. Define roots for tables which are involved in the query
        Root<Course> courseRoot = criteriaQuery.from(Course.class);

        // 3. Define predicates etc using Criteria Builder
        Predicate like = criteriaBuilder.like(courseRoot.get("name"), "%Java");

        // 4. Add predicates etc to the Criteria Query
        criteriaQuery.where(like);

        // 5. Build the TypedQuery using the entity manager and criteria query
        TypedQuery<Course> query = entityManager.createQuery(criteriaQuery.select(courseRoot));

        // 6. Execute query and get result list
        List<Course> courses = query.getResultList();
        log.info("courses: {}", courses);
    }

    @Test
    public void join() {
        // 1. Use Criteria Builder to create a Criteria Query returning the expected result object
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);

        // 2. Define roots for tables which are involved in the query
        Root<Course> courseRoot = criteriaQuery.from(Course.class);

        // 3. Define predicates etc using Criteria Builder
        Join<Object, Object> join = courseRoot.join("students");
        // 4. Add predicates etc to the Criteria Query
        TypedQuery<Course> query = entityManager.createQuery(criteriaQuery.select(courseRoot));
        // 5. Build the TypedQuery using the entity manager and criteria query
        List<Course> courses = query.getResultList();
        // 6. Execute query and get result list
        log.info("courses: {}", courses);
    }

    @Test
    public void leftJoin() {
        // 1. Use Criteria Builder to create a Criteria Query returning the expected result object
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);

        // 2. Define roots for tables which are involved in the query
        Root<Course> courseRoot = criteriaQuery.from(Course.class);

        // 3. Define predicates etc using Criteria Builder
        Join<Object, Object> join = courseRoot.join("students", JoinType.LEFT);
        // 4. Add predicates etc to the Criteria Query
        TypedQuery<Course> query = entityManager.createQuery(criteriaQuery.select(courseRoot));
        // 5. Execute query and get result list
        List<Course> courses = query.getResultList();
        log.info("courses: {}", courses);
    }
}
