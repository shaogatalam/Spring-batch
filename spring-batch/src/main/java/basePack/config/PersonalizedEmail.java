package basePack.config;
public class PersonalizedEmail {
    private String recipientEmail;
    private String emailContent;

    public PersonalizedEmail(String recipientEmail, String emailContent) {
        this.recipientEmail = recipientEmail;
        this.emailContent = emailContent;
    }

    // Getters and setters
    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    @Override
    public String toString() {
        return "To: " + recipientEmail + "\nContent:\n" + emailContent;
    }
}

