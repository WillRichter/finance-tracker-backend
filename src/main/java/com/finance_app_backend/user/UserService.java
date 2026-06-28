package com.finance_app_backend.user;

import com.finance_app_backend.exceptions.UserNotFoundException;
import com.finance_app_backend.exceptions.UsernameTakenException;
import com.finance_app_backend.lib.Mapper;
import com.finance_app_backend.requests.CreateUserRequest;
import com.finance_app_backend.responses.ApiResponseWrapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Mapper mapper;

    /**
     * Retrieves the {@link User} by the given UUID
     *
     * @param id the UUID which identifies the {@link User} to be retrieved
     * @return a {@link User} object which was found
     */
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    /**
     * Creates a {@link User} by the given {@link CreateUserRequest} object
     *
     * @param request {@link CreateUserRequest} containing username, firstName,
     * lastName, and password
     * @return {@link ApiResponseWrapper} containing the {@link UserDTO} object which was created
     */
    public ApiResponseWrapper<UserDTO> createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameTakenException("Username taken");
        }

        User user = userRepository.save(new User(
                request.username(),
                bCryptPasswordEncoder.encode(request.password()),
                request.firstName(),
                request.lastName()
        ));

        return new ApiResponseWrapper<>(
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                "User created successfully",
                mapper.userToUserDTO(user)
        );
    }

    /**
     * Returns information on the currently logged-in user
     *
     * @param user {@link User} the logged-in user
     * @return {@link ApiResponseWrapper} containing the {@link UserDTO} object
     */
    public ApiResponseWrapper<UserDTO> getCurrentUser(User user) {
        UserDTO userDTO = mapper.userToUserDTO(user);

        return new ApiResponseWrapper<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Current user fetched successfully",
                userDTO
        );
    }

    /**
     * Deletes the currently logged-in user
     *
     * @param user the {@link User} object to be deleted
     * @return the {@link UserDTO} object of the deleted user
     */
    public ApiResponseWrapper<UserDTO> deleteUser(User user) {
        UserDTO userDTO = mapper.userToUserDTO(user);
        userRepository.delete(user);

        return new ApiResponseWrapper<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "User deleted successfully",
                userDTO
        );
    }
}