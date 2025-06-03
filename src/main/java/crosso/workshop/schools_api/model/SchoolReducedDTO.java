package crosso.workshop.schools_api.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchoolReducedDTO {

    @NotNull(message = "School's id cannot be null")
    private UUID id;

    private String name;
    private Date startDate;
}
