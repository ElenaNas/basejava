package webapp.storage;

import webapp.model.Resume;

public abstract class AbstractArrayStorage implements IStorage {
    protected int size;
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];

    public int size() {
        return size;
    }

    protected abstract int findIndex(String uuid);

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("Resume " + uuid + " was not found in the storage");
        }
        return null;
    }
}
