DROP TABLE IF EXISTS artikl CASCADE;
DROP TABLE IF EXISTS dobavljac CASCADE;
DROP TABLE IF EXISTS porudzbina CASCADE;
DROP TABLE IF EXISTS stavka_porudzbine CASCADE;

DROP SEQUENCE IF EXISTS artikl_seq;
DROP SEQUENCE IF EXISTS dobavljac_seq;
DROP SEQUENCE IF EXISTS porudzbina_seq;
DROP SEQUENCE IF EXISTS stavka_porudzbine_seq;

CREATE TABLE dobavljac (
    id integer not null,
    naziv varchar(50),
    adresa varchar(200),
    kontakt varchar(100)                  
    );
    
CREATE TABLE artikl (
	id integer not null,
    naziv varchar(50),
    proizvodjac varchar(50)
);   

CREATE TABLE porudzbina (
    id integer not null,
    datum date,
    isporuceno date,
    iznos numeric,
    placeno boolean,
    dobavljac integer not null
);

CREATE TABLE stavka_porudzbine (
    id integer not null,
    redni_broj integer not null,
    kolicina numeric,
    jedinica_mere varchar(20),
    cena numeric,
    porudzbina integer not null,
    artikl integer not null
    );
    
ALTER TABLE artikl
	ADD CONSTRAINT pk_artikl PRIMARY KEY (id);
ALTER TABLE dobavljac 
	ADD CONSTRAINT pk_dobavljac PRIMARY KEY (id);
ALTER TABLE porudzbina 
	ADD CONSTRAINT pk_porudzbina PRIMARY KEY (id);
ALTER TABLE stavka_porudzbine 
	ADD CONSTRAINT pk_stavka_porudzbine PRIMARY KEY (id);
   
ALTER TABLE porudzbina 
	ADD CONSTRAINT fk_porudzbina_dobavljac FOREIGN KEY (dobavljac)
    REFERENCES dobavljac(id) ON DELETE CASCADE;
   
ALTER TABLE stavka_porudzbine
	ADD CONSTRAINT fk_stavka_porudzbine_porudzbina FOREIGN KEY (porudzbina)
    REFERENCES porudzbina(id) ON DELETE CASCADE;
    
ALTER TABLE stavka_porudzbine
	ADD CONSTRAINT fk_stavka_porudzbine_artikl FOREIGN KEY (artikl)
    REFERENCES artikl(id) ON DELETE CASCADE;    
    
CREATE INDEX idx_pk_artikl ON artikl(id);  
CREATE INDEX idx_pk_dobavljac ON dobavljac(id);
CREATE INDEX idx_pk_porudzbina ON porudzbina(id);
CREATE INDEX idx_pk_stavka_porudzbine ON stavka_porudzbine(id);

CREATE INDEX idx_fk_porudzbina_dobavljac ON porudzbina(dobavljac);
CREATE INDEX idx_fk_stavka_porudzbine_porudzbina 
	ON stavka_porudzbine(porudzbina);
CREATE INDEX idx_fk_stavka_porudzbine_artikl
	ON stavka_porudzbine(artikl);    
    
CREATE SEQUENCE IF NOT EXISTS artikl_seq INCREMENT 1 START 1;  
CREATE SEQUENCE IF NOT EXISTS dobavljac_seq INCREMENT 1 START 1;   
CREATE SEQUENCE IF NOT EXISTS porudzbina_seq INCREMENT 1 START 1;   
CREATE SEQUENCE IF NOT EXISTS stavka_porudzbine_seq INCREMENT 1 START 1;   

ALTER TABLE artikl ALTER COLUMN id SET DEFAULT nextval('artikl_seq');
ALTER TABLE dobavljac ALTER COLUMN id SET DEFAULT nextval('dobavljac_seq');
ALTER TABLE porudzbina ALTER COLUMN id SET DEFAULT nextval('porudzbina_seq');
ALTER TABLE stavka_porudzbine ALTER COLUMN id SET DEFAULT nextval('stavka_porudzbine_seq');