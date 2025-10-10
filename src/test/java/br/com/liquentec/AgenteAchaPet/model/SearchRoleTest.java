package br.com.liquentec.AgenteAchaPet.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SearchRoleTest {



    @Test
    void shouldContainAllExpectedRoles() {
        Role[] expectedRoles = { Role.TUTOR, Role.BASTIAN, Role.SENTINEL, Role.RESCUER };
        Role[] actualRoles = Role.values();

        assertArrayEquals(expectedRoles, actualRoles);
    }

    @Test
    void shouldReturnCorrectRoleFromValueOf() {
        assertEquals(Role.TUTOR, Role.valueOf("TUTOR"));
        assertEquals(Role.BASTIAN, Role.valueOf("BASTIAN"));
        assertEquals(Role.SENTINEL, Role.valueOf("SENTINEL"));
        assertEquals(Role.RESCUER, Role.valueOf("RESCUER"));
        assertEquals(Role.REPORTER, Role.valueOf("REPORTER"));
        assertEquals(Role.ADOPTER, Role.valueOf("ADOPTER"));
    }

    @Test
    void valueOfShouldThrowExceptionForInvalidRole() {
        assertThrows(IllegalArgumentException.class, () -> {
            Role.valueOf("INVALID_ROLE");
        });
    }
}
