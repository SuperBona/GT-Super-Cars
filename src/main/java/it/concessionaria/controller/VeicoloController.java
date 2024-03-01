package it.concessionaria.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.concessionaria.model.Utente;
import it.concessionaria.model.Veicolo;
import it.concessionaria.service.UtenteService;
import it.concessionaria.service.VeicoloService;

@Controller
@RequestMapping("/veicolo")
public class VeicoloController {
	
	private VeicoloService veicoloService;
	private UtenteService utenteService;
	public VeicoloController(VeicoloService veicoloService,UtenteService utenteService) {
		this.veicoloService = veicoloService;
		this.utenteService=utenteService;
	}
	
	// per vedere tutti gli utenti
	@ModelAttribute("utenti")
	   public List<Utente> getUtenti() {
	       return utenteService.getUtenti();
	   }
		
	// per i veicoli
	   @ModelAttribute("veicoli")
	   public List<Veicolo> getVeicoli() {
	       return veicoloService.getVeicoli();
	  
		}

	   // LISTA DI TUTTI I VEICOLI DEL DB
	@GetMapping("/lista")
	public String getVeicoli(Model model){
		model.addAttribute("veicoli", this.veicoloService.getVeicoli());
		return "viewVeicolo/listaveicoli";
	}
	
	@GetMapping("{id:[0-9]+}")
	public String getVeicoli(@PathVariable("id") int id, Model model) {
		model.addAttribute("veicoli", this.veicoloService.getVeicoloById(id));
		return "viewVeicolo/listaveicoli";
	}
	
	// VEICOLI NON NOLEGGIATI E NON ACQUISTATI //usato in noleggio e eliminaveicolo
	 @ModelAttribute("veicoliNonNoleggiati")
	   public List<Veicolo> getNoleggiati() {
		    List<Veicolo> veicoli = veicoloService.getVeicoli(); 
		    List<Veicolo> veicoliNonNoleggiati = new ArrayList<>();
		    for (Veicolo veicolo : veicoli) {
		    	if(veicolo.getDataInizio() == null && veicolo.getDataVendita() == null) {
		    		veicoliNonNoleggiati.add(veicolo);
		    	}
		    	
		    }
		   
		    return veicoliNonNoleggiati;
	  
		}
	
	// REGISTRAZIONE NUOVO VEICOLO 
	@GetMapping("/registrazione")
	public String handleGetRequest() {
		return "viewVeicolo/registrazioneveicolo";
	}
	
	@PostMapping("/registrazione")
	public String aggiungiVeicolo(Veicolo veicolo) {
		try {
			this.veicoloService.aggiungiVeicolo(veicolo);
			return "viewNotifiche/registrazioneveicolosuccesso";
		} catch (Exception e) {
			e.printStackTrace();
			return "viewNotifiche/erroregenerico";
		}
	}
	
	// RIMUOVIVEICOLO
	 @GetMapping("/rimuoviveicolo")
		public String getRimuovi() {
			return "viewVeicolo/eliminaveicolo";
		}
		
		@PostMapping("/rimuoviveicolo")
		public String rimuovi(Veicolo veicolo) {
			try {
			this.veicoloService.eliminaVeicolo(veicolo.getId());
			return "viewNotifiche/notificagenerica";
		}catch (Exception e) {
			e.printStackTrace();
			return "viewNotifiche/erroregenerico";
		}}
		
	
	// VENDITA VEICOLO
	@GetMapping("/vendita")
	public String getVendita() {
		return "viewVeicolo/vendita";
	}
	
	@PostMapping("/vendita")
	public String Vendita(Utente utente, Veicolo veicolo, LocalDate dataVendita) {
		try {
			veicoloService.venditaVeicolo(utente,veicolo.getId(), dataVendita);
			return "viewNotifiche/venditasuccesso";
					}catch (Exception e) {
						e.printStackTrace();
						return "viewNotifiche/erroregenerico";
					}
	}
	

	 
	// NOLEGGIO VEICOLO
	@GetMapping("/noleggio")
	public String noleggiaVeicolo() {
		return "viewVeicolo/noleggio";
	}
	
	
	@PostMapping("/noleggio")
	public String noleggio (Utente utente, Veicolo veicolo, LocalDate dataInizio, LocalDate dataFine) {
		try {
		veicoloService.noleggioVeicolo(utente, veicolo, dataInizio, dataFine);
			return "viewNotifiche/notificagenerica";
	}catch (Exception e) {
		e.printStackTrace();
		return "viewNotifiche/erroregenerico";
	} }
	
	// IDEA PREVIEW PER VEDERE PRIMA DEL NOLEGGIO UN RESOCONTO CON IL PREZZO (PREZZO AL GIORNO) ? AGGIORNATO PER TOT GIORNI
	
	// RIENTRO VEICOLO DAL NOLEGGIO
	@GetMapping("/rientroveicolo")
	public String consegnaVeicolo() {
		return "viewVeicolo/consegna";
	}
	
