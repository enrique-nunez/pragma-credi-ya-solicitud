package co.com.pragma.r2dbc.mappers;

import co.com.pragma.model.loanapplication.dto.LoanApplicationPagedResponse;
import co.com.pragma.r2dbc.dto.LoanApplicationFieldsPageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanApplicationSearchMapper {

    @Mapping(target = "nombreUsuario", ignore = true)
    LoanApplicationPagedResponse toModel(LoanApplicationFieldsPageDto dto);
}
