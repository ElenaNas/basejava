package webapp.storage;

import webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {

    protected final Map<String, Resume> resumeMap = new LinkedHashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return resumeMap.get(uuid);
    }

    @Override
    protected boolean isExisting(Resume resume) {
        return resume != null;
    }

    @Override
    protected void doSave(Resume r, Resume resume) {
        resumeMap.put(r.getUuid(), r);
        size++;
    }

    @Override
    protected Resume doGet(Resume resume) {
        return (resume);
    }

    @Override
    protected void doDelete(Resume resume) {
        resumeMap.remove((resume).getUuid());
        size--;
    }

    @Override
    protected void doUpdate(Resume r, Resume resume) {
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
