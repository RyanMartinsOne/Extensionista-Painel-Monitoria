package br.com.edu.uninter.monitoria.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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

    private String assunto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEncontro status = StatusEncontro.AGENDADO;

    private LocalDateTime dataHora;

    @Column(length = 1000)
    private String observacoes;
}