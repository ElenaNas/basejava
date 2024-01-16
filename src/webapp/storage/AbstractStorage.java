package webapp.storage;

import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.exception.StorageException;
import webapp.model.Resume;

public abstract class AbstractStorage implements IStorage {
    protected int size;
    protected static final int STORAGE_LIMIT = 10000;

    private Object searchKey;

    public abstract Object getSearchKey(String uuid);

    public abstract boolean isExisting(Object searchKey);


    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExisting(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExisting(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage is full.", r.getUuid());
        } else {
            searchKey = getNotExistingSearchKey(r.getUuid());
            doSave(r, searchKey);
        }
    }

    public abstract void doSave(Resume r, Object searchKey);

    public Resume get(String uuid) {
        searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public abstract Resume doGet(Object uuid);

    public void delete(String uuid) {
        searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    public abstract void doDelete(Object searchKey);

    public void update(Resume r) {
        searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(r, searchKey);
    }

    public abstract void doUpdate(Resume r, Object searchKey);

    @Override
    public int size() {
        return size;
    }
}



