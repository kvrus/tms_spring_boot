package ru.moscow.tms.tms.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moscow.tms.core.model.BaseDictionaryEntity;

@Entity
@Table(name = "test_case_priority")
public class TCasePriority extends BaseDictionaryEntity {}
