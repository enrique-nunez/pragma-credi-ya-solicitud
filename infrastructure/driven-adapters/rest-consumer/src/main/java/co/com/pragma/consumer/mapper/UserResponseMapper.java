package co.com.pragma.consumer.mapper;

import co.com.pragma.consumer.dto.UserResponseDto;
import co.com.pragma.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    User toModel(UserResponseDto dto);

}
