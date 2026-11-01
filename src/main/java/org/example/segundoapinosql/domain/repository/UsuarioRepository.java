package org.example.segundoapinosql.domain.repository;

import java.util.Optional;
import java.util.List;
import org.example.segundoapinosql.domain.model.Usuario;

public interface UsuarioRepository {

    Optional<Usuario> findById(Long id);

    List<Usuario> listarGestoresETecnicosAtivosDaUnidade(Long unidadeId);

}
