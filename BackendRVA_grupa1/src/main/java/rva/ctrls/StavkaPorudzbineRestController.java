package rva.ctrls;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Porudzbina;
import rva.jpa.StavkaPorudzbine;
import rva.repositories.PorudzbinaRepository;
import rva.repositories.StavkaPorudzbineRepository;

@RestController
@Api(tags = {"Stavka Porudžbine CRUD operacije"})
public class StavkaPorudzbineRestController {

	@Autowired
	private StavkaPorudzbineRepository stavkaPorudzbineRepository;
	
	@Autowired 
	private PorudzbinaRepository porudzbinaRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("stavkaPorudzbine")
	@ApiOperation(value = "Vraca kolekciju svih stavki iz baze podataka")
	public Collection<StavkaPorudzbine> getStavkePorudzbine() {
		return stavkaPorudzbineRepository.findAll();
	}
	@GetMapping("stavkaPorudzbine/{id}")
	@ApiOperation(value = "Vraca stavku u odnosu na posledjenu vrednost path varijable id")
	public StavkaPorudzbine getStavkaPorudzbine(@PathVariable("id") Integer id) {
		return stavkaPorudzbineRepository.getOne(id);
	}
	@GetMapping("stavkeZaPorudzbinaID/{id}")
	@ApiOperation(value = "Vraca kolekciju stavki koje se odnose na porudžbinu sa prosleđenom vrednošću id-ija")
	public Collection<StavkaPorudzbine> getStavkePorudzbinePoPorudzbiniID(@PathVariable("id") Integer id) {
		Porudzbina p = porudzbinaRepository.getOne(id);
		return stavkaPorudzbineRepository.findByPorudzbina(p);
	
	}
	@GetMapping("stavkePorudzbineCena/{cena}")
	@ApiOperation(value = "Vraca kolekciju stavki čija je cena manja od vrednosti prosleđene u okviru path varijable cena")
	public Collection<StavkaPorudzbine> getStavkePorudzbineCena(@PathVariable("cena") BigDecimal cena) {
		return stavkaPorudzbineRepository.findByCenaLessThanOrderById(cena);
	}
	@PostMapping("stavkaPorudzbine")
	@ApiOperation(value = "Dodaje novu stavku porudžbine u bazu podataka.")
	public ResponseEntity<StavkaPorudzbine> insertStavkaPorudzbine(@RequestBody StavkaPorudzbine stavkaPorudzbine){
		if(!stavkaPorudzbineRepository.existsById(stavkaPorudzbine.getId()))
		{	
			stavkaPorudzbine.setRedniBroj(stavkaPorudzbineRepository.nextRbr(stavkaPorudzbine.getPorudzbina().getId()));
			stavkaPorudzbineRepository.save(stavkaPorudzbine);
			return new ResponseEntity<StavkaPorudzbine>(HttpStatus.OK);
		}
		return new ResponseEntity<StavkaPorudzbine>(HttpStatus.CONFLICT);
	}
	@PutMapping("stavkaPorudzbine")
	@ApiOperation(value = "Update-uje postojeću stavku.")
	public ResponseEntity<StavkaPorudzbine> updateStavkaPorudzbine(@RequestBody StavkaPorudzbine stavkaPorudzbine){
		if(!stavkaPorudzbineRepository.existsById(stavkaPorudzbine.getId()))
		{	
			return new ResponseEntity<StavkaPorudzbine>(HttpStatus.NO_CONTENT);
		}
		stavkaPorudzbineRepository.save(stavkaPorudzbine);
		return new ResponseEntity<StavkaPorudzbine>(HttpStatus.OK);
	}
	@DeleteMapping("stavkaPorudzbine/{id}")
	@ApiOperation(value = "Briše stavku u odnosu na vrednost posleđenu path varijable id.")
	public ResponseEntity<StavkaPorudzbine> deleteStavkaPorudzbine(@PathVariable("id") Integer id) {
		if(!stavkaPorudzbineRepository.existsById(id)) {
			return new ResponseEntity<StavkaPorudzbine>(HttpStatus.NO_CONTENT);
		}
		stavkaPorudzbineRepository.deleteById(id);
		if (id == -100)
			jdbcTemplate.execute("insert into artikl (id, naziv, proizvodjac) values (-100,'test','test')");
			jdbcTemplate.execute("insert into dobavljac values (-100, 'test', 'test', '+38111111')");
			jdbcTemplate.execute(
					"INSERT INTO porudzbina(id, datum, isporuceno, iznos, placeno, dobavljac) "
					+ "VALUES (-100, '2020-03-03', '2020-03-03', 5000, true, -100)"
			);
			jdbcTemplate.execute("INSERT INTO stavka_porudzbine (id, redni_broj, kolicina, jedinica_mere, cena, porudzbina,artikl) "
					+ "VALUES (-100, 1, 1, 'kom', 100,-100,-100)");
		
		return new ResponseEntity<StavkaPorudzbine>(HttpStatus.OK);
	}
}