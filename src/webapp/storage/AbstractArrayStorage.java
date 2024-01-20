package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage implements IStorage {

    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void fillDeletedElement(int index);

    @Override
    public Resume doGet(Object uuid) {
        return storage[(int) uuid];
    }

    @Override
    public void doSave(Resume r, Object searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage is full.", r.getUuid());
        }
        insertElement(r, (Integer) searchKey);
        size++;
    }

    @Override
    public void doDelete(Object searchKey) {
        fillDeletedElement((Integer) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public void doUpdate(Resume r, Object searchKey) {
        storage[(int) searchKey] = r;
        System.out.println("Resume " + r + " has been updated");
    }

    @Override
    public List<Resume> doCopy() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public boolean isExisting(Object searchKey) {
        return (Integer) searchKey >= 0;
    }
}
