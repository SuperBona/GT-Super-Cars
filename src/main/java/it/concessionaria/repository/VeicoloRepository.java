package it.concessionaria.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import it.concessionaria.model.Veicolo;
@Repository
public interface VeicoloRepository extends  JpaRepository<Veicolo,Integer>{
			
	
	@Query("SELECT v FROM Veicolo AS v WHERE v.dataInizio IS NULL AND v.dataVendita IS NOT NULL")
	 Optional <List<Veicolo>>  ricercaVenduti (@Param("filtrovendita")int id);
	
	
	@Query("SELECT v FROM Veicolo AS v WHERE v.dataVendita BETWEEN  :filtroInizio  AND :filtroFine")
	Optional <List<Veicolo>> ricercaVenditafiltro (@Param ("filtroInizio") LocalDate filtroInizio,
			                           @Param ("filtroFine") LocalDate filtroFine);
	
	@Query("SELECT v FROM Veicolo AS v WHERE  v.tipo= :tipo AND v.dataInizio IS NOT NULL")
	 Optional <List<Veicolo>>  ricercaNoleggiati (@Param("tipo")String tipo);
	
	@Query("SELECT v FROM Veicolo AS v WHERE v.dataInizio >= :filtroInizio AND v.dataFine <= :filtroFine")
	Optional <List<Veicolo>> ricercaNoleggiofiltro (@Param("filtroInizio") LocalDate filtroInizio, @Param("filtroFine") LocalDate filtroFine);
	
	
	@Query("SELECT v FROM Veicolo AS v WHERE dataInizio IS NULL AND dataVendita IS  NULL")
	 Optional <List<Veicolo>>  ricercaVeicoliDisponibili ();
	

		  @Query("SELECT v FROM Veicolo v WHERE v.tipo = :tipo")
		  Optional <List<Veicolo>> findByTipo(@Param("tipo") String tipo);
	
}
