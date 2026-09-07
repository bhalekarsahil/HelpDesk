package com.example.HelpDesk.ticket.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class  ReqTicketDto {
    public enum TicketPriority {
        LOW,
        MEDIUM,
        HIGH
    }

    @NotBlank(message = "Title is required")
    @Size(min = 4, max = 80, message = "Title must be between 4 and 80 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Priority is required and must be LOW, MEDIUM, or HIGH")
    private TicketPriority priority;

    @NotNull(message = "Creator user ID is required")
    private Long createdById;

    // Optional on create
    private Long assignedToId;

    public ReqTicketDto() {}

    public ReqTicketDto(String title, String description, String category, TicketPriority priority, Long createdById, Long assignedToId) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.createdById = createdById;
        this.assignedToId = assignedToId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }
}