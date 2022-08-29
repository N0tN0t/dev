package main.requests;

import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
public class ProfileRequest {
    private File photo;
    private String name;
    private String email;
    private String password;
    private int removePhoto;
}
