package ru.did.jpaenumcustomrsort.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.did.jpaenumcustomrsort.domain.model.Creature;

public interface CreatureJpaRepository extends JpaRepository<Creature, Long>, QuerydslPredicateExecutor<Creature> {

}