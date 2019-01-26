package immortal.database.components;

import java.util.Collection;

public final class Values implements SqlInterface {
    private final Collection<String> values;

    public Values(Collection<String> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        if(values.size() > 0) return values.toString().replaceAll("\\[|\\]", "");
        return new String();
    }
}