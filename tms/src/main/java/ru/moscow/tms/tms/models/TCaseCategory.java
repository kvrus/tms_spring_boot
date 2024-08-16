package ru.moscow.tms.tms.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moscow.tms.core.model.BaseDictionaryEntity;
import ru.moscow.tms.core.model.BaseTmsEntity;

@Entity
@Table(name = "test_case_category")
public class TCaseCategory extends BaseDictionaryEntity {}
