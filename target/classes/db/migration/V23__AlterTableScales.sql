ALTER TABLE SCALES ADD COLUMN connection_type  VARCHAR(10) default 'rs232';
ALTER TABLE SCALES ADD COLUMN ip VARCHAR(20) default '';
ALTER TABLE SCALES ADD COLUMN eth_port INTEGER default 0;
ALTER TABLE SCALES ADD COLUMN eth_cmd  VARCHAR(50) default '';
