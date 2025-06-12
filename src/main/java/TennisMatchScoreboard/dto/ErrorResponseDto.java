package TennisMatchScoreboard.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO УБЕРИ ЭТУ АННОТАЦИЮ
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseDto {

    private Integer errorCode;
    private String message;
}
