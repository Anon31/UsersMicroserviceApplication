package tn.dev.usersmicroservice.security;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tn.dev.usersmicroservice.entities.User;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super();
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        User user = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
        } catch (JsonParseException e) {
            throw new RuntimeException("Error parsing JSON: " + e.getMessage());
        } catch (JsonMappingException e) {
            throw new RuntimeException("Error mapping JSON: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON: " + e.getMessage());
        }
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        List<String> roles = new ArrayList<>();

        springUser.getAuthorities().forEach(authority -> {
            roles.add(authority.getAuthority());
        });

        // Generate JWT token
        String jwtToken = JWT.create()
            .withSubject(springUser.getUsername())
            .withArrayClaim("roles", roles.toArray(new String[roles.size()]))
            .withExpiresAt(new Date(System.currentTimeMillis() + SecretParams.EXP_TIME))
            .sign(Algorithm.HMAC256(SecretParams.SECRET));

        // Add token to response header
        response.addHeader("Authorization", jwtToken);
    }
}
