package net.exampleproject.ems.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse {

    private String message;
    private Object data;
    private boolean success;
    private String status;
}
