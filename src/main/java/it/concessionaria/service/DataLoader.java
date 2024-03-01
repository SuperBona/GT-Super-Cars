package it.concessionaria.service;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import it.concessionaria.model.Utente;
import it.concessionaria.model.Veicolo;
import it.concessionaria.repository.UtenteRepository;
import it.concessionaria.repository.VeicoloRepository;
import jakarta.annotation.PostConstruct;
@Service
public class DataLoader {
	private UtenteRepository utenteRepository;
	private VeicoloRepository veicoloRepository;
	
	public DataLoader(UtenteRepository utenteRepository, VeicoloRepository veicoloRepository) {
		super();
		this.utenteRepository = utenteRepository;
		this.veicoloRepository = veicoloRepository;
	}
	@PostConstruct
	public void loadData() {		
		 // Creazione di 10 oggetti di Veicolo con valori realistici
		Veicolo v1 = new Veicolo("car", LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1), null, "Chiron", "Bugatti", 300000.0, 400000.0, 1000.0);
		Veicolo v2 = new Veicolo("car", LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 1), null, "Porsche 911 Turbo S", "Porsche", 22000.0, 25000.0, 50000.0);
		Veicolo v3 = new Veicolo("motorcycle", LocalDate.of(2021, 1, 1), LocalDate.of(2022, 1, 1), null, "Ducati Panigale V4 R", "Ducati", 1500.0, 6000.0, 1500.0);
		Veicolo v4 = new Veicolo("car", LocalDate.of(2020, 1, 1), LocalDate.of(2021, 1, 1), null, "Audi RS6 Avant", "Audi", 1200.0, 5000.0, 1200.0);
		Veicolo v5 = new Veicolo("motorcycle", LocalDate.of(2019, 1, 1), LocalDate.of(2020, 1, 1), null, "BMW S 1000 RR", "BMW", 20000.0, 45000.0, 120000.0);
		Veicolo v6 = new Veicolo("car", LocalDate.of(2018, 1, 1), LocalDate.of(2019, 1, 1), null, "Mercedes-AMG GT R", "Mercedes", 20000.0, 25000.0, 50000.0);
		Veicolo v7 = new Veicolo("motorcycle", LocalDate.of(2017, 1, 1), LocalDate.of(2018, 1, 1), null, "MT125", "Yamaha", 1800.0, 7000.0, 1800.0);
		Veicolo v8 = new Veicolo("motorcycle", LocalDate.of(2016, 1, 1), LocalDate.of(2017, 1, 1), null, "Kawasaki Ninja ZX-10R", "Kawasaki", 30000.0, 70000.0, 180000.0);
		Veicolo v9 = new Veicolo("car", null, null, LocalDate.of(2015, 1, 1), "Ford Mustang GT", "Ford", 18000.0, 22000.0, 45000.0);
		Veicolo v10 = new Veicolo("motorcycle", null, null, null, "Fireblade", "Honda", 35000.0, 90000.0, 250000.0);
		Utente utente1 = new Utente("ABC12345", "Mario", "Rossi", LocalDate.of(1999, 9, 20));
		Utente utente2 = new Utente("DEF67890", "Luigi", "Bianchi", LocalDate.of(2005, 11, 16));
		Utente utente3 = new Utente("GHI12345", "Giovanna", "Verdi", LocalDate.of(1985, 05, 01));
		Utente utente4 = new Utente("JKL67890", "Anna", "Neri", LocalDate.of(2007, 03,17));
		
		utente1.getVeicoli().add(v1);
		v1.setUtente(utente1);
		
		utente1.getVeicoli().add(v2);
		v2.setUtente(utente1);
		
		utente2.getVeicoli().add(v3);
		v3.setUtente(utente2);
		
		utente2.getVeicoli().add(v4);
		v4.setUtente(utente2);
		
		utente2.getVeicoli().add(v5);
		v5.setUtente(utente2);
		
		utente3.getVeicoli().add(v6);
		v6.setUtente(utente3);
		
		utente3.getVeicoli().add(v7);
		v7.setUtente(utente3);
		
		utente4.getVeicoli().add(v8);
		v8.setUtente(utente4);
		
		utente2.getVeicoli().add(v9);
		v9.setUtente(utente2);
		
		utente4.getVeicoli().add(v10);
		v10.setUtente(utente4);
		
		utenteRepository.saveAll(List.of(utente1,utente2,utente3,utente4));
		veicoloRepository.saveAll(List.of(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10));
		
	}
}

