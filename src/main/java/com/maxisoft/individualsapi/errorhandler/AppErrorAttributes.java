package com.maxisoft.individualsapi.errorhandler;

import com.maxisoft.individualsapi.exception.ApiException;
import com.maxisoft.individualsapi.exception.AuthException;
import com.maxisoft.individualsapi.exception.InvalidCredentialsException;
import com.maxisoft.individualsapi.exception.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.security.SignatureException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.maxisoft.individualsapi.errorhandler.Errors.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
public class AppErrorAttributes extends DefaultErrorAttributes {
    private HttpStatus status = INTERNAL_SERVER_ERROR;

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> attr = super.getErrorAttributes(request, options); //ErrorAttributeOptions.defaults()

        var error = getError(request);
        var errorList = new ArrayList<Map<String, Object>>();

        if (error instanceof InvalidCredentialsException) {
            status = HttpStatus.UNAUTHORIZED;
            var message = error.getMessage();
            if (message == null)
                message = error.getClass().getName();

            var errorMap = new LinkedHashMap<String, Object>();
            errorMap.put("code", INVALID_CREDENTIALS);
            errorMap.put("message", message);
            errorList.add(errorMap);
        } else if (error instanceof AuthException || error instanceof UnauthorizedException) {
            status = HttpStatus.UNAUTHORIZED;
            var message = error.getMessage();
            if (message == null)
                message = error.getClass().getName();

            var errorMap = new LinkedHashMap<String, Object>();
            errorMap.put("code", UNAUTHORIZED);
            errorMap.put("message", message);
            errorList.add(errorMap);
        } else if (error instanceof ApiException ex) {
            status = HttpStatus.BAD_REQUEST;
            var errorMap = new LinkedHashMap<String, Object>();
            errorMap.put("code", ex.getErrorCode());
            errorMap.put("message", error.getMessage());
            errorList.add(errorMap);
        } else if (error instanceof ExpiredJwtException) {
            status = HttpStatus.UNAUTHORIZED;
            var message = error.getMessage();
            if (message == null)
                message = error.getClass().getName();

            var errorMap = new LinkedHashMap<String, Object>();
            errorMap.put("code", JWT_EXPIRED);
            errorMap.put("message", message);
            errorList.add(errorMap);
        } else if (error instanceof SignatureException || error instanceof MalformedJwtException) {
            status = HttpStatus.UNAUTHORIZED;
            var message = error.getMessage();
            if (message == null)
                message = error.getClass().getName();

            var errorMap = new LinkedHashMap<String, Object>();
            errorMap.put("code", INVALID_JWT);
            errorMap.put("message", message);
            errorList.add(errorMap);
        } else if (error instanceof WebClientException) {
            status = error instanceof WebClientResponseException responseException ?
                    (HttpStatus) responseException.getStatusCode() : status;

            var message = error.getMessage();
            if (message == null)
                message = error.getClass().getName();

            var errorMap = new LinkedHashMap<String, Object>();
            errorMap.put("code", UNKNOWN);
            errorMap.put("message", message);
            errorList.add(errorMap);
        } else {
            status = INTERNAL_SERVER_ERROR;
            var message = error.getMessage();
            if (message == null)
                message = error.getClass().getName();

            var errorMap = new LinkedHashMap<String, Object>();
            errorMap.put("code", INTERNAL_ERROR);
            errorMap.put("message", message);
            errorList.add(errorMap);
        }

        attr.put("status", status.value());
        attr.put("errors", errorList);

        return attr;
    }
}
