package com.example.HelpDesk.user.UserController;

import com.example.HelpDesk.common.ApiResponse;
import com.example.HelpDesk.user.UserService.UserService;
import com.example.HelpDesk.user.dtos.ReqUserDto;
import com.example.HelpDesk.user.dtos.ResUserDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class userController {
    private final UserService userService;

    public userController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ResUserDto>> createUser(@Valid @RequestBody ReqUserDto user) {
        ResUserDto response = userService.createUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("User Created Successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResUserDto>>> getAllUsers(){
        List<ResUserDto> response = userService.getAllUsers();
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(ApiResponse.success("Users fetch Successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResUserDto>> getUser(@PathVariable Long id){
        ResUserDto response = userService.getUserById(id);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(ApiResponse.success("User fetch Successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ResUserDto>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody ReqUserDto user
    ){
        ResUserDto response = userService.updateUser(id, user);
        return ResponseEntity
                .status((HttpStatus.OK))
                .body(ApiResponse.success("User Update Successfully", response));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<ResUserDto>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("User deleted successfully", null));
    }
}
