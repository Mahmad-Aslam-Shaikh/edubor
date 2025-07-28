package com.enotes_api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommonResponseFormat {

    private String status;

    private String message;

    private HttpStatus httpStatus;

    private Object data;

    public ResponseEntity<?> create() {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", status);
        responseMap.put("message", message);

        if (!ObjectUtils.isEmpty(data))
            responseMap.put("data", data);

        return new ResponseEntity<>(responseMap, httpStatus);
    }
}
