package webapp.storage;

import webapp.model.Resume;

import java.util.List;


public interface IStorage {
    void clear();

    void update(Resume r);

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int size();

}
