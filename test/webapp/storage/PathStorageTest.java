package webapp.storage;


class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage("C:\\Users\\nas-e\\basejava\\src\\webapp\\storageSer", new ObjectStrategyStorage()));
    }
}