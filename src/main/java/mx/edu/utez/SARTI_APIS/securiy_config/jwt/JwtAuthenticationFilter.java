package mx.edu.utez.SARTI_APIS.securiy_config.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import mx.edu.utez.SARTI_APIS.securiy_config.service.UserDetailsServiceImpl;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserDetailsServiceImpl service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try{
            String token = jwtProvider.resolveToken(request);
            if (token == null){
                filterChain.doFilter(request, response);
                return;
            }
            Claims claims = jwtProvider.resolveClaims(request);
            if (claims != null && jwtProvider.validateClaims(claims, token)){
                String username = claims.getSubject();
                UserDetails user = service.loadUserByUsername(username);
                Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);

                filterChain.doFilter(request, response);
            }
        }catch (Exception e){
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

}
