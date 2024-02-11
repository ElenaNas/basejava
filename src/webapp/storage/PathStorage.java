package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;
import webapp.storage.strategy.ObjectStrategyStorage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {

    protected Path pathDirectory;

    protected ObjectStrategyStorage objectStrategyStorage;

    public void setObjectStrategyStorage(ObjectStrategyStorage objectStrategyStorage) {
        this.objectStrategyStorage = objectStrategyStorage;
    }

    protected PathStorage(String pathDirectory, ObjectStrategyStorage objectStrategyStorage) {
        this.pathDirectory = Paths.get(Objects.requireNonNull(pathDirectory, "Directory can not be null"));
        this.objectStrategyStorage = objectStrategyStorage;
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
            return objectStrategyStorage.doRead(new BufferedInputStream(Files.newInputStream(path)));
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
            objectStrategyStorage.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Following path can not be created " + path.toFile().getAbsolutePath(), path.toFile().getName(), e);
        }
    }

    @Override
    protected List<Resume> doCopy() {
        return getFileList(pathDirectory).map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getFileList(pathDirectory).forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getFileList(pathDirectory).count();
    }

    public Stream<Path> getFileList(Path pathDirectory) {
        try {
            return Files.list(pathDirectory);
        } catch (IOException e) {
            throw new StorageException("Error while fetching resume from " + pathDirectory, null);
        }
    }
}
