package TennisMatchScoreboard.util;

import TennisMatchScoreboard.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationUtilsTest {
    private final static int REQUIRED_LENGTH_FOR_NAME = 20;


    @ParameterizedTest
    @MethodSource("checkValidNames")
    void shouldAcceptValidNames(String name1, String name2) {
        assertDoesNotThrow(() -> ValidationUtils.validate(name1, name2));
    }


    @ParameterizedTest
    @MethodSource("checkSimilarNames")
    void shouldThrowInvalidParameterException(String name1, String name2) {
        var exception = assertThrows(InvalidParameterException.class,
                () -> ValidationUtils.validate(name1, name2));

        assertThat(exception.getMessage()).isEqualTo("Player names must be different");
    }

    @ParameterizedTest
    @MethodSource("namesIsBlank")
    void shouldThrowNameCannotBeBlank(String name1, String name2) {

        var exception = assertThrows(InvalidParameterException.class,
                () -> ValidationUtils.validate(name1, name2));
        assertThat(exception.getMessage()).isEqualTo("Name cannot be blank");
    }

    @Test
    void shouldThrowRequiredLengthMessage(){
        var exception = assertThrows(InvalidParameterException.class,
                () -> ValidationUtils.validate("123456789012345678901", "ура"));
        assertThat(exception.getMessage()).isEqualTo("Name cannot exceed " + REQUIRED_LENGTH_FOR_NAME + " characters");
    }


    private static Stream<Arguments> checkValidNames() {
        return Stream.of(
                Arguments.of("Roger", "Rafael"),
                Arguments.of("Roge r", "1 Roger"),
                Arguments.of("Андрей Рублёв", "Даниил Медведев"),
                Arguments.of("D".repeat(20), "M".repeat(20))
        );
    }


    private static Stream<Arguments> checkSimilarNames() {
        return Stream.of(
                Arguments.of("Сергей", "сергей"),
                Arguments.of("Сергей", "Серге й"),
                Arguments.of(" И в а н", "иван"),
                Arguments.of("СЕРГЕЙ", "сергей")
        );
    }


    private static Stream<Arguments> namesIsBlank() {
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of("ds", ""),
                Arguments.of("", "ds"),
                Arguments.of("  ", " "),
                Arguments.of("sd dss  ", " "),
                Arguments.of(" ", "dsd dssd ")
        );
    }



}
