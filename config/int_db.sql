
CREATE TABLE resumes
(
    uuid      VARCHAR(36)NOT NULL
      PRIMARY KEY ,
    full_name TEXT NOT NULL
);

CREATE TABLE contact
(
        id            SERIAL
            CONSTRAINT id_contact
            PRIMARY KEY ,
    contact_type  TEXT NOT NULL ,
    contact_value TEXT NOT NULL ,
    resume_uuid   CHAR(36) NOT NULL
       REFERENCES resume
           ON DELETE CASCADE
);
CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, contact_type);

CREATE TABLE section
(
    id            SERIAL
        CONSTRAINT id_section
        PRIMARY KEY ,
    section_type  TEXT NOT NULL ,
    section_value TEXT NOT NULL ,
    resume_uuid  CHAR(36) NOT NULL
        REFERENCES resume
            ON DELETE CASCADE
);

CREATE UNIQUE INDEX section_resume_uuid_section_type_index
    ON section (resume_uuid, section_type);