package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> implements IStreamStrategy{

    protected File fileDirectory;

    public FileStorage(File fileDirectory) {
        Objects.requireNonNull(fileDirectory, "Directory can not be null");
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
            return doRead(new BufferedInputStream(new FileInputStream(file)));
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
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File can not be created " + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    protected List<Resume> doCopy() {
        File[] listFiles = Objects.requireNonNull(fileDirectory.listFiles());
        List<Resume> resumeList = new ArrayList<>();
        try {
            for (File file : listFiles) {
                resumeList.add(doGet(file));
            }
        } catch (Exception e) {
            throw new StorageException("Following resumes can not be copied: " + fileDirectory.getAbsolutePath(), fileDirectory.getName());
        }
        return resumeList;
    }

    @Override
    public void clear() {
        File[] listFiles = Objects.requireNonNull(fileDirectory.listFiles());
        for (File file : listFiles) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        String[] listFiles = Objects.requireNonNull(fileDirectory.list());
        return listFiles.length;
    }


    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(resume);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return (Resume) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Resume can not be read.", null, e);
        }
    }
}
