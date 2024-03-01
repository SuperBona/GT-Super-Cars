package it.concessionaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.concessionaria.model.Utente;

public interface UtenteRepository extends  JpaRepository<Utente,String> {

}
