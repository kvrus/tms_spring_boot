package ru.moscow.tms.tms.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moscow.tms.auth.models.UserEntity;
import ru.moscow.tms.core.model.BaseTmsEntity;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "test_case")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TCaseWithPlan extends BaseTmsEntity {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "test_plan_case",
            joinColumns = @JoinColumn(name = "case_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_id")
    )
    private List<TPlan> plans = List.of();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private UserEntity author;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private TCaseStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private TCaseCategory category;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "priority_id", referencedColumnName = "id")
    private TCasePriority priority;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assignee_tester_id", referencedColumnName = "id")
    private UserEntity assigneeTester;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reviewer_tester_id", referencedColumnName = "id")
    private UserEntity reviewerTester;

    @Column(name="creation_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date creation_date;

    private boolean is_automated;

    private String name;

    private String description;

    private String requirements;

}
