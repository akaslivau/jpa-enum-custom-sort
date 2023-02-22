Around Aspect realization for custom sorting by Entity Enumerated field with dynamic CASE WHEN builder.
Aspect purpose is to implement such custom sorting for Data JPA reposiroty method:

  Page<Entity> findAll(Predicate predicate, Pageable pageble)
  
  where:
    Predicate -- interface from pacakge com.querydsl.core.types;
    Pageable  -- inteface from package org.springframework.data.domain.
  


Briefly:
1) Annotation is used for around aspect. The single parameter is Class<?> of entity, defined in yours JPArepository.
    @EnumSorting(className = Creature.class)

2) Aspect detects Sort, passed via pageable
  If field name of Sort equals any field of Entity, marked with @Enumerated annotation, it dynamically customizes sort.
  
3) As result:

Standard Hibernate query:
select
            creature0_.id as id1_0_,
            creature0_.kind as kind2_0_,
            creature0_.name as name3_0_,
            creature0_.weight as weight4_0_ 
        from
            creature creature0_ 
        order by
            creature0_.kind asc limit ?

Modified Hibernate query:
 select
            creature0_.id as id1_0_,
            creature0_.kind as kind2_0_,
            creature0_.name as name3_0_,
            creature0_.weight as weight4_0_ 
        from
            creature creature0_ 
        order by
            case 
                when cast(creature0_.kind as char)=? then ? 
                when cast(creature0_.kind as char)=? then ? 
                when cast(creature0_.kind as char)=? then ? 
                else 2147483647 
            end asc limit

How to use:
1) Implement OrderlyEnum interface for your enums your need for custom sort.
Ex.: 
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

2) Annotate "findAll" method of your service.
Ex.: 
    @EnumSorting(className = Creature.class)
    @Transactional(readOnly = true)
    @Override
    public Page<CreatureDto> findCreatures(CreaturePredicate predicate, Pageable pageable) {

The end.

============================================================================

Swagger: http://localhost:7080/swagger-ui/

H2: http://localhost:7080/h2-console
    jdbc:h2:mem:testdb;DATABASE_TO_UPPER=false
    (root/root)

