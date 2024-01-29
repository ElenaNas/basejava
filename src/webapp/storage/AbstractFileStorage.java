package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "Directory can not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExisting(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("Error while getting file ", file.getName());
        }
    }

    @Override
    protected void doDelete(File file) {
        try {
            file.delete();
        } catch (Exception e) {
            throw new StorageException("Error while deleting file ", file.getName());
        }
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> doCopy() {
        List<Resume> resumeList = new ArrayList<>(Objects.requireNonNull(directory.list()).length);
        try {
            resumeList.add(doRead(directory));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resumeList;
    }

    @Override
    public void clear() {
        for (File f : Objects.requireNonNull(directory.listFiles())) {
            doDelete(f);
        }
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.listFiles()).length;
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;
}
