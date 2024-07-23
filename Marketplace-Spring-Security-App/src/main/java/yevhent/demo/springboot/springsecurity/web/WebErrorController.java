package yevhent.demo.springboot.springsecurity.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class WebErrorController implements ErrorController {

    @GetMapping
    public String getErrorPage(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            HttpStatus httpStatus = HttpStatus.resolve(statusCode);
            String text = getString(httpStatus);
            model.addAttribute("errorText", text);
            model.addAttribute("errorCode", statusCode);
        } else {
            model.addAttribute("errorText", "An unknown error has occurred");
            model.addAttribute("errorCode", "Unknown");
        }
        return "error";
    }

    private String getString(HttpStatus httpStatus) {

        switch (httpStatus) {
            case NOT_FOUND:
                return "the page you are looking for cannot be found";
            case UNAUTHORIZED:
                return "you are not authorized to the page you are requesting";
            case FORBIDDEN:
                return "you don't have permissions for this page";
            default:
                return "something went wrong";
        }
    }
}