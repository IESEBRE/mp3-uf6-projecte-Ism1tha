import com.insebre.project.controller.ExceptionController;
import com.insebre.project.exception.CustomException;

public class Main {

    public static void main(String[] args) {
        // Create an instance of the ExceptionController
        ExceptionController exceptionController = new ExceptionController();

        try {
            // Call the method that may throw the custom exception
            exceptionController.throwCustomException();
        } catch (CustomException e) {
            // Handle the custom exception
            System.out.println("Caught custom exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}