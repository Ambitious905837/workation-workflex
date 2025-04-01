CREATE TABLE workations (
    workation_id VARCHAR(255) NOT NULL,
    employee VARCHAR(255),
    origin VARCHAR(255),
    destination VARCHAR(255), 
    "start" DATE,
    "end" DATE,
    working_days INTEGER,
    risk VARCHAR(255) CHECK (risk IN ('HIGH','LOW','NO')),
    PRIMARY KEY (workation_id)
); 