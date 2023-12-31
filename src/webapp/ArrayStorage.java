package webapp;

import java.util.Arrays;

public class ArrayStorage {
    int size;
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(getAll(), null);
        size = 0;
    }

    boolean containsResume(Resume r) {
        boolean b = false;
        for (Resume res : getAll()) {
            if (res.equals(r)) {
                b = true;
                break;
            }
        }
        return b;
    }

    boolean containsUuid(String id) {
        boolean b = false;
        for (Resume res : getAll()) {
            if (res.uuid.equals(id)) {
                b = true;
                break;
            }
        }
        return b;
    }

    void update(Resume r) {
        if (containsResume(r)) {
            System.out.println("Resume " + r + " is available for updates.");
        } else {
            System.out.println("Resume " + r + " was not found in the storage");
        }
    }

    void save(Resume r) {
        if (containsResume(r)) {
            System.out.println("Storage already contains resume " + r + ".");
            return;
        }
        if (size == storage.length) {
            System.out.println("Storage is full.");
            return;
        }
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        if (containsUuid(uuid)) {
            for (Resume res : getAll()) {
                if (res.uuid.equals(uuid)) {
                    return res;
                }
            }
        } else {
            System.out.println("Resume " + uuid + " was not found in the storage");
        }
        return null;
    }

    void delete(String uuid) {
        if (size == 0) {
            System.out.println("Storage is empty, no elements available for removal");
        } else {
            if (containsUuid(uuid)) {
                for (int i = 0; i < size; i++) {
                    if (storage[i].uuid.equals(uuid)) {
                        storage[i] = storage[size - 1];
                        storage[size - 1] = null;
                        size--;
                        break;
                    }
                }
            } else {
                System.out.println("There's no such resume in the storage.");
            }
        }
    }

    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }
}
