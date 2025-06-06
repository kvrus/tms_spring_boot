package ru.moscow.tms.tms.controller.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moscow.tms.auth.models.UserEntity;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestPlanResponseDto {
    private Long id;
    private String name;
    private String description;
    private String typeName;
    private String authorName;
    private Date creation_date;

    static public String getUserNameIfExists(UserEntity author) {
        if(author == null) {
            return "no author";
        } else {
            return author.getUsername();
        }
    }
}
