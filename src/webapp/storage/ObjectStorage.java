package webapp.storage;

public class ObjectStorage {

    IStreamStrategy streamStrategy;

    public ObjectStorage(IStreamStrategy streamStrategy) {
        this.streamStrategy = streamStrategy;
    }
}
