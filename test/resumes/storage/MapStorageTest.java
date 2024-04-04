package resumes.storage;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapUUIDStorage());
    }
}