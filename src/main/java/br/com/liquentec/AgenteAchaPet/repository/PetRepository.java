package br.com.liquentec.AgenteAchaPet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.liquentec.AgenteAchaPet.model.Pet;
import br.com.liquentec.AgenteAchaPet.model.PetSearch;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {


}
