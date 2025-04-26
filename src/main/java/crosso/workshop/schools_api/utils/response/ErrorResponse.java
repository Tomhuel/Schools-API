package crosso.workshop.schools_api.utils.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {
    private String errorCode;
    private String description;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String URI;
}
