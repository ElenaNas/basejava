package webapp.storage;

import java.io.File;

class FileStrategyTest extends AbstractStorageTest {

    public FileStrategyTest() {
        super(new FileStorage(new File("C:\\Users\\nas-e\\basejava\\src\\webapp\\storageSer")));
    }
}