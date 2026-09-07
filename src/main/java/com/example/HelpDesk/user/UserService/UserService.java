package com.example.HelpDesk.user.UserService;
import com.example.HelpDesk.department.DepartmentRepository.DepartmentRepository;
import com.example.HelpDesk.department.entity.Department;
import com.example.HelpDesk.exception.ResourceNotFoundException;
import com.example.HelpDesk.user.UserRepository.UserRepository;
import com.example.HelpDesk.user.dtos.ReqUserDto;
import com.example.HelpDesk.user.dtos.ResUserDto;
import com.example.HelpDesk.user.entity.User;
import com.example.HelpDesk.user.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private DepartmentRepository departmentRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper, DepartmentRepository departmentRepository){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public ResUserDto createUser(ReqUserDto reqUserDto){
        User user = userMapper.toEntity(reqUserDto);

        if(reqUserDto.getDepartmentId() != null){
            Long departmentId = reqUserDto.getDepartmentId();
            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(()-> new ResourceNotFoundException("Department", "id", departmentId));

            user.setDepartment(department);
        }
        User savedUser = userRepository.save(user);
        return userMapper.toRes(savedUser);
    }

    @Transactional
    public ResUserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toRes)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Transactional
    public List<ResUserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toRes)
                .toList();
    }

    @Transactional
    public ResUserDto updateUser(Long id, ReqUserDto reqUserDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        userMapper.updateEntityFromUserDto(reqUserDto, existingUser);

        if (reqUserDto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(reqUserDto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department", "id", reqUserDto.getDepartmentId()));
            existingUser.setDepartment(department);
        }

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toRes(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(existingUser);
    }
}
