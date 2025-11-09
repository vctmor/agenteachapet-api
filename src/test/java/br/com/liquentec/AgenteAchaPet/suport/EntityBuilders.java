package br.com.liquentec.AgenteAchaPet.suport;

import java.time.LocalDateTime;

import br.com.liquentec.AgenteAchaPet.model.Person;
import br.com.liquentec.AgenteAchaPet.model.Pet;
import br.com.liquentec.AgenteAchaPet.model.PetSearch;
import br.com.liquentec.AgenteAchaPet.model.Role;

public class EntityBuilders {

     public static Person person(String name, String email) {
    Person p = new Person();
    p.setName(name); p.setEmail(email); p.setPhone("11999990000"); p.setRole(Role.REPORTER);
    return p;
  }
  public static Pet pet(Person owner, String name) {
    Pet pet = new Pet();
    pet.setName(name); pet.setColor("preto"); pet.setBreed("SRD");
    pet.setAge(3); pet.setPerson(owner);
    return pet;
  }
  public static PetSearch petSearch(Pet pet, Person by, String slug) {
    PetSearch ps = new PetSearch();
    ps.setPet(pet); ps.setRegisteredBy(by);
    ps.setSlug(slug); ps.setLocation("Vila Sônia"); ps.setDisappearanceDate(LocalDateTime.now().minusDays(1));
    ps.setAdditionalNotes("assustada"); ps.setSpecialNeed("medicação");
    return ps;
  }
    
}
