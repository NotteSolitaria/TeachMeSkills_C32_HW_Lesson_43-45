package com.teachmeskills.hw.lesson_39_42.controller;

import com.teachmeskills.hw.lesson_39_42.model.Security;
import com.teachmeskills.hw.lesson_39_42.model.User;
import com.teachmeskills.hw.lesson_39_42.model.dto.RegistrationRequestDto;
import com.teachmeskills.hw.lesson_39_42.service.SecurityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/security")
public class SecurityController {

    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid RegistrationRequestDto requestDto,
                                          BindingResult bindingResult) throws SQLException {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(e -> e.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(errors);
        }

        Optional<User> resultUser = securityService.registration(requestDto);
        if (resultUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(resultUser.get());
        }
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("User registration failed: user may already exist");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSecurityById(@PathVariable Long id) {
        Optional<Security> security = securityService.getSecurityById(id);
        return security.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Security record not found"));
    }

    @PostMapping
    public ResponseEntity<?> createSecurity(@RequestBody @Valid Security security,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(e -> e.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(errors);
        }
        Optional<Security> createdSecurity = securityService.createSecurity(security);
        if (createdSecurity.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSecurity.get());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to create security record");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSecurity(@PathVariable Long id,
                                            @RequestBody @Valid Security security,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(e -> e.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(errors);
        }
        security.setId(id);
        Optional<Security> updatedSecurity = securityService.updateSecurity(security);
        if (updatedSecurity.isPresent()) {
            return ResponseEntity.ok(updatedSecurity.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Failed to update security record: record not found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSecurity(@PathVariable Long id) {
        boolean isDeleted = securityService.deleteSecurity(id);
        if (isDeleted) {
            return ResponseEntity.ok("Security record deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Failed to delete security record: record not found");
    }
}
