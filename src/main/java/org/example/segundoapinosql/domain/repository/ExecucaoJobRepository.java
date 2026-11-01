package org.example.segundoapinosql.domain.repository;

import org.example.segundoapinosql.domain.model.ExecucaoJob;

public interface ExecucaoJobRepository {
    boolean iniciarSeAusente(ExecucaoJob execucao);

    void atualizar(ExecucaoJob execucao);
}
