package crosso.workshop.schools_api.utils.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class APIResponse<T> {

    private T body;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String uri;
}
