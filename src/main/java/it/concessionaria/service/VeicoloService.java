package it.concessionaria.service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.concessionaria.model.Utente;
import it.concessionaria.model.Veicolo;
import it.concessionaria.repository.UtenteRepository;
import it.concessionaria.repository.VeicoloRepository;

@Service
public class VeicoloService {
	
	private VeicoloRepository veicoloRepository;
	private UtenteRepository utenteRepository;
	private UtenteService utenteService;
	
	public VeicoloService(VeicoloRepository veicoloRepository, UtenteRepository utenteRepository, UtenteService utenteService) {
		
		this.veicoloRepository = veicoloRepository;
		this.utenteRepository = utenteRepository;
		this.utenteService = utenteService;
	}
	
	// metodo che ritorna tutti i veicoli, lista
	public List<Veicolo> getVeicoli(){
		return this.veicoloRepository.findAll();
		
	}
	
	// metodo che prende il veicolo per id 
  	public Veicolo getVeicoloById(int id) throws IllegalStateException{
		
		Optional<Veicolo> veicoloTrovato = this.veicoloRepository.findById(id);
		
		if(!veicoloTrovato.isPresent())
			throw new IllegalStateException("Veicolo non presente nel DB!");
		
		return veicoloTrovato.get();
	}
	
  	// ELIIMINA VEICOLO (potrò rimuoverlo solo se non è ne noleggiato nè acquistato)
	public void eliminaVeicolo(int id) {
		
		this.veicoloRepository.deleteById(id);
	}

	
	// AGGIUNGI UN NUOVO VEICOLO
	public void aggiungiVeicolo(Veicolo veicolo) throws IllegalStateException{
		Optional<Veicolo> veicoloTrovato = this.veicoloRepository.findById(veicolo.getId());
		if(veicoloTrovato.isPresent()) {
			throw new IllegalStateException("Il veicolo è già presente nel DB");
                
}
		this.veicoloRepository.save(veicolo);
	}
	
	// VENDI UN VEICOLO
	 public void venditaVeicolo(Utente utente, int id, LocalDate dataVendita) {
		   	Optional<Utente> utenteTrovato = utenteRepository.findById(utente.getcF());
		   	if(!utenteTrovato.isPresent()) {
		   		utenteService.aggiungiUtente(utente);
		   	}
		   	else {
		   		Optional<Veicolo> veicoloTrovato = veicoloRepository.findById(id);
		       	if(!veicoloTrovato.isPresent())
		       				throw new IllegalStateException("Veicolo non presente nel DB!");
		           Veicolo veicolo = veicoloTrovato.get();
		           veicolo.setDataVendita(dataVendita);
		           veicolo.setDataInizio(null);
		           veicolo.setDataFine(null);
		           veicoloRepository.save(veicolo);
		   		}
		   }
	 
	// NOLEGGIA VEICOLO
	 public void noleggioVeicolo(Utente utente, Veicolo veicolo, LocalDate dataInizio, LocalDate dataFine) {
		 
		 	Optional<Utente> utenteTrovato = utenteRepository.findById(utente.getcF());
		  	if(!utenteTrovato.isPresent()) {
		   		utenteService.aggiungiUtente(utente);
		  	}else {
			if (utenteTrovato.get().getEta() >= 21) {
				Optional<Veicolo> veicoloTrovato = veicoloRepository.findById(veicolo.getId());
		       	if(!veicoloTrovato.isPresent())
		       				throw new IllegalStateException("Veicolo non presente nel DB!");
		        
		           if (veicoloTrovato.get().getDataInizio() != null && veicoloTrovato.get().getDataVendita() != null) {
		        	   throw new IllegalStateException("Il veicolo è già stato noleggiato o venduto");
		           }
		           // Usa veicoloTrovato.get() invece di veicolo
		           veicoloTrovato.get().setDataInizio(dataInizio);
		           veicoloTrovato.get().setDataFine(dataFine);
		           veicoloRepository.save(veicoloTrovato.get());
			}
		}
	   		}
	 
		// CONSEGNA VEICOLO DAL NOLEGGIO
	 public void consegnaVeicolo(Veicolo veicolo, double chilometraggio) throws IllegalStateException {
		   Optional<Veicolo> veicoloTrovato = veicoloRepository.findById(veicolo.getId());
		   if (veicoloTrovato.isPresent()) {
			   Veicolo veicoloEsistente = veicoloTrovato.get();
		        veicoloEsistente.setDataInizio(null);
		        veicoloEsistente.setDataFine(null);
		        veicoloEsistente.setUtente(null); // Imposta la chiave esterna su null
		        veicoloEsistente.setChilometraggio(chilometraggio); // Imposta il nuovo chilometraggio
		        veicoloRepository.save(veicoloEsistente);
		   } else {
		       throw new IllegalStateException("Veicolo non trovato.");
		   }
		  
	}
	 

