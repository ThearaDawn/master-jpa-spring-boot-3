package com.rean.repository;

import com.rean.MasterJpaSpringBoot3Application;
import com.rean.model.Course;
import com.rean.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Slf4j
@Transactional(isolation = Isolation.READ_COMMITTED)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MasterJpaSpringBoot3Application.class)
public class JPQLTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void findAllWithQuery() {
        Course course = Course.of("Java Programming");
        entityManager.persist(course);
        Query query = entityManager.createQuery("SELECT c FROM Course c");
        List<?> courses = query.getResultList();
        log.info("courses -> {}", courses);

    }

    @Test
    public void findAllWithTypeQuery() {
        Course course = Course.of("Java Programming");
        entityManager.persist(course);
        TypedQuery<Course> query = entityManager.createQuery("SELECT c FROM Course c", Course.class);
        List<Course> courses = query.getResultList();
        log.info("courses -> {}", courses);
    }

    @Test
    public void findAllWithWhereCondition() {
        Course course = Course.of("Java Programming");
        entityManager.persist(course);
        TypedQuery<Course> query = entityManager.createQuery("SELECT c FROM Course c WHERE c.name LIKE '%Java%'", Course.class);
        List<Course> courses = query.getResultList();
        log.info("courses -> {}", courses);
    }

    @Test
    public void findAllWithNameQuery() {
        Course course = Course.of("Java Programming");
        entityManager.persist(course);
        TypedQuery<Course> query = entityManager.createNamedQuery("query_get_all_courses", Course.class);
        List<Course> courses = query.getResultList();
        log.info("courses -> {}", courses);
    }

    @Test
    public void joinQuery() {
        Course course = Course.of("Java Programming");
        entityManager.persist(course);

        Student student = new Student(
                0L, "Rean", null,
                null, null, Arrays.asList(course)
        );
        studentRepository.save(student);

        Query query = entityManager.createQuery("SELECT c, s FROM Course c JOIN c.students s");
        List<Object[]> resultList = query.getResultList();
        log.info("resultList size -> {}", resultList.size());
        resultList.forEach(
                r -> {
                    log.info("course -> {}", r[0]);
                    log.info("student -> {}", r[1]);
                }
        );
    }

    @Test
    public void leftJoinQuery() {
        Course course = Course.of("Java Programming");
        entityManager.persist(course);

        Student student = new Student(
                0L, "Rean", null,
                null, null, Arrays.asList(course)
        );
        studentRepository.save(student);

        Query query = entityManager.createQuery("SELECT c, s FROM Course c left join c.students s");
        List<Object[]> resultList = query.getResultList();
        log.info("resultList size -> {}", resultList.size());
        resultList.forEach(
                r -> {
                    log.info("course -> {}", r[0]);
                    log.info("student -> {}", r[1]);
                }
        );
    }

    @Test
    public void crossJoinQuery() {
        Course course = Course.of("Java Programming");
        entityManager.persist(course);

        Student student = new Student(
                0L, "Rean", null,
                null, null, Arrays.asList(course)
        );
        studentRepository.save(student);

        Query query = entityManager.createQuery("SELECT c, s FROM Course c, Student s");
        List<Object[]> resultList = query.getResultList();
        log.info("resultList size -> {}", resultList.size());
        resultList.forEach(
                r -> {
                    log.info("course -> {}", r[0]);
                    log.info("student -> {}", r[1]);
                }
        );
    }

    @Test
    public void findCoursesAtLeastTwoStudents() {
        TypedQuery<Course> query = entityManager.createQuery(
                "SELECT c FROM Course c WHERE SIZE(c.students) >= 2", Course.class);

        TypedQuery<Course> queryOrderBy = entityManager.createQuery(
                "SELECT c FROM Course c order by size(c.students)" , Course.class);
    }
}
