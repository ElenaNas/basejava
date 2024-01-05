package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findIndex(String uuid) {
        Resume searchIndex = new Resume();
        searchIndex.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchIndex);
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            for (int i = index; i < size - 1; i++) {
                storage[i] = storage[i + 1];
            }
            size--;
        } else {
            System.out.println("There's no such resume in the storage.");
        }
    }

    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Storage is full.");
        } else if (size == 0) {
            storage[0] = resume;
            size++;
        } else {
            for (int i = 0; i < size; i++) {
                if(findIndex(resume.getUuid())>=0){
                    System.out.println("Storage already contains resume " + resume + ".");
                    break;
                } else if (resume.compareTo(storage[0]) < 0) {
                    System.arraycopy(storage, 0, storage, 1, size + 1);
                    storage[i] = resume;
                    size++;
                    break;
                } else if (((storage[i].compareTo(resume) < 0) && (storage[i + 1] == null))
                        || ((storage[i].compareTo(resume) < 0) && (storage[i + 1].compareTo(resume) > 0))) {
                    System.arraycopy(storage, i + 1, storage, i + 2, size - i - 1);
                    storage[i + 1] = resume;
                    size++;
                    break;
                }
            }
        }
    }
}
