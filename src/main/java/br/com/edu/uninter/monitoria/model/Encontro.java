package br.com.edu.uninter.monitoria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "encontro")
@NoArgsConstructor
public class Encontro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Usuario monitor;

    @Column(nullable = false)
    private String beneficiado;
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Materia materia;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusEncontro status = StatusEncontro.AGENDADO;

    @NotNull
    private LocalDateTime dataHora;

    private String observacoes;
}
