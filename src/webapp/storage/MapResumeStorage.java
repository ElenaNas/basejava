package webapp.storage;

import webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {

    protected final Map<String, Resume> resumeMap = new LinkedHashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return resumeMap.get(uuid);
    }

    @Override
    protected boolean isExisting(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        resumeMap.put(r.getUuid(), r);
        size++;
    }

    @Override
    protected Resume doGet(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void doDelete(Object searchKey) {
        resumeMap.remove(((Resume) searchKey).getUuid());
        size--;
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        resumeMap.replace(r.getUuid(), r);
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
