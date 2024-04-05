
create table resume
(
    uuid      varchar(36) not null
        primary key,
    full_name text        not null
);

create table contact
(
    id            serial,
    contact_type  text     not null,
    contact_value text     not null,
    resume_uuid   char(36) not null
        references resume
            on delete cascade
);

create table section
(
    id            serial,
    section_type  text,
    section_value text,
    resume_uuid   char(36) not null
);
CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, contact_type);