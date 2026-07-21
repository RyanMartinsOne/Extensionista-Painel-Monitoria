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

    @ManyToOne
    @JoinColumn(name = "monitor_id", nullable = false)
    @NotNull
    private Aluno monitor;

    @ManyToOne
    @JoinColumn(name = "beneficiado_id", nullable = false)
    @NotNull
    private Aluno beneficiado;

    @ManyToOne
    @JoinColumn(name = "materia_id",  nullable = false)
    @NotNull
    private Materia materia;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusEncontro status = StatusEncontro.AGENDADO;

    @NotNull
    private LocalDateTime dataHora;

    private String observacoes;
}
