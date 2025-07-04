package ru.moscow.tms.tms.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moscow.tms.core.model.BaseDictionaryEntity;

@Entity
@Table(name = "test_execution_status")
public class TExecutionStatus extends BaseDictionaryEntity {}

