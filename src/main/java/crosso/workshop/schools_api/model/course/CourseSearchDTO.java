package crosso.workshop.schools_api.model.course;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseSearchDTO {
    private String name;
    private String description;
    private String code;
}
