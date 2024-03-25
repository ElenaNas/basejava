
CREATE TABLE resume (
                        uuid CHAR PRIMARY KEY NOT NULL,
                        full_name TEXT NOT NULL
);

CREATE TABLE contact (
                         id SERIAL NOT NULL,
                         type TEXT NOT NULL,
                         value TEXT NOT NULL,
                         resume_uuid CHAR NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE
);
CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, type);