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
@Table(name = "test_plan")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TPlan extends BaseTmsEntity {

    private Long parent_id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private UserEntity author;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_type_id", referencedColumnName = "id")
    private TPlanType planType;

    @Column(name="creation_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date creationDate;

    private boolean is_active;

    private String description;

    private String name;
}