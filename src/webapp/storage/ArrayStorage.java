package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    protected int size;
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[10000];

    public void clear() {
        Arrays.fill(getAll(), null);
        size = 0;
    }

    public int findIndex(String uuid) {
        int index;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                index = i;
                return index;
            }
        }
        return -1;
    }

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
            System.out.println("Resume " + r + " has been updated");
        } else {
            System.out.println("Resume " + r + " was not found in the storage");
        }
    }

    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (size == STORAGE_LIMIT) {
            System.out.println("Storage is full.");
        } else if (index >= 0) {
            System.out.println("Storage already contains resume " + r + ".");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("Resume " + uuid + " was not found in the storage");
        }
        return null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("There's no such resume in the storage.");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
