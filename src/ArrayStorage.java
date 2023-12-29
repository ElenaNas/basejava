import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                storage[i] = null;
            }
        }
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
        }
    }

    Resume get(String uuid) {
        Resume resume = null;
        for (Resume value : storage) {
            if (value != null && value.uuid.equals(uuid)) {
                resume = value;
                break;
            }
        }
        return resume;
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (this.storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                for (int j = i; j < storage.length - 1; j++) {
                    storage[j] = storage[j + 1];
                }
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int firstNullValue = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                firstNullValue = i;
                break;
            }
        }
        return Arrays.copyOfRange(storage, 0, firstNullValue);
    }

    int size() {
        return storage.length;
    }
}
