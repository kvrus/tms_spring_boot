package ru.moscow.tms.tms.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moscow.tms.auth.models.UserEntity;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "test_plan")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parent_id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "test_plan_case",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "case_id")
    )
    private List<TCase> cases = List.of();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private UserEntity author;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_type_id", referencedColumnName = "id")
    private TPlanType planType;

    @Column(name="creation_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date creation_date;

    private boolean is_active;

    private String description;

    private String name;
}