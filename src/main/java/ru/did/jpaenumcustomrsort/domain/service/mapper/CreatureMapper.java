package ru.did.jpaenumcustomrsort.domain.service.mapper;

import ru.did.jpaenumcustomrsort.domain.controller.CreatureDto;
import ru.did.jpaenumcustomrsort.domain.enums.CreatureKind;
import ru.did.jpaenumcustomrsort.domain.model.Creature;

import java.math.BigDecimal;
import java.util.Optional;


public class CreatureMapper {

    public static CreatureDto to(Creature o) {
        CreatureDto dto = new CreatureDto();

        dto.setId(o.getId());
        dto.setName(o.getName());
        Optional.ofNullable(o.getKind()).map(Enum::name).ifPresent(dto::setKind);
        Optional.ofNullable(o.getKind()).map(CreatureKind::getLegCount).ifPresent(dto::setLegCount);
        Optional.ofNullable(o.getWeight()).map(BigDecimal::doubleValue).ifPresent(dto::setWeight);

        return dto;
    }

}
