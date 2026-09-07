package com.example.HelpDesk.user.mapper;

import com.example.HelpDesk.ticket.dtos.ReqTicketDto;
import com.example.HelpDesk.ticket.entity.Ticket;
import com.example.HelpDesk.user.dtos.ReqUserDto;
import com.example.HelpDesk.user.dtos.ResUserDto;
import com.example.HelpDesk.user.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "department", ignore = true)
    User toEntity(ReqUserDto dto);

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    ResUserDto toRes(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUserDto(ReqUserDto dto, @MappingTarget User user);
}
