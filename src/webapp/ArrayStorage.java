package webapp;

import java.util.Arrays;

public class ArrayStorage {
    protected int size;
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[10000];

    protected void clear() {
        Arrays.fill(getAll(), null);
        size = 0;
    }

    protected int findIndex(String uuid) {
        int index;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                index = i;
                return index;
            }
        }
        return -1;
    }

    protected void update(Resume r) {
        int index = findIndex(r.uuid);
        if (index >= 0) {
            storage[index] = r;
            System.out.println("Resume " + r + " has been updated");
        } else {
            System.out.println("Resume " + r + " was not found in the storage");
        }
    }

    protected void save(Resume r) {
        int index = findIndex(r.uuid);
        if (size == STORAGE_LIMIT) {
            System.out.println("Storage is full.");
        } else if (index >= 0) {
            System.out.println("Storage already contains resume " + r + ".");
        } else {
            storage[size] = r;
            size++;
        }
    }

    protected Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("Resume " + uuid + " was not found in the storage");
        }
        return null;
    }

    protected void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("There's no such resume in the storage.");
        }
    }

    protected Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected int size() {
        return size;
    }
}
