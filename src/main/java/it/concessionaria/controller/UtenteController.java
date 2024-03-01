package it.concessionaria.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.concessionaria.model.Utente;
import it.concessionaria.model.Veicolo;
import it.concessionaria.service.UtenteService;
import it.concessionaria.service.VeicoloService;

@Controller
@RequestMapping("/utente")
public class UtenteController {
	
private VeicoloService veicoloService;
	
	private UtenteService utenteService;
	
	public UtenteController(UtenteService utenteService, VeicoloService veicoloService) {
		this.utenteService = utenteService;
		this.veicoloService=veicoloService;
	}
	
	// per vedere tutti
	@ModelAttribute("utenti")
	   public List<Utente> getAllUtenti() {
	       return utenteService.getUtenti();
	   }
		
	   @ModelAttribute("veicoli")
	   public List<Veicolo> getAllVeicoli() {
			
	       return veicoloService.getVeicoli();
	  
		}
	   
	   // LISTA DI TUTTI GLI UTENTI
	   @GetMapping("/lista")
		public String getUtente(Model model) {
			
			model.addAttribute("utente", this.utenteService.getUtenti());
			return "viewUtente/listautenti";
		}
		
		@GetMapping("{id:[0-9]+}")
		public String getUtente(@PathVariable("id") String id, Model model) {
			
			model.addAttribute("utente", this.utenteService.getUtenteById(id));
			return "viewUtente/listautenti";
		}
	   
		// AGGIUNGI UTENTE, REGISTRAZIONE, l'età non è required perchè la calcola in automatico
		@GetMapping("/registrazione")
	public String handleGetRequestUtente() {
		
		return "viewUtente/registrazioneutente";
	}

		
	@PostMapping("/registrazione")
	public String handlePostRequestUtente(Utente utente, Model model) {
		try {
		if(utente.getEta() < 18) {
			return "viewNotifiche/rifiutoutentereg";
		}

		this.utenteService.aggiungiUtente(utente);
		
		return "viewNotifiche/registrazioneutentesuccesso";
		} catch (Exception e) {
			e.printStackTrace();
			return "viewNotifiche/erroregenerico";
		}
	}
		
		// ELIMINA UTENTE
	@GetMapping("/rimuoviutente")
	public String getRimuovi() {
		
		return "viewUtente/eliminautente";
	}
	
	@PostMapping("/rimuoviutente")
	public String Rimuovi(Utente utente) {
		try {
		this.utenteService.deleteUtente(utente.getcF());
	
		System.out.println(utente);
		return "viewNotifiche/notificagenerica";
	} catch (Exception e) {
		e.printStackTrace();
		return "viewNotifiche/erroregenerico";
	}
	
	}
	
	
	// MODIFICA UTENTE

    @GetMapping("/modificautente")
    public String mostraFormModifica(Model model) {
        
        return "viewUtente/modificautente";
    }

    @PostMapping("/modificautente")
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
}
	
	// VEDERE INFO DI UN SINGOLO UTENTE CON VEICOLO ASSOCIATO
/*	@GetMapping("{cF}")
	public String getUtenteVeicolo(@PathVariable("cF") String cF, Integer id, Model model) {
	    model.addAttribute("utente", this.utenteService.getUtenteById(cF));
	    if (id != null) {
	        model.addAttribute("veicolo", this.veicoloService.getVeicoloById(id));
	    } else {
	        System.out.println("non presente");
	    }
	   
	    return "viewUtente/dettagli";
	}*/
	
	
	
	// modifica utente da fare, in home operazioni e lista

