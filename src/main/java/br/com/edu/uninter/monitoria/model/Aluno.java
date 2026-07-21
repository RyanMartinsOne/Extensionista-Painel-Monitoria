package br.com.edu.uninter.monitoria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "aluno")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    private String telefone;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoAluno tipo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "aluno_materia",
            joinColumns = @JoinColumn(name = "aluno_id"),
            inverseJoinColumns = @JoinColumn(name = "materia_id")
    )
    private Set<Materia> materias =  new HashSet<>();

    @ElementCollection
    @CollectionTable(
            name = "aluno_disponibilidade",
            joinColumns = @JoinColumn(name = "aluno_id")
    )
    private Set<String> disponibilidade =  new HashSet<>();
}
