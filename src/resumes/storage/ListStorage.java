package resumes.storage;

import resumes.model.Resume;

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
    }

    @Override
    public int size() {
        return resumeList.size();
    }

    @Override
    public void doUpdate(Resume r, Integer searchKey) {
        resumeList.set(searchKey, r);
        System.out.println("Resume " + r + " has been updated");
    }

    @Override
    public void doSave(Resume r, Integer searchKey) {
        resumeList.add(r);
    }

    @Override
    public Resume doGet(Integer uuid) {
        return resumeList.get(uuid);
    }

    @Override
    public void doDelete(Integer searchKey) {
        resumeList.remove((int) searchKey);
    }

    @Override
    public List<Resume> doCopy() {
        return new ArrayList<>(resumeList);
    }
}
