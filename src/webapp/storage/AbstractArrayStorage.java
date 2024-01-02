package webapp.storage;

import webapp.model.Resume;

public abstract class AbstractArrayStorage implements IStorage {
    protected int size;
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    public int size() {
        return size;
    }
}
