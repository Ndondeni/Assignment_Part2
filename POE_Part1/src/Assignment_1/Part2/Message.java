package Assignment_1.Part2;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Message {
  
    public static List<Message> allMessages = new ArrayList<>();
    public static int totalMessagesSent = 0;

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String status;

    // Ctor
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateID();
        this.messageHash = createMessageHash();
    }

    
    private String generateID() {
        long id = (long) (Math.random() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }

   
    public void setMessageID(String messageID) {
        this.messageID = messageID;
        this.messageHash = createMessageHash(); // Recalculate hash if ID changes
    }

    // 1. Ensures Message ID is not more than 10 characters
    public boolean checkMessageID() {
        return this.messageID.length() <= 10;
    }

    // 2. Ensures recipient cell number is no more than 10 characters and starts with an international code (+)
    public String checkRecipientCell() {
        if (this.recipient.length() <= 10 && this.recipient.startsWith("+")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // 3. Creates the Message Hash: First 2 digits of ID, ":", Message Number, ":", First and Last words in CAPS
    public String createMessageHash() {
        if (this.messageText == null || this.messageText.trim().isEmpty()) {
            return "";
        }
        String[] words = this.messageText.trim().split("\\s+");
        // Remove punctuation from first and last words
        String firstWord = words[0].replaceAll("[^a-zA-Z]", "").toUpperCase();
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "").toUpperCase();
        
        String idPrefix = this.messageID.substring(0, 2);
        return idPrefix + ":" + this.messageNumber + ":" + firstWord + lastWord;
    }

    // 4. Checks message length (max 250 characters)
    public String checkMessageLength() {
        if (this.messageText.length() > 250) {
            int excess = this.messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        } else {
            return "Message ready to send.";
        }
    }

    // 5. Handles the Send, Disregard, or Store actions
    public String sentMessage(String action) {
        this.status = action;
        if (action.equalsIgnoreCase("Send")) {
            totalMessagesSent++;
            allMessages.add(this);
            return "Message successfully sent.";
        } else if (action.equalsIgnoreCase("Disregard")) {
            return "Press 0 to delete the message.";
        } else if (action.equalsIgnoreCase("Store")) {
            storeMessage();
            return "Message successfully stored.";
        }
        return "Invalid action.";
    }

    // 6. Returns all messages sent while the program is running
    public String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (Message m : allMessages) {
            sb.append("Message ID: ").append(m.messageID).append("\n");
            sb.append("Message Hash: ").append(m.messageHash).append("\n");
            sb.append("Recipient: ").append(m.recipient).append("\n");
            sb.append("Message: ").append(m.messageText).append("\n\n");
        }
        return sb.toString();
    }

    // 7. Returns the total number of messages sent
    public int returnTotalMessagess() { // Kept spelling as per prompt
        return totalMessagesSent;
    }

    // 8. Stores the message in a JSON file
    public void storeMessage() {
        String json = "{\n" +
                "  \"messageID\": \"" + this.messageID + "\",\n" +
                "  \"messageNumber\": " + this.messageNumber + ",\n" +
                "  \"recipient\": \"" + this.recipient + "\",\n" +
                "  \"messageText\": \"" + this.messageText + "\",\n" +
                "  \"messageHash\": \"" + this.messageHash + "\"\n" +
                "}";
        try (FileWriter file = new FileWriter("messages.json", true)) { // Append mode
            file.write(json + ",\n");
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    // Getters for testing and display
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
}