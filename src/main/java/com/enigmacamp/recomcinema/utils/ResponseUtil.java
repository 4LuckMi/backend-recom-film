package com.enigmacamp.recomcinema.utils;

import com.enigmacamp.recomcinema.model.response.ApiResponse;
import com.enigmacamp.recomcinema.model.response.ErrorResponse;
import com.enigmacamp.recomcinema.model.response.PagingResponse;
import com.enigmacamp.recomcinema.model.response.StatusResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseUtil {
    // build a single response object
    public static <T> ResponseEntity<ApiResponse<T>> buildSingleResponse(
            HttpStatus httpdStatus,
            String message,
            T data) {
        // status reponse
        StatusResponse statusResponse = StatusResponse.builder()
                .statusCode(httpdStatus.value())
                .message(httpdStatus.getReasonPhrase() + " : " + message)
                .build();
        ApiResponse<T> response = ApiResponse.<T>builder()
                .status(statusResponse)
                .data(data)
                .build();
        return ResponseEntity.status(httpdStatus).body(response);
    }

    // build paging response object
    public static <T> ResponseEntity<ApiResponse<List<T>>> buildPageResponse(
            HttpStatus httpdStatus,
            String message,
            Page<T> page) {
        // status reponse
        StatusResponse statusResponse = StatusResponse.builder()
                .statusCode(httpdStatus.value())
                .message(message)
                .build();
        // paging
        PagingResponse paging = PagingResponse.builder()
                .page(page.getNumber() + 1)
                .rowsPerPage(page.getSize())
                .totalRows((int) page.getTotalElements())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
        ApiResponse<List<T>> response = ApiResponse.<List<T>>builder()
                .status(statusResponse)
                .data(page.getContent())
                .paging(paging)
                .build();
        return ResponseEntity.status(httpdStatus).body(response);
    }

    // build error response
    public static ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus httpStatus,
            String message,
            List<String> errors) {

        StatusResponse status = StatusResponse.builder()
                .statusCode(httpStatus.value())
                .message(message)
                .build();
        // Data
        ErrorResponse response = ErrorResponse.builder()
                .status(status)
                .message(errors)
                .build();

        // return
        return ResponseEntity.status(httpStatus).body(response);
    }
}
