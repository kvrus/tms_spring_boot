package ru.moscow.tms.tms.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@NoArgsConstructor
/*@AllArgsConstructor
@Getter
@Setter
public class TPlanCalculation {

}*/

public interface TPlanCalculation {
    Long getPlanId();
    Long getCaseCount();
    String getName();
}