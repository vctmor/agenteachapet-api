package br.com.liquentec.AgenteAchaPet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.liquentec.AgenteAchaPet.model.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {


}
