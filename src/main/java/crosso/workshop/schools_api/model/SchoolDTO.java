package crosso.workshop.schools_api.model;

import lombok.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDTO {
    private UUID id;

    private String name;
    private Date startDate;

    private Set<CourseDTO> courses;
}
