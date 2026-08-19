/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment_1.Part2;

/**
 *
 * @author Student
 */
import java.io.*;
import java.util.*;

public class MessageStorage {
    private static final String FILE_NAME = "messages.json";
    private List<Message> messages;
    
    public MessageStorage() {
        messages = loadMessages();
    }
    
    // Save messages to JSON file
    public void saveMessages() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write("[\n");
            for (int i = 0; i < messages.size(); i++) {
                Message m = messages.get(i);
                writer.write("  {\n");
                writer.write("    \"id\": " + m.getMessageId() + ",\n");
                writer.write("    \"recipient\": \"" + escape(m.getRecipient()) + "\",\n");
                writer.write("    \"content\": \"" + escape(m.getContent()) + "\",\n");
                writer.write("    \"hash\": \"" + escape(m.getMessageHash()) + "\",\n");
                writer.write("    \"timestamp\": \"" + escape(m.getTimestamp()) + "\"\n");
                writer.write("  }" + (i < messages.size() - 1 ? "," : ""));
                writer.write("\n");
            }
            writer.write("]");
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }
    
    // Load messages from JSON file
    private List<Message> loadMessages() {
        List<Message> loaded = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return loaded;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            
            String content = json.toString();
            if (content.trim().isEmpty() || content.equals("[]")) return loaded;
            
            // Parse JSON manually (simple approach)
            String[] parts = content.split("\\{");
            for (String part : parts) {
                if (part.trim().isEmpty()) continue;
                String clean = part.replace("}", "").trim();
                if (clean.endsWith(",")) clean = clean.substring(0, clean.length() - 1);
                
                String idStr = extractValue(clean, "id");
                String recipient = extractValue(clean, "recipient");
                String messageContent = extractValue(clean, "content");
                String hash = extractValue(clean, "hash");
                String timestamp = extractValue(clean, "timestamp");
                
                if (idStr != null && !idStr.isEmpty()) {
                    Message msg = new Message(recipient, messageContent);
                    loaded.add(msg);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading: " + e.getMessage());
        }
        return loaded;
    }
    
    private String extractValue(String json, String key) {
        String search = "\"" + key + "\":";
        int start = json.indexOf(search);
        if (start == -1) return "";
        
        start += search.length();
        char firstChar = json.charAt(start);
        
        if (firstChar == '"') {
            start++;
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        } else {
            int end = start;
            while (end < json.length() && (Character.isDigit(json.charAt(end)) || json.charAt(end) == '-')) {
                end++;
            }
            return json.substring(start, end);
        }
    }
    
    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");
    }
    
    public void addMessage(Message msg) {
        messages.add(msg);
        saveMessages();
    }
    
    public List<Message> getMessages() {
        return messages;
    }
    
    public void deleteAll() {
        messages.clear();
        saveMessages();
    }
    
    public boolean deleteById(int id) {
        boolean removed = messages.removeIf(m -> m.getMessageId() == id);
        if (removed) saveMessages();
        return removed;
    }
}