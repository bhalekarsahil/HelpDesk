package com.example.HelpDesk.department.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ReqDepartmentDto {
    @NotBlank(message = "departmentName cannot be empty")
    @Size(min = 4, max = 80, message = "department name max up to 80 characters")
    private String name;
    public ReqDepartmentDto(){}
    public ReqDepartmentDto(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
