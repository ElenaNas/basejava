package webapp.storage;

import webapp.model.Resume;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    protected final Map<String, Resume> resumeMap = new LinkedHashMap<>();

    @Override
    public Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public boolean isExisting(Object searchKey) {
        return resumeMap.containsKey((String) searchKey);
    }

    @Override
    public void doSave(Resume r, Object searchKey) {
        resumeMap.put((String) searchKey, r);
        size++;
    }

    @Override
    public Resume doGet(Object uuid) {
        return resumeMap.get(uuid);
    }

    @Override
    public void doDelete(Object searchKey) {
        resumeMap.remove(searchKey);
        size--;
    }

    @Override
    public void doUpdate(Resume r, Object searchKey) {
        resumeMap.replace((String) searchKey, r);
    }

    @Override
    public void clear() {
        resumeMap.clear();
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return resumeMap.values().toArray(new Resume[size]);
    }
}
