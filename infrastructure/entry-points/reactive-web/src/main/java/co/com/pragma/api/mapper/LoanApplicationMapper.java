package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.LoanApplicationResponseDto;
import co.com.pragma.api.dto.SaveLoanApplicationDto;
import co.com.pragma.model.loanapplication.LoanApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LoanApplicationMapper {

    @Mapping(target = "idLoan", ignore = true)
    LoanApplication toLoanApplication(SaveLoanApplicationDto saveLoanApplicationDto);

    LoanApplicationResponseDto toLoanApplicationResponseDto(LoanApplication user);
}
