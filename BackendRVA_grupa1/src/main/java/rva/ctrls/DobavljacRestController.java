package rva.ctrls;

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
import rva.jpa.Artikl;
import rva.jpa.Dobavljac;
import rva.repositories.DobavljacRepository;

@RestController
@Api(tags = {"Dobavljač CRUD operacije"})
public class DobavljacRestController {

	@Autowired
	private DobavljacRepository dobavljacRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@GetMapping("dobavljac")
	@ApiOperation(value = "Vraca kolekciju svih dobavljaca iz baze podataka")
	public Collection<Dobavljac> getDobavljaci() {
		return dobavljacRepository.findAll();
	}
	@GetMapping("dobavljac/{id}")
	@ApiOperation(value = "Vraca dobavljača u odnosu na posledjenu vrednost path varijable id")
	public Dobavljac getDobavljac(@PathVariable("id") Integer id) {
		return dobavljacRepository.getOne(id);
	}
	@GetMapping("dobavljacNaziv/{naziv}")
	@ApiOperation(value = "Vraca kolekciju dobavljaca koji imaju naziv koji sadrži vrednost prosleđenu u okviru path varijable naziv")
	public Collection<Dobavljac> getDobavljaciByNaziv(@PathVariable("naziv") String naziv) {
		return dobavljacRepository.findByNazivContainingIgnoreCase(naziv);
	}
	@PostMapping("dobavljac")
	@ApiOperation(value = "Dodaje novog dobavljača u bazu podataka.")
	public ResponseEntity<Dobavljac> insertDobavljac(@RequestBody Dobavljac dobavljac) {
		if (!dobavljacRepository.existsById(dobavljac.getId())) {
			dobavljacRepository.save(dobavljac);
			return new ResponseEntity<Dobavljac>(HttpStatus.OK);
		}
		return new ResponseEntity<Dobavljac>(HttpStatus.CONFLICT);
	}
	@PutMapping("dobavljac")
	@ApiOperation(value = "Update-uje postojećeg dobavljača.")
	public ResponseEntity<Dobavljac> updateDobavljac(@RequestBody Dobavljac dobavljac) {
		if (dobavljacRepository.existsById(dobavljac.getId())) {
			dobavljacRepository.save(dobavljac);
			return new ResponseEntity<Dobavljac>(HttpStatus.OK);
		}
		return new ResponseEntity<Dobavljac>(HttpStatus.CONFLICT);
	}
	@DeleteMapping("dobavljac/{id}")
	@ApiOperation(value = "Briše dobavljaca u odnosu na vrednost posleđenu path varijable id.")
	public ResponseEntity<Dobavljac> deleteDobavljac(@PathVariable("id") Integer id) {
		if (dobavljacRepository.existsById(id)) {
			dobavljacRepository.deleteById(id);

			if (id == -100) 
			{
				jdbcTemplate.execute("insert into dobavljac values (-100, 'test', 'test', '+38111111')");
			}

			return new ResponseEntity<Dobavljac>(HttpStatus.OK);
		}
		return new ResponseEntity<Dobavljac>(HttpStatus.NO_CONTENT);
	}
}