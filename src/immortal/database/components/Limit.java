package immortal.database.components;

public final class Limit implements SqlInterface {
    private final int limit;

    public Limit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return Integer.toString(limit);
    }
}
