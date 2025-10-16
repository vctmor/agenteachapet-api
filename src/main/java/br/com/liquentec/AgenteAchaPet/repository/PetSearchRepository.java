package br.com.liquentec.AgenteAchaPet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.liquentec.AgenteAchaPet.model.PetSearch;

public interface PetSearchRepository extends JpaRepository<PetSearch, Long> {
    
    List<PetSearch> findByPetId(Long petId);

    @Query("""
          select ps from PetSearch ps
          left join fetch ps.pet
          left join fetch ps.registeredBy
        """)
    java.util.List<PetSearch> findAllDetailed();

    @Query("""
          select ps from PetSearch ps
          left join fetch ps.pet
          left join fetch ps.registeredBy
          where ps.slug = :slug
        """)

    Optional<PetSearch> findBySlug(String slug);

    @Query("""
  select ps from PetSearch ps
  left join fetch ps.pet
  left join fetch ps.registeredBy
  where ps.slug = :slug
""")
Optional<PetSearch> findDetailedBySlug(@Param("slug") String slug);
}