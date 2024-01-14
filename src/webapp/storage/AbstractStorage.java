package webapp.storage;

public abstract class AbstractStorage implements IStorage {
    protected int size;
    protected static final int STORAGE_LIMIT = 10000;

    protected abstract int findIndex(String uuid);

    @Override
    public int size() {
        return size;
    }
}
