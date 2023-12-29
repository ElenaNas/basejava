import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    public static int size = 0;
    Resume[] storage = new Resume[10000];

    void clear() {
        if (size > 0) {
            for (int i = 0; i < size(); i++) {
                storage[i] = null;
            }
        }
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[size - 1] = storage[i];
                storage[size - 1] = null;
                size--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    int size() {
        return size;
    }
}
