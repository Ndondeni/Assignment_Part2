package Assignment_1.Part2;
import java.util.Scanner;

public class QuickChat {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
       
        boolean isLoggedIn = true; 
        
        if (!isLoggedIn) {
            System.out.println("You must log in first.");
            return;
        }

        
        System.out.println("Welcome to QuickChat.");

        
        System.out.print("How many messages do you wish to enter? ");
        int numMessages = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        int messagesProcessed = 0;
        boolean quit = false;

        // Requirement 4: Application runs until user quits
        while (!quit && messagesProcessed < numMessages) {
            // Requirement 3: Numeric Menu
            System.out.println("\nMenu:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    if (messagesProcessed >= numMessages) {
                        System.out.println("You have reached the maximum number of messages.");
                        break;
                    }
                    
                    System.out.print("Enter recipient number (e.g., +27718693002): ");
                    String recipient = scanner.nextLine();
                    
                    System.out.print("Enter message: ");
                    String text = scanner.nextLine();

                  
                    Message tempMsg = new Message(messagesProcessed + 1, recipient, text);
                    
                    // Validate Recipient
                    String recipientCheck = tempMsg.checkRecipientCell();
                    if (!recipientCheck.equals("Cell phone number successfully captured.")) {
                        System.out.println(recipientCheck);
                        break; 
                    }
                    
                    // Validate Length
                    String lengthCheck = tempMsg.checkMessageLength();
                    if (!lengthCheck.equals("Message ready to send.")) {
                        System.out.println(lengthCheck);
                        break; 
                    }

                    // Asking user to Send, Disregard, or Store
                    System.out.println("Choose action: 1) Send, 2) Disregard, 3) Store");
                    int actionChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    
                    String action = "";
                    if (actionChoice == 1) action = "Send";
                    else if (actionChoice == 2) action = "Disregard";
                    else if (actionChoice == 3) action = "Store";

                    String result = tempMsg.sentMessage(action);
                    System.out.println(result);
               
                    if (action.equals("Send") || action.equals("Store")) {
                        System.out.println("\n--- Message Details ---");
                        System.out.println("Message ID: " + tempMsg.getMessageID());
                        System.out.println("Message Hash: " + tempMsg.getMessageHash());
                        System.out.println("Recipient: " + tempMsg.getRecipient());
                        System.out.println("Message: " + tempMsg.getMessageText());
                        System.out.println("-----------------------");
                    }
                    
                    messagesProcessed++;
                    break;

                case 2:
                   
                    System.out.println("Coming Soon.");
                    break;

                case 3:
                    quit = true;
                    System.out.println("Exiting QuickChat.");
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }

        
        if (Message.totalMessagesSent > 0) {
            System.out.println("\nTotal messages sent: " + Message.totalMessagesSent);
            System.out.println("\n--- All Sent Messages ---");
            System.out.println(new Message(0, "", "").printMessages());
        } else {
             System.out.println("\nNo messages sent.");
        }

        scanner.close();
    }
}