package com.example.HelpDesk.department.dtos;

public class ResDepartmentDto {
    private Long id;
    private String name;
    public ResDepartmentDto() {}
    public ResDepartmentDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
