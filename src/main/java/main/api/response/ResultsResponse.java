package main.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ResultsResponse {
    boolean result;
    Map<String,String> errors;
    String stringResult;
    int intResult;
}
