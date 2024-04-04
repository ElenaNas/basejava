package resumes.exception;

public class NotExistStorageException extends StorageException {

    public NotExistStorageException(String uuid) {
        super("Resume " + uuid + " was not found in the storage.", uuid);
    }
}
