package com.rean.repository;

import com.rean.MasterJpaSpringBoot3Application;
import com.rean.model.Course;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MasterJpaSpringBoot3Application.class)
public class CourseSpringDataRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    @DirtiesContext
    public void save() {
        Course course = Course.builder()
                .name("Microservices in 100 Steps")
                .build();
        courseRepository.save(course);
        log.info("course: {}", course);

        assertEquals(1, courseRepository.findAll().size());

        // update
        course.setName("Microservices in 100 Steps - Updated");
        courseRepository.save(course);
        log.info("course: {}", course);
    }

    @Test
    public void findById() {
        Course course =  Course.builder()
                        .name("Java in 50 Steps")
                        .build();
        courseRepository.save(course);

        log.info("course: {}", course);
        assertEquals("Java in 50 Steps", course.getName());

        List<Course> courses = courseRepository.findAll();
        log.info("courses: {}", courses);
    }

    @Test
    public void findAllCoursesAndSortByName() {
        Course course =  Course.builder()
                .name("Java in 50 Steps")
                .build();
        courseRepository.save(course);
        Course course1 =  Course.builder()
                .name("Spring in 50 Steps")
                .build();
        courseRepository.save(course1);
        Sort sort = Sort.by(Sort.Direction.DESC, "name");
        List<Course> courses = courseRepository.findAll(sort);
        log.info("courses: {}", courses);
    }

    @Test
    public void findAllWithPagination() {
        Course course =  Course.builder()
                .name("Java in 50 Steps")
                .build();
        courseRepository.save(course);
        Course course1 =  Course.builder()
                .name("Spring in 50 Steps")
                .build();
        courseRepository.save(course1);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Course> findAll = courseRepository.findAll(pageable);

        log.info("findAll: {}", findAll.getContent());
    }

}
