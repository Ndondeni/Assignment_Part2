/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment_1.Part2;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author Student
 */
public class Message {
    private static int counter = 0;
    private int messageId;
    private String recipient;
    private String content;
    private String messageHash;
    private String timestamp;
    
    public Message(String recipient, String content) {
        this.messageId = ++counter;
        this.recipient = recipient;
        this.content = content;
        this.messageHash = generateHash();
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    private String generateHash() {
        String idStr = String.valueOf(messageId);
        String firstTwo = idStr.length() >= 2 ? idStr.substring(0, 2) : idStr;
        String[] words = content.trim().split("\\s+");
        String first = words.length > 0 ? words[0] : "";
        String last = words.length > 1 ? words[words.length - 1] : first;
        return firstTwo + ":" + messageId + ":" + (first + last).toUpperCase();
    }
    
    // Getters
    public int getMessageId() { return messageId; }
    public String getRecipient() { return recipient; }
    public String getContent() { return content; }
    public String getMessageHash() { return messageHash; }
    public String getTimestamp() { return timestamp; }
}