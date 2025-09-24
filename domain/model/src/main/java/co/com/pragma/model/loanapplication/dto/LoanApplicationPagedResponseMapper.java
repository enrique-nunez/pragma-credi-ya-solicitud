package co.com.pragma.model.loanapplication.dto;

import co.com.pragma.model.user.User;

import java.util.List;

public class LoanApplicationPagedResponseMapper {
    public static LoanApplicationPagedResponse map(LoanApplicationPagedResponse summary, List<User> users) {
        String nombreUsuario = null;
        if (summary.getEmailUsuario() != null) {
            nombreUsuario = users.stream()
                    .filter(user -> user.getEmail().equals(summary.getEmailUsuario()))
                    .map(User::getFullName)
                    .findFirst()
                    .orElse(null);
        }
        return LoanApplicationPagedResponse.builder()
                .idSolicitud(summary.getIdSolicitud())
                .montoSolicitado(summary.getMontoSolicitado())
                .plazoMeses(summary.getPlazoMeses())
                .tipoPrestamo(summary.getTipoPrestamo())
                .tasaInteres(summary.getTasaInteres())
                .estadoSolicitud(summary.getEstadoSolicitud())
                .deudaTotalMensual(summary.getDeudaTotalMensual())
                .emailUsuario(summary.getEmailUsuario())
                .nombreUsuario(nombreUsuario)
                .build();
    }
}
