package io.bhimsur.cf14seabe.util;

import io.bhimsur.cf14seabe.config.JwtUtil;
import io.bhimsur.cf14seabe.dto.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class MetadataUtil {

    @Autowired
    private JwtUtil jwtUtil;

    public Metadata constructMetadata(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        token = token.replace("Bearer ", "");
        return Metadata.builder().ipAddress(Objects.equals(request.getHeader("X-Forwarded-For"), "") ? request.getRemoteAddr() : request.getHeader("X-Forwarded-For")).userAgent(request.getHeader("User-Agent")).userId(Integer.parseInt(jwtUtil.getUserIdFromToken(token))).build();
    }

}
