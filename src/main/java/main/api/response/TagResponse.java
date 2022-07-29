package main.api.response;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
public class TagResponse {
    private String name;
    private double weight;

    public TagResponse(String name, double weight) {
        this.name = name;
        this.weight = roundNumber(weight);
    }
    private double roundNumber(double number){
        return Math.round(number);
    }
}