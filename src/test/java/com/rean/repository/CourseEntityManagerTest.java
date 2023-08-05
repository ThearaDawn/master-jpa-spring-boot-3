package com.rean.repository;

import com.rean.MasterJpaSpringBoot3Application;
import com.rean.model.Course;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MasterJpaSpringBoot3Application.class)
public class CourseEntityManagerTest {

    @Autowired
    private CourseEntityManager courseEntityManager;

    @Test
    @DirtiesContext
    public void save() {
        Course course = Course.of("Java Programming");
        courseEntityManager.save(course);
        log.info("course: {}", course);

        assertEquals("Java Programming", course.getName());

        // update
        course.setName("Java Programming 2");
        courseEntityManager.save(course);

        log.info("course: {}", course);
        assertEquals("Java Programming 2", course.getName());
    }

    @Test
    public void findById() {
        Course course = Course.of("Spring Boot");
        courseEntityManager.save(course);

        log.info("course: {}", course);
        assertEquals("Spring Boot", course.getName());

        course = courseEntityManager.findById(course.getId());
        log.info("course: {}", course);
    }

    @Test
    @Transactional
    public void deleteById() {
        Course course = Course.of("Spring Boot");
        courseEntityManager.save(course);

        log.info("course: {}", course);
        assertEquals("Spring Boot", course.getName());

        courseEntityManager.deleteById(course.getId());
        course = courseEntityManager.findById(course.getId());
        log.info("course: {}", course);
    }
}
