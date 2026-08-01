ALTER TABLE encontro
    ADD CONSTRAINT FK_ENCONTRO_ON_BENEFICIADO FOREIGN KEY (beneficiado_id) REFERENCES usuario (id);

ALTER TABLE encontro
    ADD CONSTRAINT FK_ENCONTRO_ON_MATERIA FOREIGN KEY (materia_id) REFERENCES materia (id);

ALTER TABLE encontro
    ADD CONSTRAINT FK_ENCONTRO_ON_MONITOR FOREIGN KEY (monitor_id) REFERENCES usuario (id);

ALTER TABLE aluno_materia
    ADD CONSTRAINT fk_alumat_on_aluno FOREIGN KEY (aluno_id) REFERENCES usuario (id);

ALTER TABLE aluno_materia
    ADD CONSTRAINT fk_alumat_on_materia FOREIGN KEY (materia_id) REFERENCES materia (id);

ALTER TABLE aluno_disponibilidade
    ADD CONSTRAINT fk_aluno_disponibilidade_on_aluno FOREIGN KEY (aluno_id) REFERENCES usuario (id);