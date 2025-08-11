package br.com.liquentec.AgenteAchaPet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.liquentec.AgenteAchaPet.model.PetSearch;

public interface PetSearchRepository extends JpaRepository<PetSearch, Long> {
    
    List<PetSearch> findByPetId(Long petId);

    Optional<PetSearch> findBySlug(String slug);
}