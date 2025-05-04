package crosso.workshop.schools_api.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EntityNotFoundException extends Exception {

    private String error;
    private String idNotFound;

    public EntityNotFoundException(String entity, String id) {
        super(String.format("Entity %s with id %s not found", entity, id));
        this.error = String.format("%s.id", entity);
        this.idNotFound = id;
    }
}
