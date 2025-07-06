package TennisMatchScoreboard.filter;


import TennisMatchScoreboard.dto.ErrorResponseDto;
import TennisMatchScoreboard.exceptions.DataBaseException;
import TennisMatchScoreboard.exceptions.InvalidParameterException;
import TennisMatchScoreboard.exceptions.MatchProcessingException;
import TennisMatchScoreboard.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.*;


@WebFilter("/")
public class ExceptionHandlingFilter extends HttpFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        try{
            chain.doFilter(req, res);
        }
        catch (InvalidParameterException | MatchProcessingException e){
            writeErrorResponse(res, SC_BAD_REQUEST , e);
        }
        catch (DataBaseException e){
            writeErrorResponse(res, SC_INTERNAL_SERVER_ERROR , e);
        }
        catch (NotFoundException e){
            writeErrorResponse(res, SC_NOT_FOUND, e);
        }
    }

    private void writeErrorResponse(HttpServletResponse response, int errorCode, RuntimeException e) throws IOException {
        response.setStatus(errorCode);

        objectMapper.writeValue(response.getWriter(), new ErrorResponseDto(
                errorCode,
                e.getMessage()
        ));
    }

}
