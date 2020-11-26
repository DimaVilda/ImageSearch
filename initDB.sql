CREATE SCHEMA "img";
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA "img" TO postgres;
CREATE TABLE "img".image (
    id character varying(128) PRIMARY KEY,
    url character varying(512),
    meta_teg character varying(512)
);