package immortal.database.components;

import java.util.Set;

public final class Columns implements SqlInterface {
    private final Set<String> columns;

    public Columns(Set<String> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        if(columns.size() > 0) return columns.toString().replaceAll("\\[|\\]", "");
        return new String();
    }
}