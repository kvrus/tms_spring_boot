package ru.moscow.tms.tms.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moscow.tms.auth.models.UserEntity;
import ru.moscow.tms.core.model.BaseTmsEntity;

import java.util.Date;

@Entity
@Table(name = "test_execution")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TExecution extends BaseTmsEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "case_id", referencedColumnName = "id")
    private TCase testCase;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "run_id", referencedColumnName = "id")
    private TRun testRun;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private TExecutionStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tester_id", referencedColumnName = "id")
    private UserEntity tester;

    @Column(name="creation_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date creation_date;

    @Column(name="start_date", columnDefinition="TIMESTAMP")
    private Date start_date;

    @Column(name="finish_date", columnDefinition="TIMESTAMP")
    private Date finish_date;

    private String description;

    private String name;
}