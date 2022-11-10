package Beans;

/**
 * This Bean is used to store feedback information for user inputs
 * Note that Entities are also used as Beans in this application
 * @author Tamati Rudd 18045626
 */
public class UserFeedback {
    private boolean success;
    private String message;

    //Constructors
    public UserFeedback() {
        this.success = false;
        this.message = "";
    }
    public UserFeedback(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    //Getters & Setters for Bean Attributes
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
