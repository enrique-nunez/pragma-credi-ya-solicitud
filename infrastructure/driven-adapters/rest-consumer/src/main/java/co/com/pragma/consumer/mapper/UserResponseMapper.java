package co.com.pragma.consumer.mapper;

import co.com.pragma.consumer.dto.UserResponseDto;
import co.com.pragma.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    @Mapping(target = "roleId", ignore = true)
    User toModel(UserResponseDto dto);

}
