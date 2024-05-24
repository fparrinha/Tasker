package tasker.api.models;

import static tasker.api.utils.Utils.isStringNull;

public record TaskModel(Long id, String description, Integer priority) implements DataModel {
    @Override
    public boolean isDataCorrupt() {
        return id == null || isStringNull(description) || priority == null;
    }
}