	@PostMapping("/rientroveicolo")
	public String veicoloConsegnato(Veicolo veicolo, double chilometraggio) {
		try {
		this.veicoloService.consegnaVeicolo(veicolo, chilometraggio);
		return "viewNotifiche/notificagenerica";
	}catch (Exception e) {
		e.printStackTrace();
		return "viewNotifiche/erroregenerico";
	} }
	
	// ESTENDI NOLEGGIO 
	@GetMapping("/estendinoleggio")
	public String getModificaNoleggio() {	
		return "viewVeicolo/estendinoleggio";
	}
	
	@PostMapping("/estendinoleggio")
	public String Modifica(Veicolo veicolo, LocalDate nuovaDataFine) {
	try {	veicoloService.estendiNoleggio(veicolo.getId(), nuovaDataFine);
		return "viewNotifiche/notificagenerica";
	} catch (Exception e) {
	      return "viewNotifiche/erroregenerico"; 
	    }}
	

	
	// VENDUTI LISTA
	@GetMapping("/veicolivenduti")
	public String getVeicoliVenduti(Model model) {
	    model.addAttribute("veicoli", this.veicoloService.getVeicoliVenduti());
	    return "viewVeicolo/veicolivenduti";
	}

	// NOLEGGIATI LISTA
	@GetMapping("/veicolinoleggiati")
	public String getVeicoliNoleggiati(Model model) {
	 try {   model.addAttribute("veicoli", this.veicoloService.getVeicoliNoleggiati());
	    return "viewVeicolo/veicolinoleggiati";
	} catch (Exception e) {

	      model.addAttribute("errorMessage", e.getMessage());
	      return "viewNotifiche/erroregenerico"; 
	    }}

	
	// VENDITA FILTRO 
		
			@GetMapping("/filtrovendita")
			public String filtroVendita () {
				return "viewVeicolo/filtrovendita";
			}
			@PostMapping("/filtrovendita")
			public String filtroVendita(@ModelAttribute("inizio") LocalDate inizio, @ModelAttribute("fine") LocalDate fine, Model model) {
			try {    model.addAttribute("venditePerData",this.veicoloService.ricercaVenditafiltro(inizio,fine));
			    return "viewVeicolo/ricercafiltrovendita";
			} catch (Exception e) {

			      model.addAttribute("errorMessage", e.getMessage());
			      return "viewNotifiche/erroregenerico"; 
			    }
			}
			
			// FILTRO NOLEGGIATI
			@GetMapping ("/filtronoleggio")
			public String filtroNoleggio () {
				return "viewVeicolo/filtronoleggio";
			}
			
			@PostMapping("/filtronoleggio")
			public String filtroNoleggio(@ModelAttribute("inizio") LocalDate inizio, @ModelAttribute("fine") LocalDate fine, Model model) {
			try {    model.addAttribute("noleggioPerData",this.veicoloService.ricercaNoleggiofiltro(inizio,fine));
			    // qui aggiungi le date al modello
			    model.addAttribute("inizio", inizio);
			    model.addAttribute("fine", fine);
			    return "viewVeicolo/ricercafiltronoleggio";
			} catch (Exception e) {

			      model.addAttribute("errorMessage", e.getMessage());
			      return "viewNotifiche/erroregenerico"; 
			    }
			}
			
	// FILTRA PER TIPO
			@GetMapping("filtrotipo")
			public String filtroTipo() {
				return "viewVeicolo/filtroTipo";
			}
			
			@PostMapping("filtrotipo")
			 public String filterVeicoli(@ModelAttribute("veicolo") String tipo, Model model) {
				 try {
					    // Recupera i veicoli per il tipo selezionato
					    Optional<List<Veicolo>> veicoliOptional = veicoloService.getVeicoliByTipo(tipo);
					    
					    // Controlla se sono stati trovati veicoli
					    if (veicoliOptional.isPresent()) {
					      List<Veicolo> veicoli = veicoliOptional.get();
					      model.addAttribute("veicoli", veicoli);
					    } 

			      return "viewVeicolo/resultfiltrotipo"; 
			    } catch (Exception e) {

			      model.addAttribute("errorMessage", e.getMessage());
			      return "viewNotifiche/erroregenerico"; 
			    }
			  }
			
			// MODIFICA VEICOLO
	/*		@GetMapping("/modificaveicolo")
		    public String mostraFormModifica(Model model) {
		        
		        return "viewVeicolo/modificaveicolo";
		    }

		    @PostMapping("/modificaveicolo")
		    public String modificaDatiUtente(@RequestParam("utente") String cF,
		                                     @RequestParam(name = "nome", required = false) String nuovoNome,
		                                     @RequestParam(name = "cognome", required = false) String nuovoCognome,
		                                     @RequestParam(name = "dataDiNascita", required = false) LocalDate nuovaDataDiNascita,
		                                     Model model) {
		        try {
		            // Modifica i dati dell'utente nel servizio
		            Utente utenteModificato = utenteService.modificaDatiUtente(cF, nuovoNome, nuovoCognome, nuovaDataDiNascita);
		            model.addAttribute("utente", utenteModificato);
		            return "viewNotifiche/notificagenerica";
		        } catch (Exception e) {
		            e.printStackTrace();
		            return "viewNotifiche/erroregenerico";
		        }
		    }
		}*/
}

	