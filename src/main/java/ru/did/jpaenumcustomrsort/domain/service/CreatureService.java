package ru.did.jpaenumcustomrsort.domain.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CreatureService {

    Page<CreatureDto> findCreatures(CreaturePredicate predicate, Pageable pageable);
}
