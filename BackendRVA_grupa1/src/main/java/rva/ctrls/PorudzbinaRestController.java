package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.repositories.PorudzbinaRepository;
import rva.jpa.Porudzbina;

@CrossOrigin
@RestController
@Api(tags = {"Porudzbina CRUD operacije"})
public class PorudzbinaRestController {
	
	@Autowired
	private PorudzbinaRepository porudzbinaRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("porudzbina")
	@ApiOperation(value = "Vraca kolekciju svih porudzbina iz baze podataka")
	public Collection<Porudzbina> getPorudzbine() {
		return porudzbinaRepository.findAll();
	}
	@GetMapping("porudzbina/{id}")
	@ApiOperation(value = "Vraca porudzbinu u odnosu na posledjenu vrednost path varijable id")
	public Porudzbina getPorudzbina(@PathVariable("id") Integer id) {
		return porudzbinaRepository.getOne(id);
	}
	@GetMapping("porudzbinePlacene")
	@ApiOperation(value = "Vraca kolekciju porudzbina kod kojih je vrednost obećežja placeno jednaka sa true")
	public Collection<Porudzbina> getPorudzbinaByPlaceno() {
		return porudzbinaRepository.findByPlacenoTrue();
	}
	@PostMapping("porudzbina")
	@ApiOperation(value = "Dodaje novu porudžbinu u bazu podataka.")
	public ResponseEntity<Porudzbina> insertPorudzbina(@RequestBody Porudzbina porudzbina) {
		if(!porudzbinaRepository.existsById(porudzbina.getId()))
		{
			porudzbinaRepository.save(porudzbina);
			return new ResponseEntity<Porudzbina>(HttpStatus.OK);
		}
		return new ResponseEntity<Porudzbina>(HttpStatus.CONFLICT);
	}
	@PutMapping("porudzbina")
	@ApiOperation(value = "Update-uje postojeću porudžbinu.")
	public ResponseEntity<Porudzbina> updatePorudzbina(@RequestBody Porudzbina porudzbina) {
		if(!porudzbinaRepository.existsById(porudzbina.getId()))
			return new ResponseEntity<Porudzbina>(HttpStatus.NO_CONTENT);
		porudzbinaRepository.save(porudzbina);
		return new ResponseEntity<Porudzbina>(HttpStatus.OK);
	}
	@DeleteMapping("porudzbina/{id}")
	@ApiOperation(value = "Briše porudžbinu u odnosu na vrednost posleđenu path varijable id.")
	public ResponseEntity<Porudzbina> deletePorudzbina(@PathVariable("id") Integer id) {
		if(!porudzbinaRepository.existsById(id)) {
			return new ResponseEntity<Porudzbina>(HttpStatus.NO_CONTENT);
		}
		
		porudzbinaRepository.deleteById(id);
		if(id == -100) {
			jdbcTemplate.execute("insert into dobavljac values (-100, 'test', 'test', '+38111111')");
			jdbcTemplate.execute(
					"INSERT INTO porudzbina(id, datum, isporuceno, iznos, placeno, dobavljac) "
					+ "VALUES (-100, '2020-03-03', '2020-03-03', 5000, true, -100)"
			);
		}
		return new ResponseEntity<Porudzbina>(HttpStatus.OK);
	}
}