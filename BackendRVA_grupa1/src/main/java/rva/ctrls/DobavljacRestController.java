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

import rva.jpa.Artikl;
import rva.jpa.Dobavljac;
import rva.repositories.DobavljacRepository;

@RestController
public class DobavljacRestController {

	@Autowired
	private DobavljacRepository dobavljacRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@GetMapping("dobavljac")
	public Collection<Dobavljac> getDobavljaci() {
		return dobavljacRepository.findAll();
	}
	@GetMapping("dobavljac/{id}")
	public Dobavljac getDobavljac(@PathVariable("id") Integer id) {
		return dobavljacRepository.getOne(id);
	}
	@GetMapping("dobavljacNaziv/{naziv}")
	public Collection<Dobavljac> getDobavljaciByNaziv(@PathVariable("naziv") String naziv) {
		return dobavljacRepository.findByNazivContainingIgnoreCase(naziv);
	}
	@PostMapping("dobavljac")
	public ResponseEntity<Dobavljac> insertDobavljac(@RequestBody Dobavljac dobavljac) {
		if (!dobavljacRepository.existsById(dobavljac.getId())) {
			dobavljacRepository.save(dobavljac);
			return new ResponseEntity<Dobavljac>(HttpStatus.OK);
		}
		return new ResponseEntity<Dobavljac>(HttpStatus.CONFLICT);
	}
	@PutMapping("dobavljac")
	public ResponseEntity<Dobavljac> updateDobavljac(@RequestBody Dobavljac dobavljac) {
		if (dobavljacRepository.existsById(dobavljac.getId())) {
			dobavljacRepository.save(dobavljac);
			return new ResponseEntity<Dobavljac>(HttpStatus.OK);
		}
		return new ResponseEntity<Dobavljac>(HttpStatus.CONFLICT);
	}
	@DeleteMapping("dobavljac/{id}")
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