package org.example.segundoapinosql.infrastructure.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoSqlOpenApiCustomizer implements OpenApiCustomizer {
    @Override
    public void customise(OpenAPI openAPI) {
        if (openAPI.getPaths() == null) return;

        aplicar(openAPI, List.of(
                new SwaggerEndpoint("POST", "/api/v1/modelo-checklist/cadastrar", "Modelos de checklist", "Cadastrar modelo de checklist", "Cria um modelo de checklist."),
                new SwaggerEndpoint("GET", "/api/v1/modelo-checklist/listar", "Modelos de checklist", "Listar modelos de checklist", "Lista os modelos do unidade do usuário."),
                new SwaggerEndpoint("PATCH", "/api/v1/modelo-checklist/atualizar", "Modelos de checklist", "Atualizar modelo de checklist", "Atualiza um modelo de checklist."),
                new SwaggerEndpoint("DELETE", "/api/v1/modelo-checklist/deletar/{modeloChecklistId}", "Modelos de checklist", "Excluir modelo de checklist", "Remove um modelo de checklist."),
                new SwaggerEndpoint("GET", "/api/v1/inspecao-checklist/modelos/{modeloChecklistId}", "Inspeções", "Listar modelos do checklist", "Lista somente os modelos vinculados ao checklist."),
                new SwaggerEndpoint("POST", "/api/v1/inspecao-checklist/cadastrar", "Inspeções", "Cadastrar inspeção", "Registra uma inspeção do equipamento.")
        ));
    }

    private void aplicar(OpenAPI api, List<SwaggerEndpoint> endpoints) {
        endpoints.forEach(endpoint -> {
            PathItem path = api.getPaths().get(endpoint.path());
            if (path == null) return;
            Operation operation = switch (endpoint.method()) {
                case "GET" -> path.getGet();
                case "POST" -> path.getPost();
                case "PATCH" -> path.getPatch();
                case "DELETE" -> path.getDelete();
                default -> null;
            };
            if (operation == null) return;
            operation.setTags(List.of(endpoint.tag()));
            operation.setSummary(endpoint.summary());
            operation.setDescription(endpoint.description());
            operation.setSecurity(List.of(new SecurityRequirement().addList("bearerAuth")));
        });
    }
}
