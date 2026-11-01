package org.example.segundoapinosql.infrastructure.database.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "token_dispositivo", timeToLive = 15_552_000L)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDispositivoEntity {
    @Id
    private String token;

    @Indexed
    private Long usuarioId;
}
