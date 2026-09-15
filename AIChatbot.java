import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AIChatbot extends JFrame {

    private JTextArea chatArea;
    private JTextField inputField;
    private JLabel statusLabel;

    private int userMessages = 0;
    private int botMessages = 0;

    public AIChatbot() {

        // Window settings
        setTitle("AI Chatbot");
        setSize(750, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(18, 18, 18));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // =========================
        // HEADER
        // =========================

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 30, 30));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("🤖  AI CHATBOT");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 25));

        statusLabel = new JLabel("● Online");
        statusLabel.setForeground(new Color(80, 220, 120));
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(statusLabel, BorderLayout.EAST);

        // =========================
        // CHAT AREA
        // =========================

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        chatArea.setBackground(new Color(22, 22, 22));
        chatArea.setForeground(new Color(235, 235, 235));
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        chatArea.setMargin(new Insets(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(
                new Color(50, 50, 50)
        ));

        // Welcome message
        addBotMessage(
                "Hello! 👋\n" +
                "I'm your AI chatbot.\n\n" +
                "You can ask me about:\n" +
                "• Java\n" +
                "• Programming\n" +
                "• Artificial Intelligence\n" +
                "• NLP\n" +
                "• Help\n\n" +
                "How can I help you?"
        );

        // =========================
        // INPUT PANEL
        // =========================

        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setBackground(new Color(18, 18, 18));
        inputPanel.setBorder(new EmptyBorder(15, 0, 10, 0));

        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputField.setForeground(Color.WHITE);
        inputField.setBackground(new Color(35, 35, 35));
        inputField.setCaretColor(Color.WHITE);

        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70)),
                new EmptyBorder(10, 12, 10, 12)
        ));

        inputField.setToolTipText("Type your message here...");

        JButton sendButton = new JButton("Send ➤");
        styleButton(sendButton);

        sendButton.addActionListener(this::sendMessage);

        // Press Enter to send
        inputField.addActionListener(this::sendMessage);

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // =========================
        // BOTTOM BUTTONS
        // =========================

        JPanel bottomPanel = new JPanel(new FlowLayout(
                FlowLayout.CENTER, 15, 5
        ));

        bottomPanel.setBackground(new Color(18, 18, 18));

        JButton clearButton = new JButton("🧹 Clear Chat");
        JButton statsButton = new JButton("📊 Statistics");
        JButton exitButton = new JButton("✕ Exit");

        styleSecondaryButton(clearButton);
        styleSecondaryButton(statsButton);
        styleSecondaryButton(exitButton);

        clearButton.addActionListener(e -> clearChat());

        statsButton.addActionListener(e -> showStatistics());

        exitButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to exit?",
                    "Exit Chatbot",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        bottomPanel.add(clearButton);
        bottomPanel.add(statsButton);
        bottomPanel.add(exitButton);

        // =========================
        // ADD COMPONENTS
        // =========================

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(new Color(18, 18, 18));

        southPanel.add(inputPanel, BorderLayout.NORTH);
        southPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Focus on input field
        inputField.requestFocusInWindow();
    }

    // ==========================================
    // SEND MESSAGE
    // ==========================================

    private void sendMessage(ActionEvent e) {

        String userInput = inputField.getText().trim();

        if (userInput.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please type a message first.",
                    "Empty Message",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        userMessages++;

        addUserMessage(userInput);

        String response = getBotResponse(userInput);

        botMessages++;

        addBotMessage(response);

        inputField.setText("");
        inputField.requestFocus();
    }

    // ==========================================
    // NLP PREPROCESSING
    // ==========================================

    private String preprocess(String text) {

        // Convert to lowercase
        text = text.toLowerCase();

        // Remove punctuation
        text = text.replaceAll("[^a-zA-Z0-9\\s]", "");

        // Remove extra spaces
        text = text.replaceAll("\\s+", " ").trim();

        return text;
    }

    // ==========================================
    // CHATBOT LOGIC
    // ==========================================

    private String getBotResponse(String userInput) {

        String input = preprocess(userInput);

        // Greeting
        if (input.contains("hello")
                || input.contains("hi")
                || input.contains("hey")) {

            return "Hello! 😊 Nice to meet you.\nHow can I help you today?";
        }

        // Name
        if (input.contains("your name")
                || input.contains("who are you")) {

            return "I'm your AI Chatbot 🤖\nYou can call me CodeBot!";
        }

        // Java
        if (input.contains("java")) {

            return "Java is a popular object-oriented programming language. ☕\n\n" +
                    "It is commonly used for:\n" +
                    "• Desktop applications\n" +
                    "• Web applications\n" +
                    "• Android development\n" +
                    "• Enterprise software";
        }

        // Programming
        if (input.contains("programming")
                || input.contains("coding")) {

            return "Programming means giving instructions to a computer " +
                    "to perform a specific task. 💻\n\n" +
                    "Popular languages include Java, Python, C++, JavaScript and C#.";
        }

        // AI
        if (input.contains("artificial intelligence")
                || input.equals("ai")
                || input.contains("what is ai")) {

            return "Artificial Intelligence (AI) is the field of creating " +
                    "computer systems that can perform tasks that normally " +
                    "require human intelligence. 🧠";
        }

        // NLP
        if (input.contains("nlp")
                || input.contains("natural language")) {

            return "NLP stands for Natural Language Processing. 🗣️\n\n" +
                    "It helps computers understand and process human language.\n\n" +
                    "Examples:\n" +
                    "• Chatbots\n" +
                    "• Translation\n" +
                    "• Sentiment analysis\n" +
                    "• Speech recognition";
        }

        // Machine Learning
        if (input.contains("machine learning")
                || input.contains("ml")) {

            return "Machine Learning is a part of AI where computers learn " +
                    "patterns from data and use those patterns to make predictions.";
        }

        // Help
        if (input.contains("help")
                || input.contains("what can you do")) {

            return "I can answer basic questions about:\n\n" +
                    "🤖 Artificial Intelligence\n" +
                    "☕ Java\n" +
                    "💻 Programming\n" +
                    "🧠 Machine Learning\n" +
                    "🗣️ NLP\n\n" +
                    "Try asking: \"What is Java?\"";
        }

        // Thanks
        if (input.contains("thank")
                || input.contains("thanks")) {

            return "You're welcome! 😊\nI'm happy to help!";
        }

        // Bye
        if (input.contains("bye")
                || input.contains("goodbye")) {

            return "Goodbye! 👋\nHave a great day!";
        }

        // How are you
        if (input.contains("how are you")) {

            return "I'm doing great! 🤖✨\nThanks for asking!";
        }

        // Default
        return "I'm not sure I understand that yet. 🤔\n\n" +
                "Try asking me about Java, AI, NLP, Machine Learning, " +
                "Programming, or type \"help\".";
    }

    // ==========================================
    // ADD USER MESSAGE
    // ==========================================

    private void addUserMessage(String message) {

        chatArea.append(
                "\n👤 You\n" +
                message +
                "\n"
        );

        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    // ==========================================
    // ADD BOT MESSAGE
    // ==========================================

    private void addBotMessage(String message) {

        chatArea.append(
                "\n🤖 AI Bot\n" +
                message +
                "\n"
        );

        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    // ==========================================
    // CLEAR CHAT
    // ==========================================

    private void clearChat() {

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Clear the complete chat?",
                "Clear Chat",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {

            chatArea.setText("");

            userMessages = 0;
            botMessages = 0;

            addBotMessage(
                    "Chat cleared! 🧹\n" +
                    "Hello again! How can I help you?"
            );
        }
    }

    // ==========================================
    // STATISTICS
    // ==========================================

    private void showStatistics() {

        JOptionPane.showMessageDialog(
                this,
                "📊 CHATBOT STATISTICS\n\n" +
                        "Your messages: " + userMessages + "\n" +
                        "Bot responses: " + botMessages + "\n\n" +
                        "Status: ● Online",
                "Chatbot Statistics",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ==========================================
    // BUTTON STYLE
    // ==========================================

    private void styleButton(JButton button) {

        button.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                15
        ));

        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 120, 220));

        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(
                10, 20, 10, 20
        ));

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleSecondaryButton(JButton button) {

        button.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                13
        ));

        button.setForeground(new Color(220, 220, 220));
        button.setBackground(new Color(40, 40, 40));

        button.setFocusPainted(false);

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(70, 70, 70)
                ),
                new EmptyBorder(
                        8, 15, 8, 15
                )
        ));

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // ==========================================
    // MAIN METHOD
    // ==========================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            AIChatbot chatbot = new AIChatbot();

            chatbot.setVisible(true);
        });
    }
}