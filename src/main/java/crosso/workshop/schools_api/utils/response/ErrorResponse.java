package crosso.workshop.schools_api.utils.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private String causedBy;
    private String description;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String uri;
}
