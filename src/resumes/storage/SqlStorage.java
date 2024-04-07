package resumes.storage;

import resumes.exception.NotExistStorageException;
import resumes.model.ContactType;
import resumes.model.Resume;
import resumes.model.Section;
import resumes.model.SectionType;
import resumes.sql.SqlHelper;
import resumes.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements IStorage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            updateResume(resume, connection);
            deleteContacts(resume, connection);
            deleteSections(resume, connection);
            saveContacts(resume, connection);
            saveSections(resume, connection);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            saveResume(resume, connection);
            saveContacts(resume, connection);
            saveSections(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "SELECT * FROM resume r " +
                        "WHERE r.uuid =? ",
                preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    ResultSet set = preparedStatement.executeQuery();
                    if (!set.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, set.getString("full_name"));
                    getContacts(resume);
                    getSections(resume);
                    return resume;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>execute("" +
                        "DELETE FROM resume " +
                        "WHERE uuid=?",
                preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    if (preparedStatement.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("" +
                        "SELECT r.*, c.contact_type, c.contact_value, s.section_type, s.section_value " +
                        "FROM resume r " +
                        "LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                        "LEFT JOIN section s ON r.uuid = s.resume_uuid " +
                        "ORDER BY r.full_name, r.uuid",
                preparedStatement -> {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    Map<String, Resume> map = new LinkedHashMap<>();
                    while (resultSet.next()) {
                        String uuid = resultSet.getString("uuid");
                        Resume resume = map.get(uuid);
                        if (resume == null) {
                            resume = new Resume(uuid, resultSet.getString("full_name"));
                            map.put(uuid, resume);
                        }
                        addContactOrSection(resultSet, resume);
                    }
                    return new ArrayList<>(map.values());
                });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) from resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }

    private void addContactOrSection(ResultSet resultSet, Resume resume) throws SQLException {
        String contactType = resultSet.getString("contact_type");
        String contactValue = resultSet.getString("contact_value");
        String sectionType = resultSet.getString("section_type");
        String sectionValue = resultSet.getString("section_value");
        if (contactType != null && contactValue != null) {
            resume.addContact(ContactType.valueOf(contactType), contactValue);
        }
        if (sectionType != null && sectionValue != null) {
            resume.addSection(SectionType.valueOf(sectionType), JsonParser.read(sectionValue, Section.class));
        }
    }

    private void updateResume(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.setString(2, resume.getUuid());
            if (preparedStatement.executeUpdate() != 1) {
                throw new NotExistStorageException(resume.getUuid());
            }
        }
    }

    private void deleteContacts(Resume resume, Connection connection) throws SQLException {
        deleteFromTable(connection, "DELETE  FROM contact WHERE resume_uuid=?", resume.getUuid());
    }

    private void deleteSections(Resume resume, Connection connection) throws SQLException {
        deleteFromTable(connection, "DELETE  FROM section WHERE resume_uuid=?", resume.getUuid());
    }

    private void deleteFromTable(Connection connection, String query, String uuid) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, uuid);
            preparedStatement.execute();
        }
    }

    private void saveResume(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.setString(2, resume.getFullName());
            preparedStatement.execute();
        }
    }

    private void saveContacts(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contact (resume_uuid, contact_type,contact_value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, entry.getKey().name());
                preparedStatement.setString(3, entry.getValue());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void saveSections(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO section (resume_uuid, section_type, section_value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, entry.getKey().name());
                Section section = entry.getValue();
                preparedStatement.setString(3, JsonParser.write(section, Section.class));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void getContacts(Resume resume) {
        sqlHelper.execute("SELECT * FROM contact" +
                        " WHERE resume_uuid =?",
                preparedStatement -> {
                    preparedStatement.setString(1, resume.getUuid());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        addContact(resultSet, resume);
                    }
                    return null;
                }
        );
    }

    private void getSections(Resume resume) {
        sqlHelper.execute("SELECT * FROM section WHERE resume_uuid =?",
                preparedStatement -> {
                    preparedStatement.setString(1, resume.getUuid());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        addSection(resultSet, resume);
                    }
                    return null;
                }
        );
    }

    private void addContact(ResultSet resultSet, Resume resume) throws SQLException {
        String value = resultSet.getString("contact_value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(resultSet.getString("contact_type")), value);
        }
    }

    private void addSection(ResultSet resultSet, Resume resume) throws SQLException {
        String sectionValue = resultSet.getString("section_value");
        if (sectionValue != null) {
            SectionType type = SectionType.valueOf(resultSet.getString("section_type"));
            resume.addSection(type, JsonParser.read(sectionValue, Section.class));
        }
    }
}


