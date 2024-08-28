package com.syniiq.library_management.application.mapper;


import com.syniiq.library_management.application.dto.User.UserReqDTO;
import com.syniiq.library_management.domain.model.User;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;

@Mapper(componentModel = "spring")
@Configuration
public interface AccountMapper {
    User fromUserReqDTO(UserReqDTO userReqDTO);
}
