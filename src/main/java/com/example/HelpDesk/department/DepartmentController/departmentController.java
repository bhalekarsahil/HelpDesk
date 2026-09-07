package com.example.HelpDesk.department.DepartmentController;

import com.example.HelpDesk.common.ApiResponse;
import com.example.HelpDesk.department.DepartmentService.DepartmentService;
import com.example.HelpDesk.department.dtos.ReqDepartmentDto;
import com.example.HelpDesk.department.dtos.ResDepartmentDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/departments")
public class departmentController {
    private final DepartmentService departmentService;

    public departmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ResDepartmentDto>> createDepartment(@Valid @RequestBody ReqDepartmentDto reqDepartmentDto){
        ResDepartmentDto response = departmentService.createDepartment(reqDepartmentDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Department created successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResDepartmentDto>> getDepartmentById(@PathVariable Long id){
        ResDepartmentDto response = departmentService.getDepartmentById(id);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(ApiResponse.success("Department Fetch Successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResDepartmentDto>>> getAllDepatments() {
        List<ResDepartmentDto> response = departmentService.getAllDepartment();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Departments fetched successfully", response  ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ResDepartmentDto>> updateDepartments(@PathVariable Long id,@Valid @RequestBody ReqDepartmentDto reqDepartmentDto) {
        ResDepartmentDto response = departmentService.updateDepartment(id, reqDepartmentDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Department updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDepartment(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("Department Delete Successfully",null));
    }
}
