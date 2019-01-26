package immortal.database.components;

import java.util.Map;

public final class Set implements SqlInterface {
    private final Map<String, String> set;

    public Set(Map<String, String> set) {
        this.set = set;
    }

    @Override
    public String toString() {
        if(set.size() > 0) return set.toString().replaceAll("\\{|\\}", "");
        return new String();
    }
}
