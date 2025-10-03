package io.github.jlapugot.adaptr;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class AdaptRTest {

    interface PersonDTO {
        String getName();

        @Mapping("age_in_years")
        Integer getAge();

        @Mapping("full_name")
        String getFullName();

        Boolean isActive();
    }

    @Test
    void shouldAdaptMapToInterface() {
        // Given
        Map<String, Object> personData = new HashMap<>();
        personData.put("name", "John Doe");
        personData.put("age_in_years", 30);
        personData.put("full_name", "John Michael Doe");
        personData.put("active", true);

        // When
        PersonDTO person = AdaptR.adapt(personData, PersonDTO.class);

        // Then
        assertThat(person.getName()).isEqualTo("John Doe");
        assertThat(person.getAge()).isEqualTo(30);
        assertThat(person.getFullName()).isEqualTo("John Michael Doe");
        assertThat(person.isActive()).isTrue();
    }

    @Test
    void shouldHandleToString() {
        // Given
        Map<String, Object> personData = new HashMap<>();
        personData.put("name", "Jane Doe");

        // When
        PersonDTO person = AdaptR.adapt(personData, PersonDTO.class);

        // Then
        assertThat(person.toString()).contains("AdaptR Proxy");
        assertThat(person.toString()).contains("name");
    }

    @Test
    void shouldDeriveKeyFromMethodName() {
        // Given
        Map<String, Object> personData = new HashMap<>();
        personData.put("name", "Alice");

        // When
        PersonDTO person = AdaptR.adapt(personData, PersonDTO.class);

        // Then - getName() should map to "name" key
        assertThat(person.getName()).isEqualTo("Alice");
    }

    @Test
    void shouldUseMappingAnnotationWhenPresent() {
        // Given
        Map<String, Object> personData = new HashMap<>();
        personData.put("age_in_years", 25);

        // When
        PersonDTO person = AdaptR.adapt(personData, PersonDTO.class);

        // Then - getAge() should use @Mapping("age_in_years")
        assertThat(person.getAge()).isEqualTo(25);
    }
}
