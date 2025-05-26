package br.com.liquentec.AgenteAchaPet.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.liquentec.AgenteAchaPet.model.Person;
import br.com.liquentec.AgenteAchaPet.model.Pet;

@SpringBootTest
public class PetTest {

	@Test
	void testPetConstructorAndGetters() {

		Person person = new Person();
		person.setId(1L);
		person.setName("João");

		byte[] image = new byte[] { 1, 2, 3 };

		Pet pet = new Pet(
				1L,
				"Totó",
				"Preto",
				"Vira-lata",
				3,
				image,
				person);

		assertEquals(1L, pet.getId());
		assertEquals("Totó", pet.getName());
		assertEquals("Preto", pet.getColor());
		assertEquals("Vira-lata", pet.getBreed());
		assertEquals(3, pet.getAge());
		assertArrayEquals(image, pet.getImage());
		assertEquals(person, pet.getPerson());
	}

	@Test
	void testSetters() {
		Pet pet = new Pet();
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