package webapp.storage;

import webapp.model.Resume;

import java.util.*;

public class MapUUIDStorage extends AbstractStorage<String> {

    protected final Map<String, Resume> resumeMap = new LinkedHashMap<>();

    @Override
    public String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public boolean isExisting(String uuid) {
        return resumeMap.containsKey((String) uuid);
    }

    @Override
    public void doSave(Resume r, String searchKey) {
        resumeMap.put(searchKey, r);
        size++;
    }

    @Override
    public Resume doGet(String uuid) {
        return resumeMap.get(uuid);
    }

    @Override
    public void doDelete(String uuid) {
        resumeMap.remove(uuid);
        size--;
    }

    @Override
    public void doUpdate(Resume r, String uuid) {
        resumeMap.replace(uuid, r);
    }

    @Override
    public List<Resume> doCopy() {
        return new ArrayList<>(resumeMap.values());
    }

    @Override
    public void clear() {
        resumeMap.clear();
        size = 0;
    }
}
