package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> implements IStreamStrategy {

    protected Path pathDirectory;

    protected PathStorage(String pathDirectory) {
        this.pathDirectory = Paths.get(Objects.requireNonNull(pathDirectory, "Directory can not be null"));
        if (!Files.isDirectory(this.pathDirectory) || !Files.isWritable(this.pathDirectory)) {
            throw new IllegalArgumentException(pathDirectory + " is not directory or is not writable");
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return pathDirectory.resolve(uuid);
    }

    @Override
    protected boolean isExisting(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Path with this name can not be created in indicated directory  " + path.toFile().getAbsolutePath(), path.toFile().getName(), e);
        }
        doUpdate(resume, path);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Resume can not be found in following directory: ", path.toFile().getName());
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (Exception e) {
            throw new StorageException("Following path can not be deleted: ", path.toFile().getName());
        }
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Following path can not be created " + path.toFile().getAbsolutePath(), path.toFile().getName(), e);
        }
    }

    @Override
    protected List<Resume> doCopy() {
        try {
            return Files.list(pathDirectory).map(this::doGet).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Resumes can not be copied to the indicated directory: " + pathDirectory.getFileName(), null);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(pathDirectory).forEach(this::doDelete);
        } catch (Exception e) {
            throw new StorageException("This file path can not be deleted", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(pathDirectory).count();
        } catch (IOException e) {
            throw new StorageException("Storage size can not be defined", null);
        }
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
