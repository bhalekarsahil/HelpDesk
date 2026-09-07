package com.example.HelpDesk.department.DepartmentService;

import com.example.HelpDesk.department.DepartmentRepository.DepartmentRepository;
import com.example.HelpDesk.department.dtos.ReqDepartmentDto;
import com.example.HelpDesk.department.dtos.ResDepartmentDto;
import com.example.HelpDesk.department.entity.Department;
import com.example.HelpDesk.department.mapper.DepartmentMapper;
import com.example.HelpDesk.exception.ResourceNotFoundException;
import com.example.HelpDesk.ticket.dtos.ReqTicketDto;
import jakarta.transaction.Transactional;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @Transactional
    public ResDepartmentDto createDepartment(ReqDepartmentDto reqDepartmentDto) {
        Department department = departmentMapper.toEntity(reqDepartmentDto);
        final Department savedDepartment = departmentRepository.save(department);
        return departmentMapper.toRes(savedDepartment);
    }

    @Transactional
    public ResDepartmentDto getDepartmentById(Long id) {
        final Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", id));
        return departmentMapper.toRes(department);
    }

    @Transactional
    public List<ResDepartmentDto> getAllDepartment(){
        List<Department> departmentList = departmentRepository.findAll();
        return departmentList.stream()
                .map(departmentMapper::toRes)
                .toList();
    }

    @Transactional
    public ResDepartmentDto updateDepartment(Long id, ReqDepartmentDto reqDepartmentDto){
         Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("department", "id", id));

         departmentMapper.updateEntityFromDepartmentDto(reqDepartmentDto, existingDepartment);

         //save update department
        Department updatedDepartment = departmentRepository.save(existingDepartment);
        return departmentMapper.toRes(updatedDepartment);
    }

    @Transactional
    public boolean deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("department", "id", id));
        departmentRepository.delete(department);
        return true;
    }
}
