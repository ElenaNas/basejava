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

public abstract class AbstractPathStorage extends AbstractStorage<Path> {

    private final Path directory;

    protected AbstractPathStorage(String directory) {
        this.directory = Paths.get(directory);
        Objects.requireNonNull(this.directory, "Directory can not be null");
        if (!Files.isDirectory(this.directory) || !Files.isWritable(this.directory)) {
            throw new IllegalArgumentException(directory + " is not directory or is not writable");
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
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
            return Files.list(directory).map(this::doGet).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Resumes can not be copied to the indicated directory: " + directory.getFileName(), null);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (Exception e) {
            throw new StorageException("This file path can not be deleted", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Storage size can not be defined", null);
        }
    }

    protected abstract void doWrite(Resume resume, OutputStream outputStream) throws IOException;

    protected abstract Resume doRead(InputStream inputStream) throws IOException;
}
