package ru.diasoft.micro.data.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.diasoft.micro.domain.model.CandidateActivityType;

import java.util.Optional;

public interface CandidateActivityTypeJpaRepository extends JpaRepository<CandidateActivityType, Long>, QuerydslPredicateExecutor<CandidateActivityType> {

    Optional<CandidateActivityType> findByNameIgnoreCase(String name);

}