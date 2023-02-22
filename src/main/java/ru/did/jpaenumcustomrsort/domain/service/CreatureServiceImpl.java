package ru.did.jpaenumcustomrsort.domain.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.did.jpaenumcustomrsort.domain.controller.CreatureDto;
import ru.did.jpaenumcustomrsort.domain.controller.CreaturePredicate;
import ru.did.jpaenumcustomrsort.aspect.EnumSorting;
import ru.did.jpaenumcustomrsort.domain.model.Creature;
import ru.did.jpaenumcustomrsort.domain.model.QCreature;
import ru.did.jpaenumcustomrsort.domain.repository.CreatureJpaRepository;
import ru.did.jpaenumcustomrsort.domain.service.mapper.CreatureMapper;

import java.util.Optional;

@Service
@Primary
@RequiredArgsConstructor
public class CreatureServiceImpl implements CreatureService {

    private final CreatureJpaRepository jpaRepository;

    @EnumSorting(className = Creature.class)
    @Transactional(readOnly = true)
    @Override
    public Page<CreatureDto> findCreatures(CreaturePredicate predicate, Pageable pageable) {
        QCreature q = QCreature.creature;
        BooleanBuilder bb = new BooleanBuilder();

        Optional.ofNullable(predicate.getName()).map(q.name::containsIgnoreCase).ifPresent(bb::and);
        Optional.ofNullable(predicate.getKinds()).map(q.kind.stringValue()::in).ifPresent(bb::and);
        Optional.ofNullable(predicate.getWeightFrom()).map(q.weight::goe).ifPresent(bb::and);
        Optional.ofNullable(predicate.getWeightTo()).map(q.weight::loe).ifPresent(bb::and);

        return jpaRepository.findAll(bb, pageable).map(CreatureMapper::to);
    }
}
