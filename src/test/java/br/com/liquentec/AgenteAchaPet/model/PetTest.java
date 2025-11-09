package br.com.liquentec.AgenteAchaPet.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PetTest {

	private Pet pet;
	private Person person;

	@BeforeEach
	void setup(){

		pet = new Pet();
		person = new Person();
	}

	@Test
	void shouldCreatePetUsingConstructorAndGetAttributes() {

		
		person.setId(1L);
		person.setName("João");

		byte[] image = new byte[] { 1, 2, 3 };
		
		pet.setId(1L);
		pet.setName("Totó");
		pet.setColor("Preto");
		pet.setBreed("Vira-lata");
		pet.setAge(3);
		pet.setPhoto(image);
		pet.setPerson(person);				

		assertEquals(1L, pet.getId());
		assertEquals("Totó", pet.getName());
		assertEquals("Preto", pet.getColor());
		assertEquals("Vira-lata", pet.getBreed());
		assertEquals(3, pet.getAge());
		assertArrayEquals(image, pet.getPhoto());
		assertEquals(person, pet.getPerson());
	}

	@Test
	void shouldSetAndGetAttributesUsingSetters() {

		pet.setName("Rex");
		pet.setColor("Marrom");
		pet.setBreed("Labrador");
		pet.setAge(5);

		assertEquals("Rex", pet.getName());
		assertEquals("Marrom", pet.getColor());
		assertEquals("Labrador", pet.getBreed());
		assertEquals(5, pet.getAge());
	}
}