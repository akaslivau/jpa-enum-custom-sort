package ru.did.jpaenumcustomrsort.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.did.jpaenumcustomrsort.aspect.OrderlyEnum;

@AllArgsConstructor
@Getter
public enum CreatureKind implements OrderlyEnum {
    ARTIODACTYLS("Парнокопытные", 4),
    BIRDS("Птицы", 2),
    ARTHROPODS("Членистоногие", 8);

    private final String title;
    private final Integer legCount;

    @Override
    public Integer getSortOrder() {
        return legCount;
    }
}
