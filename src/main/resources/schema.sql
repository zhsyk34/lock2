DROP TABLE IF EXISTS gateway;
DROP TABLE IF EXISTS locks;
DROP TABLE IF EXISTS word;

CREATE TABLE IF NOT EXISTS gateway (
  id         BIGINT UNSIGNED NOT NULL                AUTO_INCREMENT,
  sn         VARCHAR(30)     NOT NULL,
  udid       VARCHAR(60)     NOT NULL,
  name       VARCHAR(30)     NOT NULL,
  ip         VARCHAR(60)     NOT NULL                DEFAULT '127.0.0.1',
  port       INT UNSIGNED    NOT NULL                DEFAULT 50000,
  remote     VARCHAR(60)     NOT NULL                DEFAULT '114.55.219.171',
  version    VARCHAR(12)     NOT NULL,
  createTime TIMESTAMP       NOT NULL,
  updateTime TIMESTAMP       NOT NULL,
  PRIMARY KEY (id)
)
  COMMENT = '网关'
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

CREATE TABLE IF NOT EXISTS locks (
  id         BIGINT UNSIGNED   NOT NULL                AUTO_INCREMENT,
  gatewayId  BIGINT UNSIGNED   NOT NULL,
  number     SMALLINT UNSIGNED NOT NULL,
  uuid       VARCHAR(36)       NOT NULL,
  name       VARCHAR(30)       NOT NULL,
  permission BIT               NOT NULL                DEFAULT 0,
  createTime TIMESTAMP         NOT NULL,
  updateTime TIMESTAMP         NOT NULL,
  PRIMARY KEY (id)
)
  COMMENT = '门锁'
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

CREATE TABLE IF NOT EXISTS word (
  id         BIGINT UNSIGNED NOT NULL          AUTO_INCREMENT,
  lockId     BIGINT UNSIGNED NOT NULL,
  number     BIGINT UNSIGNED NOT NULL,
  value      VARCHAR(10)     NOT NULL,
  createTime TIMESTAMP       NOT NULL,
  updateTime TIMESTAMP       NOT NULL,
  PRIMARY KEY (id)
)
  COMMENT = '密码表'
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;


/*foreign key*/
ALTER TABLE locks
  ADD CONSTRAINT fk_locks_gateway FOREIGN KEY (gatewayId) REFERENCES gateway (id);
ALTER TABLE word
  ADD CONSTRAINT fk_word_locks FOREIGN KEY (lockId) REFERENCES locks (id);

/*unique key*/
ALTER TABLE gateway
  ADD CONSTRAINT un_gw_sn UNIQUE (sn),
  ADD CONSTRAINT un_gw_udid UNIQUE (udid);
ALTER TABLE locks
  ADD CONSTRAINT un_gw_no UNIQUE (gatewayId, number, permission);
ALTER TABLE word
  ADD CONSTRAINT un_no_value UNIQUE (lockId, value);


