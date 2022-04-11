-- Test podaci
insert into artikl (id, naziv, proizvodjac)
	values (-100,'test', 'test');
insert into dobavljac
	values (-100, 'test', 'test', '+38165426366');
insert into porudzbina (id, datum, isporuceno, dobavljac, iznos, placeno)
	values (-100,to_date('01.03.2022.', 'dd.mm.yyyy.'),to_date('05.03.2022.', 'dd.mm.yyyy.'), -100,0,TRUE);
insert into stavka_porudzbine (id, porudzbina, redni_broj, artikl, kolicina, jedinica_mere, cena)      
	values (-100, -100, 1, -100,20, 'komad', 100);
  
-- Artikl podaci
insert into artikl (id, naziv, proizvodjac)
values (nextval('artikl_seq'),'Moja kravica sveze mleka, 1L', 'AD Imlek'),
		(nextval('artikl_seq'), 'Moja Kravica svezi sir, 1kg', 'AD Imlek'),
        (nextval('artikl_seq'), 'Jogurt, 1L', 'AD Imlek'),
        (nextval('artikl_seq'), 'Persil Regular prasak, 2kg', 'Henkel Srbija'),
        (nextval('artikl_seq'), 'Persil Duo Caps pak', 'Henkel Srbija'),
        (nextval('artikl_seq'), 'Persil Regular gel, 1l', 'Henkel Srbija'),
        (nextval('artikl_seq'), 'Jagoda', 'Fruit DOO'),
        (nextval('artikl_seq'), 'Jabuka', 'Fruit DOO'),
        (nextval('artikl_seq'), 'Breskva', 'Fruit DOO'),
        (nextval('artikl_seq'), 'Slag pena', 'CENTROPROIZVOD'),
        (nextval('artikl_seq'), 'Puding jagoda', 'CENTROPROIZVOD'),
        (nextval('artikl_seq'), 'Puding vanila', 'CENTROPROIZVOD');
        
 -- dobavljac podaci
 insert into dobavljac
 values (nextval('dobavljac_seq'), 'AD Imlek', 'Industrijska zona bb, Beograd', '+38165426366'),
 	(nextval('dobavljac_seq'), 'Henkel Srbija', 'Industrijska zona 55, Novi Sad', '+3814569523'),
    (nextval('dobavljac_seq'), 'Fruit DOO', 'Futoska bb', '+3814455566'),
    (nextval('dobavljac_seq'), 'CENTROPROIZVOD' , 'Dobanovacki put, Surcin', '+38136955466');
 
 -- porudzbina podaci
 insert into porudzbina (id, datum, isporuceno, dobavljac, iznos, placeno)
 values (nextval('porudzbina_seq'),to_date('01.03.2022.', 'dd.mm.yyyy.'),
        to_date('05.03.2022.', 'dd.mm.yyyy.'), 1,0,TRUE),
        (nextval('porudzbina_seq'),to_date('08.02.2022.', 'dd.mm.yyyy.'),
        to_date('01.03.2022.', 'dd.mm.yyyy.'), 2,1000,FALSE),
        (nextval('porudzbina_seq'),to_date('01.01.2022.', 'dd.mm.yyyy.'),
        to_date('05.05.2022.', 'dd.mm.yyyy.'), 3, 2500,TRUE),
        (nextval('porudzbina_seq'),to_date('01.03.2022.', 'dd.mm.yyyy.'),
        to_date('05.03.2022.', 'dd.mm.yyyy.'), 4,3000,FALSE),
        (nextval('porudzbina_seq'),to_date('07.03.2022.', 'dd.mm.yyyy.'),
        to_date('09.03.2022.', 'dd.mm.yyyy.'), 4,4200,TRUE);
        
-- Stavka Porudzbine podaci        
  insert into stavka_porudzbine (id, porudzbina, redni_broj, artikl, kolicina, jedinica_mere, cena)      
  values (nextval('stavka_porudzbine_seq'), 1, 1, 1,20, 'komad', 100),
  		(nextval('stavka_porudzbine_seq'), 1, 2, 2,10, 'komad', 300),
        (nextval('stavka_porudzbine_seq'), 1, 3, 3,15, 'komad', 120),
        (nextval('stavka_porudzbine_seq'), 2, 1, 4,100, 'komad', 400),
        (nextval('stavka_porudzbine_seq'), 2, 2, 5,60, 'komad', 350),
        (nextval('stavka_porudzbine_seq'), 2, 3, 6,30, 'komad', 300),
        (nextval('stavka_porudzbine_seq'), 3, 1, 7,20, 'kg', 100),
        (nextval('stavka_porudzbine_seq'), 3, 2, 8,30, 'kg', 200),
        (nextval('stavka_porudzbine_seq'), 3, 3, 9,50, 'kg', 100),
        (nextval('stavka_porudzbine_seq'), 4, 1, 10,20, 'komad', 30),
        (nextval('stavka_porudzbine_seq'), 4, 2, 11,20, 'komad', 100),
        (nextval('stavka_porudzbine_seq'), 4, 3, 12,30, 'komad', 300),
        (nextval('stavka_porudzbine_seq'), 5, 1, 10,20, 'komad', 100),
        (nextval('stavka_porudzbine_seq'), 5, 2, 11,20, 'komad', 100);