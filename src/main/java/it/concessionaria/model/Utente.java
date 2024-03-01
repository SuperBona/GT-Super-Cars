package it.concessionaria.model;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
@Entity
public class Utente {
	@Id
   private String cF;
   private String nome;
   private String cognome;
   private int eta;
   private LocalDate dataDiNascita;
	@JsonIgnore
	@OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
	private List<Veicolo> veicoli;
	public Utente() {
		veicoli = new ArrayList<>();
	}
	 public Utente(String cF, String nome, String cognome, LocalDate dataDiNascita) {
	        this.cF = cF;
	        this.nome = nome;
	        this.cognome = cognome;
	        this.dataDiNascita = dataDiNascita;
	        this.eta=calcolaEta();
	        veicoli = new ArrayList<>();
	}
	public String getcF() {
		return cF;
	}
	public void setcF(String cF) {
		this.cF = cF;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public LocalDate getDataDiNascita() {
		return dataDiNascita;
	}
	public void setDataDiNascita(LocalDate dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
		this.eta = calcolaEta();
	}
	public List<Veicolo> getVeicoli() {
		return veicoli;
	}
	public void setVeicoli(List<Veicolo> veicoli) {
		this.veicoli = veicoli;
	}
	private int calcolaEta() {
	    LocalDate oggi = LocalDate.now();
	    return Period.between(dataDiNascita, oggi).getYears();
	}
	
	public int getEta() {
		return eta;
	}
	@Override
	public String toString() {
	    return "Il/La Sig.re/ra " + nome + " " + cognome + "(codice fiscale: "+cF+"), è nato/a il "+dataDiNascita+" ed ha "+ eta +" anni.";
	}
}


