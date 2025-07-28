package com.enotes_api.security;

import com.enotes_api.messages.ExceptionMessages;
import com.enotes_api.response.ResponseUtils;
import com.enotes_api.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

    private JwtService jwtService;

    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String tokenFromHeader = null;
        String userEmailFromToken = null;
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            tokenFromHeader = authorizationHeader.substring(7);
            try {
                userEmailFromToken = jwtService.extractUserEmailFromToken(tokenFromHeader);
            } catch (SignatureException ex) {
                sendErrorResponse(response, ExceptionMessages.INVALID_JWT_SIGNATURE_MESSAGE);
                return;
            } catch (ExpiredJwtException ex) {
                sendErrorResponse(response, ExceptionMessages.JWT_TOKEN_EXPIRED_MESSAGE);
                return;
            }
        }
        if (StringUtils.hasText(userEmailFromToken) && ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication())) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmailFromToken);
            Boolean isTokenValid = jwtService.validateToken(tokenFromHeader, userDetails);
            if (isTokenValid) {
                UsernamePasswordAuthenticationToken userNamePasswordAuthentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                userNamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userNamePasswordAuthentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ResponseEntity<?> jwtErrorResponse = ResponseUtils.createFailureResponseWithMessage(HttpStatus.UNAUTHORIZED,
                message);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonErrorResponse = objectMapper.writeValueAsString(jwtErrorResponse.getBody());

        response.getWriter().write(jsonErrorResponse);
    }

}