	 // ESTENDI NOLEGGIO
	 public void estendiNoleggio(int id, LocalDate nuovaDataFine) {
	        Optional<Veicolo> veicoloTrovato= veicoloRepository.findById(id);
	      
	        if(veicoloTrovato.isPresent()) {
	            Veicolo veicolo = veicoloTrovato.get();
	            veicolo.setDataFine(nuovaDataFine);
	            veicoloRepository.save(veicolo);
	        } else {
	            throw new IllegalStateException("Veicolo non presente nel DB!");
	        }
	    }
		

	 
	 // modifica veicolo - in operazioni (home) e lista
	 
	 // veicoli venduti e veicoli noleggiati liste
	 
	 // FILTRO VENDITA 
	 public List<Veicolo> ricercaVenditafiltro(LocalDate inizio, LocalDate fine) {
		 Optional<List<Veicolo>> veicoloTrovato= veicoloRepository.ricercaVenditafiltro(inizio,fine);
		 if(!veicoloTrovato.isPresent())
	        	throw new IllegalStateException("Veicoli non presente nel DB!");
	
		
	        return veicoloTrovato.get();
	
	 }

	 // VEICOLI VENDUTI
	 public List<Veicolo> getVeicoliVenduti() {
		    List<Veicolo> veicoli = this.veicoloRepository.findAll();
		    List<Veicolo> veicoliVenduti = new ArrayList<>();
		    for (Veicolo veicolo : veicoli) {
		        if (veicolo.getDataVendita() != null) {

		            veicoliVenduti.add(veicolo);
		        }
		    }
		    return veicoliVenduti;
		}

		// VEICOLI NOLEGGIATI
	 public List<Veicolo> getVeicoliNoleggiati() {
		    List<Veicolo> veicoli = this.veicoloRepository.findAll();
		    List<Veicolo> veicoliNoleggiati = new ArrayList<>();
		    for (Veicolo veicolo : veicoli) {
		        if (veicolo.getDataInizio() != null && veicolo.getDataVendita()==null) {

		        	veicoliNoleggiati.add(veicolo);
		        }
		    }
		    return veicoliNoleggiati;
		}
	 
	 // FILTRO NOLEGGIATI
	 public List<Veicolo>  ricercaNoleggiati (String tipo){
			
			Optional<List<Veicolo>> veicoloTrovato= veicoloRepository.ricercaNoleggiati(tipo);
	      
	       if(!veicoloTrovato.isPresent())
	       	throw new IllegalStateException("Veicoli non presente nel DB!");
	      
	      
	       return veicoloTrovato.get();
	      
	       }
	 
	 public List<Veicolo> ricercaNoleggiofiltro(LocalDate inizio, LocalDate fine) {
		 Optional<List<Veicolo>> veicoloTrovato= veicoloRepository.ricercaNoleggiofiltro(inizio,fine);
		 if(!veicoloTrovato.isPresent())
	        	throw new IllegalStateException("Veicoli non presente nel DB!");
	
		
	        return veicoloTrovato.get();
	
	 }
	 
	 // FILTRA PER TIPO
	 
	 public Optional<List<Veicolo>> getVeicoliByTipo(String tipo) {
		  // Controlla se il tipo è valido
		  if (!tipo.equals("car") && !tipo.equals("motorcycle")) {
		    throw new IllegalArgumentException("Tipo di veicolo non valido: " + tipo);
		  }

		  // Recupera i veicoli dal repository con il tipo specificato
		  return this.veicoloRepository.findByTipo(tipo);
		}


	 
		// VEDERE UTENTE E LISTA SUOI VEICOLI ?
	/*	public List<Veicolo> getVeicoliByCF(String cF) {
		    // qui puoi usare il repository per ottenere tutti i veicoli dal database
		    List<Veicolo> veicoli = this.veicoloRepository.findAll();
		    // qui puoi creare una lista vuota per i veicoli filtrati
		    List<Veicolo> veicoliFiltrati = new ArrayList<>();
		    // qui puoi iterare su tutti i veicoli e controllare se appartengono all'utente e sono noleggiati
		    for (Veicolo veicolo : veicoli) {
		        if (veicolo.getCF().equals(cF) && veicolo.getDataInizio() != null && veicolo.getDataFine() != null) {
		            // qui puoi aggiungere il veicolo alla lista filtrata
		            veicoliFiltrati.add(veicolo);
		        }
		    }
		    // qui puoi restituire la lista filtrata
		    return veicoliFiltrati;
		}*/
	 
}