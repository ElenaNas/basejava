package webapp.storage;

import webapp.Config;

class SqlStorageTest extends AbstractStorageTest {
        public SqlStorageTest() {
            super(Config.get().getStorage());
            // super(new SqlStorage("jdbc:postgresql://localhost:5432/webapp", "postgres", "123"));
        }
}