package in.sunnyshekhar.gatekeeper.management.service;

import java.time.Instant;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.sunnyshekhar.gatekeeper.management.dto.requestDto.LoginRequest;
import in.sunnyshekhar.gatekeeper.management.dto.responseDto.LoginResponse;
import in.sunnyshekhar.gatekeeper.management.entity.ManagementUser;
import in.sunnyshekhar.gatekeeper.management.entity.OrganisationStatus;
import in.sunnyshekhar.gatekeeper.management.exception.ApiException;
import in.sunnyshekhar.gatekeeper.management.repository.ManagementUserRepository;

@Service 
public class AuthenticationService {
    private final ManagementUserRepository managementUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final long expirationSeconds;

    public AuthenticationService(ManagementUserRepository managementUserRepository,
                PasswordEncoder passwordEncoder,JwtEncoder jwtEncoder,
                @Value ("${security.jwt.expiration-seconds}") long expirationSeconds
                ){
        this.managementUserRepository=managementUserRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtEncoder=jwtEncoder;
        this.expirationSeconds=expirationSeconds;
    }

    @Transactional (readOnly = true)
    public LoginResponse login(LoginRequest request){
        ManagementUser user= managementUserRepository.findByUsername(request.getUsername())
            .orElseThrow(()-> invalidCredentials());
        
        if(!user.isEnabled() || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash()) ){
            throw invalidCredentials();
        }

        if(user.getOrganisation() != null && user.getOrganisation().getStatus() != OrganisationStatus.ACTIVE){
            throw new ApiException(HttpStatus.FORBIDDEN,"The organisation is not active");
        }

        Instant issuedAt=Instant.now();
        String role=user.getRole().getName();
        Long organisationId= user.getOrganisation()==null? null: user.getOrganisation().getId();


        JwtClaimsSet.Builder claims= JwtClaimsSet.builder()
                                     .issuer("gatekeeper-api-management")
                                     .issuedAt(issuedAt)
                                     .expiresAt(issuedAt.plusSeconds(expirationSeconds))
                                     .subject(user.getUsername())
                                     .claim("role",role);
        
        if(organisationId != null){
            claims.claim("organisationId", organisationId);
        }

        JwsHeader header= JwsHeader.with(MacAlgorithm.HS256).build();

        String token=jwtEncoder.encode(
            JwtEncoderParameters.from(header,claims.build())).getTokenValue();

            return new LoginResponse(token, "Bearer", expirationSeconds,role, organisationId);
    }

    private RuntimeException invalidCredentials(){
        return new ApiException(HttpStatus.UNAUTHORIZED,"Invalid username or password");
    }
    
}
