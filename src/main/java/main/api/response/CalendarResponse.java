package main.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class CalendarResponse {
    private Integer[] years;
    private Map<String, Integer> posts;
}
