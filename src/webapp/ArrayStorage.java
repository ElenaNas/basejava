package webapp;

import java.util.Arrays;

public class ArrayStorage {
    protected int size;
    protected int index;
    protected final Resume[] storage = new Resume[10000];

    protected void clear() {
        Arrays.fill(getAll(), null);
        size = 0;
    }

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                index = i;
                return index;
            }
        }
        return -1;
    }

    protected void update(Resume r) {
        if (findIndex(r.uuid) > -1) {
            for (int i = 0; i < size; i++) {
                if (storage[i].uuid.equals(r.uuid)) {
                    storage[i] = r;
                    System.out.println("Resume " + r + " has been updated");
                    break;
                }
            }
        } else {
            System.out.println("Resume " + r + " was not found in the storage");
        }
    }

    protected void save(Resume r) {
        if (size == storage.length) {
            System.out.println("Storage is full.");
        } else if (findIndex(r.uuid) > -1) {
            System.out.println("Storage already contains resume " + r + ".");
        } else {
            storage[size] = r;
            size++;
        }
    }

    protected Resume get(String uuid) {
        if (findIndex(uuid) > -1) {
            return storage[findIndex(uuid)];
        } else {
            System.out.println("Resume " + uuid + " was not found in the storage");
        }
        return null;
    }

    protected void delete(String uuid) {
        if (findIndex(uuid) > -1) {
            storage[findIndex(uuid)] = storage[size - 1];
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
