package com.cloud.cloudstorage.models;

import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

@Data
public class JwtRequest implements Serializable {

    @Serial
    private String login;
    private String password;

}
