ALTER TABLE compra ADD COLUMN situacao VARCHAR(20) NOT NULL;

ALTER TABLE ingresso ADD COLUMN status VARCHAR(20) NOT NULL;

ALTER TABLE usuario ADD COLUMN produtor BOOLEAN NOT NULL;
