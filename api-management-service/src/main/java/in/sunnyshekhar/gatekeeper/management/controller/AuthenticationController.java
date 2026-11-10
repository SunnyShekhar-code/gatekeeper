package in.sunnyshekhar.gatekeeper.management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sunnyshekhar.gatekeeper.management.dto.requestDto.LoginRequest;
import in.sunnyshekhar.gatekeeper.management.dto.responseDto.LoginResponse;
import in.sunnyshekhar.gatekeeper.management.service.AuthenticationService;
import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/api/auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService=authenticationService;
    }

    @PostMapping ("/login")
    public ResponseEntity<LoginResponse> login(@Valid  @RequestBody LoginRequest request){
        return ResponseEntity.ok(authenticationService.login(request));
    }
    
}
