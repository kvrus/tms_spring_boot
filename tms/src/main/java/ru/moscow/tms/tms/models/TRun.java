package ru.moscow.tms.tms.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moscow.tms.auth.models.UserEntity;

import java.util.Date;

@Entity
@Table(name = "test_run")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TRun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private UserEntity author;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id", referencedColumnName = "id")
    private TPlan plan;

    @Column(name="creation_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date creation_date;

    @Column(name="start_date", columnDefinition="TIMESTAMP")
    private Date start_date;

    @Column(name="finish_date", columnDefinition="TIMESTAMP")
    private Date finish_date;

    private String description;

    private String name;
}