package crosso.workshop.schools_api.exception;

import lombok.Data;

@Data
public class EntityNotFoundException extends Exception {

    private String error;

    public EntityNotFoundException(String entity, String id) {
        super(String.format("Entity %s with id %s not found", entity, id));
        this.error = String.format("%s.id", entity);
    }
}
