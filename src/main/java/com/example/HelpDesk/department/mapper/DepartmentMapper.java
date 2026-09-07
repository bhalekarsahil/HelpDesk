package com.example.HelpDesk.department.mapper;

import com.example.HelpDesk.department.dtos.ReqDepartmentDto;
import com.example.HelpDesk.department.dtos.ResDepartmentDto;
import com.example.HelpDesk.department.entity.Department;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentMapper {
    Department toEntity(ReqDepartmentDto reqDepartmentDto);

    ResDepartmentDto toRes(Department savedDepartment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDepartmentDto(ReqDepartmentDto reqDepartmentDto, @MappingTarget Department existingDepartment);
}
