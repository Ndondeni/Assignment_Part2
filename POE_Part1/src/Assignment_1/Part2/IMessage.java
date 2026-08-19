/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment_1.Part2;
import javax.swing.JOptionPane;
import java.util.List;
/**
 *
 * @author Student
 */



public class IMessage {
    private static MessageStorage storage = new MessageStorage();
    
    public static void main(String[] args) {
        showMenu();
    }
    
    public static void showMenu() {
        String[] options = {"Send Message", "View Messages", "Delete Message", "Delete All", "Exit"};
        
        int choice = JOptionPane.showOptionDialog(
            null,
            "📱 Welcome to Imessage!\nWhat do you want to do?",
            "Main Menu",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        switch (choice) {
            case 0: sendMessage(); break;
            case 1: viewMessages(); break;
            case 2: deleteMessage(); break;
            case 3: deleteAll(); break;
            default: 
                storage.saveMessages();
                System.exit(0);
        }
    }
    public  void MessageHome() {
        String[] options = {"Send Message", "View Messages", "Delete Message", "Delete All", "Exit"};
        
        int choice = JOptionPane.showOptionDialog(
            null,
            "📱 Welcome to Imessage!\nWhat do you want to do?",
            "Main Menu",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        switch (choice) {
            case 0: sendMessage(); break;
            case 1: viewMessages(); break;
            case 2: deleteMessage(); break;
            case 3: deleteAll(); break;
            default: 
                storage.saveMessages();
                System.exit(0);
        }
    }
    
    public static void sendMessage() {
        String recipient = JOptionPane.showInputDialog("Enter recipient name:");
        if (recipient == null) { showMenu(); return; }
        
        String content = JOptionPane.showInputDialog("Enter message (max 250 chars):");
        if (content == null) { showMenu(); return; }
        
        if (content.length() > 250) {
            JOptionPane.showMessageDialog(null, "Message too long! Max 250 characters.", "Error", JOptionPane.ERROR_MESSAGE);
            sendMessage();
            return;
        }
        
        Message msg = new Message(recipient, content);
        storage.addMessage(msg);
        
        JOptionPane.showMessageDialog(null, 
            "✅ Message sent!\n\n" +
            "Hash: " + msg.getMessageHash() + "\n" +
            "To: " + recipient + "\n" +
            "Time: " + msg.getTimestamp(),
            "Success", JOptionPane.INFORMATION_MESSAGE);
        
        showMenu();
    }
    
    public static void viewMessages() {
        List<Message> messages = storage.getMessages();
        
        if (messages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "📭 No messages found.", "View", JOptionPane.INFORMATION_MESSAGE);
            showMenu();
            return;
        }
        
        StringBuilder display = new StringBuilder("📬 Your Messages:\n\n");
        for (Message m : messages) {
            display.append("ID: ").append(m.getMessageId())
                   .append("\nTo: ").append(m.getRecipient())
                   .append("\nHash: ").append(m.getMessageHash())
                   .append("\nMessage: ").append(m.getContent())
                   .append("\nTime: ").append(m.getTimestamp())
                   .append("\n" + "─".repeat(40) + "\n\n");
        }
        
        JOptionPane.showMessageDialog(null, display.toString(), "Messages", JOptionPane.INFORMATION_MESSAGE);
        showMenu();
    }
    
    public static void deleteMessage() {
        String idStr = JOptionPane.showInputDialog("Enter message ID to delete:");
        if (idStr == null) { showMenu(); return; }
        
        try {
            int id = Integer.parseInt(idStr);
            if (storage.deleteById(id)) {
                JOptionPane.showMessageDialog(null, "🗑 Message deleted.", "Done", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "❌ Message not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "❌ Invalid ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        showMenu();
    }
    
    public static void deleteAll() {
        int confirm = JOptionPane.showConfirmDialog(
            null,
            "⚠️ Delete ALL messages?",
            "Confirm",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            storage.deleteAll();
            JOptionPane.showMessageDialog(null, "🗑 All messages deleted.", "Done", JOptionPane.INFORMATION_MESSAGE);
        }
        showMenu();
    }
}