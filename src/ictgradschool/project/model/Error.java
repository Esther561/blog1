package ictgradschool.project.model;

import java.io.Serializable;

public class Error implements Serializable {
    private String message;

    public Error(String message) {
        this.message = message;
    }

    public Error() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Error{" +
                "message='" + message + '\'' +
                '}';
    }
}
