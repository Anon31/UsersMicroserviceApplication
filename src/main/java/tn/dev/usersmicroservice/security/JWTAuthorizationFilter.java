package tn.dev.usersmicroservice.security;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Extract the JWT token from the request header
        String jwt = request.getHeader("Authorization");
        if (jwt == null ||  !jwt.startsWith(SecretParams.PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Validate the JWT token and set the authentication in the security context
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecretParams.SECRET)).build();
        // Remove "Bearer " prefix
        jwt = jwt.substring(SecretParams.PREFIX.length());
        // Verify the token
        DecodedJWT decodedJWT = verifier.verify(jwt);
        String username = decodedJWT.getSubject();
        // Get the roles from the token
        List<String> roles = decodedJWT.getClaims().get("roles").asList(String.class);
        // Create a collection of granted authorities
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // Add the roles to the authorities
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        // Create an authentication token
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username, null, authorities);
        // Set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(user);
        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
