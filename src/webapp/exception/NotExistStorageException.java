package webapp.exception;

public class NotExistStorageException extends StorageException {

    NotExistStorageException(String uuid) {
        super("Resume " + uuid + " doesn't exist.", uuid);
    }
}
