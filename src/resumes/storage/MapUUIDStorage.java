package resumes.storage;

import resumes.model.Resume;

import java.util.*;

public class MapUUIDStorage extends AbstractStorage<String> {

    protected final Map<String, Resume> resumeMap = new LinkedHashMap<>();

    @Override
    public String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public boolean isExisting(String uuid) {
        return resumeMap.containsKey(uuid);
    }

    @Override
    public void doSave(Resume r, String searchKey) {
        resumeMap.put(searchKey, r);
    }

    @Override
    public Resume doGet(String uuid) {
        return resumeMap.get(uuid);
    }

    @Override
    public void doDelete(String uuid) {
        resumeMap.remove(uuid);
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
    }

    @Override
    public int size() {
        return resumeMap.size();
    }
}
