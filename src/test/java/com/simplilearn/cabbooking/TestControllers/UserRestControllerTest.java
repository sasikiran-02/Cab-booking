package com.simplilearn.cabbooking.TestControllers;

import com.simplilearn.cabbooking.RestControllers.UserRestController;
import com.simplilearn.cabbooking.model.User;
import com.simplilearn.cabbooking.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserRestControllerTest {

    @InjectMocks
    private UserRestController userRestController;

    @Mock
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(
                1L,
                "John Doe",
                "john.doe@example.com",
                "1234567890",
                "password123",
                "johndoe",
                LocalDateTime.now()
        );
    }

    @Test
    public void testSaveUser() {
        when(userService.saveUser(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userRestController.saveUser(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testSaveUser_BadRequest() {
        when(userService.saveUser(any(User.class))).thenThrow(new IllegalArgumentException());

        ResponseEntity<User> response = userRestController.saveUser(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetUserById_Success() {
        when(userService.findUserById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userRestController.getUserById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUserById_NotFound() {
        when(userService.findUserById(1L)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userRestController.getUserById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetAllUsers() {
        when(userService.findAllUsers()).thenReturn(List.of(user));

        ResponseEntity<List<User>> response = userRestController.getAllUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void testGetAllUsers_NoContent() {
        when(userService.findAllUsers()).thenReturn(List.of());

        ResponseEntity<List<User>> response = userRestController.getAllUsers();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    public void testUpdateUser_Success() {
        when(userService.findUserById(1L)).thenReturn(Optional.of(user)); // Mock finding user

        // Call the controller method
        ResponseEntity<User> response = userRestController.updateUser(1L, user);

        // Verify the status code is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the userService.updateUser was called with the correct user
        verify(userService, times(1)).updateUser(user);

        // Optionally verify that the returned response body is the same user
        assertEquals(user, response.getBody());
    }

    @Test
    public void testUpdateUser_NotFound() {
        when(userService.findUserById(1L)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userRestController.updateUser(1L, user);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteUser_Success() {
        when(userService.findUserById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<Void> response = userRestController.deleteUser(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteUser_NotFound() {
        when(userService.findUserById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = userRestController.deleteUser(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
