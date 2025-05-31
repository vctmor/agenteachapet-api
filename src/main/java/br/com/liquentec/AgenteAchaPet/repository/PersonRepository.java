package br.com.liquentec.AgenteAchaPet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.liquentec.AgenteAchaPet.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
