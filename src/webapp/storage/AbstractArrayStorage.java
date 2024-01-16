package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage implements IStorage {

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void fillDeletedElement(int index);

    @Override
    public Resume doGet(Object uuid) {
        return storage[(int) uuid];
    }

    @Override
    public void doSave(Resume r, Object searchKey) {
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

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
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
