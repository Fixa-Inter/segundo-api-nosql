package org.example.segundoapinosql.application.security;

import org.example.segundoapinosql.application.exception.RegraProblemaException;
import org.example.segundoapinosql.domain.enums.TipoAcesso;

/** Regras de acesso por perfil para os fluxos de checklist. */
public final class PermissaoChecklist {
    private PermissaoChecklist() { }

    public static void exigirGestao(TipoAcesso tipoAcesso) {
        if (tipoAcesso != TipoAcesso.ADMINISTRADOR && tipoAcesso != TipoAcesso.GESTOR) {
            throw new RegraProblemaException("exception.access.denied");
        }
    }

    public static void exigirInspecao(TipoAcesso tipoAcesso) {
        if (tipoAcesso != TipoAcesso.ADMINISTRADOR
                && tipoAcesso != TipoAcesso.GESTOR
                && tipoAcesso != TipoAcesso.TECNICO) {
            throw new RegraProblemaException("exception.access.denied");
        }
    }
}
