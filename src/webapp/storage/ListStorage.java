package webapp.storage;

import webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    protected final List<Resume> resumeList = new ArrayList<>();

    @Override
    public Integer getSearchKey(String uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (resumeList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public boolean isExisting(Object searchKey) {
        return searchKey != null;
    }

    @Override
    public void clear() {
        resumeList.clear();
        size = 0;
    }

    @Override
    public void doUpdate(Resume r, Object searchKey) {
        resumeList.set((Integer) searchKey, r);
        System.out.println("Resume " + r + " has been updated");
    }

    @Override
    public void doSave(Resume r, Object searchKey) {
        resumeList.add(r);
        size++;
    }

    @Override
    public Resume doGet(Object uuid) {
        return resumeList.get((Integer) uuid);
    }

    @Override
    public void doDelete(Object searchKey) {
        resumeList.remove((int) searchKey);
        size--;
    }

    @Override
    public Resume[] getAll() {
        return resumeList.toArray(new Resume[size]);
    }
}
