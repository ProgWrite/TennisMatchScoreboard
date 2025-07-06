package TennisMatchScoreboard.dto;

import lombok.*;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ErrorResponseDto {

    private Integer errorCode;
    private String message;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponseDto that = (ErrorResponseDto) o;
        return Objects.equals(errorCode, that.errorCode) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode, message);
    }


    @Override
    public String toString() {
        return "ErrorResponseDto{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }
}
