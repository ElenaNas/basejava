package webapp.storage;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


@Suite
@SelectClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapStorageTest.class,
        MapResumeStorageTest.class,
        ObjectStreamStorageTest.class,
        ObjectStreamPathStorageTest.class
})
public class AllStorageTest {
}
