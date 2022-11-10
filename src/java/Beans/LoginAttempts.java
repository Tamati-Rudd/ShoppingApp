package Beans;

/**
 * This Bean tracks the number of failed login attempts in a session
 * @author Tamati Rudd 18045626
 */
public class LoginAttempts {
    private int attempts;
    private int allowed;
    
    public LoginAttempts() {
        attempts = 0;
        allowed = 4;
    }
    
    public int getAttempts() {
        return attempts;
    }
    
    public void setAttempts(int newAttempts) {
        attempts = newAttempts;
    }
    
    public int getAllowed() {
        return allowed;
    }
    
    public void setAllowed(int newAttempts) {
        allowed = newAttempts;
    }
}
