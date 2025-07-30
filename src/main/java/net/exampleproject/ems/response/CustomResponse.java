package net.exampleproject.ems.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse<T> {

    private String message;
    private T data;
    private boolean success;
    private String status;
}
