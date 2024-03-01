package it.concessionaria.service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import it.concessionaria.model.Utente;
import it.concessionaria.repository.UtenteRepository;

@Service
public class UtenteService {
	
	private UtenteRepository utenteRepository;
	
	public UtenteService(UtenteRepository utenteRepository) {
		this.utenteRepository = utenteRepository;
}
	// lista utenti, il metodo prende tutti gli utenti
	public List<Utente> getUtenti(){
		
		return this.utenteRepository.findAll();
	}		
	
	// trova utente per id
	public Utente getUtenteById(String id) throws IllegalStateException{
	
		Optional<Utente> utenteTrovato= this.utenteRepository.findById(id);
		
			if(!utenteTrovato.isPresent())
			throw new IllegalStateException("Utente non presente nel DB!");
		
			return utenteTrovato.get();
	}
	
	// ELIMINA UTENTE
	public void deleteUtente(String id) {
			
		this.utenteRepository.deleteById(id);
	}	
	
	// AGGIUNGI UTENTE AL DB, REGISTRAZIONE
	public void aggiungiUtente(Utente utente) throws IllegalStateException{
		
		Optional<Utente> utenteTrovato = this.utenteRepository.findById(utente.getcF());
		
		if(utenteTrovato.isPresent())
			throw new IllegalStateException("Utente già presente nel DB!");
		
		this.utenteRepository.save(utente);
	}
	

	// MODIFICA UTENTE
	public Utente modificaDatiUtente(String cF, String nuovoNome, String nuovoCognome, LocalDate nuovaDataDiNascita) {
		Optional<Utente> utenteOptional = utenteRepository.findById(cF);
		 
	    if (utenteOptional.isEmpty()) {
	        throw new IllegalStateException("Utente non trovato con CF: " + cF);
	    }

	    Utente utente = utenteOptional.get();
	    if (nuovoNome != null && !nuovoNome.isEmpty()) {
	        utente.setNome(nuovoNome);
	    }
	    if (nuovoCognome != null && !nuovoCognome.isEmpty()) {
	        utente.setCognome(nuovoCognome);
	    }
	    if (nuovaDataDiNascita != null) {
	        utente.setDataDiNascita(nuovaDataDiNascita);
	    }

	    // Salva le modifiche nel repository
	    return utenteRepository.save(utente);
	} 
		
		/* questo non va perchè non mi considera un campo vuoto
		 * Optional<Utente> utenteOptional = utenteRepository.findById(cF);
		    if (utenteOptional.isEmpty()) {
		        throw new IllegalStateException("Utente non trovato con CF: " + cF);
		    }

		    Utente utente = utenteOptional.get();
		    utente.setNome(nuovoNome);
		    utente.setCognome(nuovoCognome);
		    utente.setDataDiNascita(nuovaDataDiNascita);

		    // Salva le modifiche nel repository
		    return utenteRepository.save(utente);
		} */
		
		
}
	
