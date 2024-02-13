package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;
import webapp.storage.strategy.ObjectStreamStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private File fileDirectory;

    private ObjectStreamStrategy objectStrategyStorage;

    public void setObjectStrategyStorage(ObjectStreamStrategy objectStrategyStorage) {
        this.objectStrategyStorage = objectStrategyStorage;
    }

    public FileStorage(File fileDirectory, ObjectStreamStrategy objectStrategyStorage) {
        Objects.requireNonNull(fileDirectory, "Directory can not be null");
        this.objectStrategyStorage = objectStrategyStorage;
        if (!fileDirectory.isDirectory()) {
            throw new IllegalArgumentException(fileDirectory.getAbsolutePath() + " is not a directory");
        }
        if (!fileDirectory.canRead() || !fileDirectory.canWrite()) {
            throw new IllegalArgumentException(fileDirectory.getAbsolutePath() + " is not readable/writable");
        }
        this.fileDirectory = fileDirectory;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(fileDirectory, uuid);
    }

    @Override
    protected boolean isExisting(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("File with this name can not be created in indicated directory  " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(resume, file);
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return objectStrategyStorage.doRead(new BufferedInputStream(new FileInputStream(file)));
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
    protected void doUpdate(Resume resume, File file) {
        try {
            objectStrategyStorage.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File can not be created " + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    protected List<Resume> doCopy() {
        List<Resume> resumeList = new ArrayList<>();
        for (File file : listFiles()) {
            resumeList.add(doGet(file));
        }
        return resumeList;
    }

    @Override
    public void clear() {
        for (File file : listFiles()) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        return listFiles().length;
    }

    public File[] listFiles() {
        File[] listFiles = fileDirectory.listFiles();
        if (listFiles == null) {
            throw new StorageException("Error while getting list of files from " + fileDirectory.getName(), null, null);
        } else {
            return listFiles;
        }
    }
}
