package webapp.storage;

import webapp.Config;

class SqlStorageTest extends AbstractStorageTest {
        public SqlStorageTest() {
            super(Config.get().getiStorage());
            // super(new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "123"));
        }
}