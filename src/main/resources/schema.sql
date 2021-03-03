CREATE TABLE IF NOT EXISTS manufacturer (
    name VARCHAR(128),
    nationality VARCHAR(128),
    PRIMARY KEY (name)
);
CREATE TABLE IF NOT EXISTS beer (
    name VARCHAR(128),
    graduation FLOAT,
    type VARCHAR(128),
    description VARCHAR(1024),
    manufacturer VARCHAR(128) NULL,
    PRIMARY KEY (name),
    FOREIGN KEY (manufacturer) REFERENCES manufacturer(name)
);