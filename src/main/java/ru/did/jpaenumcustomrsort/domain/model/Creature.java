package ru.did.jpaenumcustomrsort.domain.model;

import lombok.*;
import ru.did.jpaenumcustomrsort.domain.enums.CreatureKind;

import javax.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "creature")
public class Creature {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "kind")
    @Enumerated(EnumType.STRING)
    private CreatureKind kind;

}
