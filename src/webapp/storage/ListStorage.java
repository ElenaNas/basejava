package webapp.storage;

import webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

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
    public boolean isExisting(Integer searchKey) {
        return searchKey != null;
    }

    @Override
    public void clear() {
        resumeList.clear();
        size = 0;
    }

    @Override
    public void doUpdate(Resume r, Integer searchKey) {
        resumeList.set((Integer) searchKey, r);
        System.out.println("Resume " + r + " has been updated");
    }

    @Override
    public void doSave(Resume r, Integer searchKey) {
        resumeList.add(r);
        size++;
    }

    @Override
    public Resume doGet(Integer uuid) {
        return resumeList.get((Integer) uuid);
    }

    @Override
    public void doDelete(Integer searchKey) {
        resumeList.remove((int) searchKey);
        size--;
    }

    @Override
    public List<Resume> doCopy() {
        return new ArrayList<>(resumeList);
    }
}
