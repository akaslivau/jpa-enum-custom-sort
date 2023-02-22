package ru.did.jpaenumcustomrsort.domain.enums;

import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.did.jpaenumcustomrsort.aspect.OrderlyEnum;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Didyk Andrey
 */
@AllArgsConstructor
@Getter
public enum AnimalKindEnum implements OrderlyEnum {
    ARTIODACTYLS("Парнокопытные", 4),
    BIRDS("Птицы"),
    INTERVIEWER("Интервьюер");

    private final String title;
    private final Integer legCount;

    /**
     * Ищет экземпляр перечисления по его наименованию.
     */
    public static Optional<RecruiterKindEnum> find(String o) {
        if (Strings.isNullOrEmpty(o)) {
            return Optional.empty();
        }
        return Stream.of(RecruiterKindEnum.values()).filter(k -> k.toString().equals(o)).findFirst();
    }

    /**
     * Проверяет наличие экземпляра в перечислении по его наименованию.
     */
    public static boolean exists(String o) {
        return Stream.of(RecruiterKindEnum.values()).anyMatch(k -> Objects.equals(k.name(), o));
    }

    /**
     * Ищет список экземпляров перечисления по наименованиям.
     */
    public static List<RecruiterKindEnum> findAll(Collection<String> names) {
        return Stream.of(RecruiterKindEnum.values())
                .filter(k -> names.contains(k.toString()))
                .collect(Collectors.toList());
    }

    /**
     * Возвращает весь список перечисления.
     */
    public static List<RecruiterKindEnum> findAll() {
        return Stream.of(RecruiterKindEnum.values()).collect(Collectors.toList());
    }
}
