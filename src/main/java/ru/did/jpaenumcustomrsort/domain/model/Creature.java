package ru.diasoft.micro.domain.model;

import com.querydsl.core.annotations.QueryInit;
import lombok.*;
import ru.diasoft.micro.domain.enums.RecruiterKindEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

/**
 * Модель "Рекрутер" (Recruiter).
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "qpp_recruiter")
@NamedEntityGraph(
        name = Recruiter.RECRUITER_GRAPH,
        attributeNodes = {
                @NamedAttributeNode(value = "selectionApplication", subgraph = "Recruiter.selectionApplication"),
                @NamedAttributeNode(value = "kind")
        }, subgraphs =
@NamedSubgraph(
        name = "Recruiter.selectionApplication",
        attributeNodes = {
                @NamedAttributeNode(value = "department"),
                @NamedAttributeNode(value = "subdivision"),
                @NamedAttributeNode(value = "role"),
                @NamedAttributeNode(value = "customer")
        })
)
public class Recruiter {

    public static final String RECRUITER_GRAPH = "recruiter.detailed-graph";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qpp_recruiter_id_seq")
    @SequenceGenerator(name = "qpp_recruiter_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "recruterkind")
    @Enumerated(EnumType.STRING)
    private RecruiterKindEnum kind;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selectionapplicationid")
    @QueryInit("*.*.*.*")
    private SelectionApplicationServiceEntity selectionApplication;

    @NotNull
    @Column(name = "recruterid")
    private Long employeeId;

    @Column(name = "datefrom")
    private LocalDate dateFrom;

    @Column(name = "dateto")
    private LocalDate dateTo;

    @NotNull
    @Column(name = "activity")
    private Boolean activity;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY)
    private Set<CandidateActivity> candidateActivities;

}
