package com.rean.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDateTime;
import java.util.List;


@Cacheable
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)

@SQLDelete(sql = "UPDATE tbl_course SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")

@NamedQueries(
        value = {
                @NamedQuery(name = "query_get_all_courses", query = "SELECT c FROM Course c"),
                @NamedQuery(name = "query_get_all_join_fetch", query = "SELECT c FROM Course c JOIN FETCH c.students s"),
        }
)

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "course_name", nullable = false, length = 200)
    private String name;

    @ManyToMany(mappedBy = "courses")
    @Setter
    private List<Student> students;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;

    @PreRemove
    private void preRemove() {
        this.isDeleted = true;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                '}';
    }

    private void addStudent(Student student) {
        this.students.add(student);
    }
    private void removeStudent(Student student) {
        this.students.remove(student);
    }

    private Course(String name) {
        this.name = name;
    }

    public static Course of(String name) {
        return new Course(name);
    }

}
