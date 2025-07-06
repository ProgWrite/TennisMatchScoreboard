package TennisMatchScoreboard.dto;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PaginationDto<T> {
    private List<T> matches;
    private int currentPage;
    private int totalPages;
    private int totalMatches;
}
