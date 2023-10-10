package com.cloud.cloudstorage.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class JwtResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -3418503142215704031L;

    @JsonProperty("auth-token")
    private String authToken;

}
