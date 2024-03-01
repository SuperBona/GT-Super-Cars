package it.concessionaria.model;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class Veicolo {
	@Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
   private String tipo;
   private LocalDate dataInizio;
   private LocalDate dataFine;
   private LocalDate dataVendita;
   private String modello;
   private String marca;
   private double costoAcquisto;
   private double costoNoleggio;
   private double chilometraggio;
	@JsonIgnore
	@ManyToOne(optional= true)
	@JoinColumn(name = "fk_utente", referencedColumnName = "cF")
	private Utente utente;
	public Veicolo() {	}	
	public Veicolo(String tipo, LocalDate dataInizio, LocalDate dataFine, LocalDate dataVendita, String modello, String marca, double costoAcquisto, double costoNoleggio, double chilometraggio) {
       this.tipo = tipo;
       this.dataInizio = dataInizio;
       this.dataFine = dataFine;
       this.dataVendita = dataVendita;
       this.modello = modello;
       this.marca = marca;
       this.costoAcquisto = costoAcquisto;
       this.costoNoleggio = costoNoleggio;
       this.chilometraggio = chilometraggio;
   }
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getModello() {
		return modello;
	}
	public void setModello(String modello) {
		this.modello = modello;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public double getCostoAcquisto() {
		return costoAcquisto;
	}
	public void setCostoAcquisto(double costoAcquisto) {
		this.costoAcquisto = costoAcquisto;
	}
	public double getCostoNoleggio() {
		return costoNoleggio;
	}
	public void setCostoNoleggio(double costoNoleggio) {
		this.costoNoleggio = costoNoleggio;
	}
	public double getChilometraggio() {
		return chilometraggio;
	}
	public void setChilometraggio(double chilometraggio) {
		this.chilometraggio = chilometraggio;
	}
	
	public LocalDate getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(LocalDate dataInizio) {
		this.dataInizio = dataInizio;
	}
	public LocalDate getDataFine() {
		return dataFine;
	}
	public void setDataFine(LocalDate dataFine) {
		this.dataFine = dataFine;
	}
	public LocalDate getDataVendita() {
		return dataVendita;
	}
	public void setDataVendita(LocalDate dataVendita) {
		this.dataVendita = dataVendita;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public int getId() {
		return id;
	}
	@Override
	public String toString() {
		if ((dataVendita==null) & (dataInizio == null)) {
			return "L'ID del veicolo " + tipo + " è " + id + ". Il modello è " +
		            modello + " della marca " + marca + ". Il costo di acquisto è " + costoAcquisto +
		            ", il costo di noleggio è " + costoNoleggio + ", e ha " + chilometraggio +
		            " chilometri.";
		}
		if (dataVendita!=null) {
			return "L'ID del veicolo " + tipo + " è " + id + ". È stato venduto il " + dataVendita + ". Il modello è " +
		            modello + " della marca " + marca + ". Il costo di acquisto è " + costoAcquisto +
		            "e ha " + chilometraggio + " chilometri. Appartiene all'utente con codice fiscale " + utente.getcF() + ".";
		}
		else {
		return "L'ID del veicolo " + tipo + " è " + id + ". Il noleggio è partito in data " + dataInizio +
		            " e finirà il giorno " + dataFine + ". Il modello è " + modello + " della marca " + marca + ". Il costo di noleggio è " + costoNoleggio + ", e ha " + chilometraggio + " chilometri. È stato affittato dall'utente con codice fiscale " + utente.getcF() + ".";
		}
	}
}

