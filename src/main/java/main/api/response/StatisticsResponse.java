package main.api.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
public class StatisticsResponse {
    private int postsCount = 0;
    private int likesCount = 0;
    private int dislikesCount = 0;
    private int viewsCount = 0;
    private long firstPublication = 0;
}
