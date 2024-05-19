import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

class DayObject {
    protected String text;
    protected String verse;

    public DayObject(String text, String verse) {
        this.text = text;
        this.verse = verse;
    }
}

public class GUI implements ActionListener {

    private JFrame frame;
    private JPanel menuPanel;
    private JPanel mainPanel;
    private JPanel inputPanel;

    private JLabel mainTitle;
    private JLabel descriptionText;

    private JRadioButton fullStoryRadio;
    private JRadioButton userSelectRadio;
    private ButtonGroup radioGroup;

    private JButton selectButton;
    private boolean fullIsSelected;
    private CardLayout mainLayout;

    private HashMap<String, DayObject> storyMap = new HashMap<String, DayObject>();

    public GUI() {
        frame = new JFrame();
        mainLayout = new CardLayout();
        mainPanel = new JPanel(mainLayout);
        menuPanel = new JPanel();
        inputPanel = new InputPanel(mainLayout, mainPanel);

        mainTitle = new JLabel();
        mainTitle.setText("The Creation Story");
        mainTitle.setFont(new Font("SansSerif", Font.BOLD, 30));
        mainTitle.setHorizontalAlignment(SwingConstants.CENTER);

        descriptionText = new JLabel();
        descriptionText.setText(
                "<html>This interactive program walks you through the creation story! <br/>Please choose an option to continue</html>");
        descriptionText.setFont(new Font("SansSerif", Font.PLAIN, 22));
        descriptionText.setHorizontalAlignment(SwingConstants.CENTER);

        fullStoryRadio = new JRadioButton("Go through all seven days");
        userSelectRadio = new JRadioButton("Enter a specific day");

        fullStoryRadio.setFont(new Font("SansSerif", Font.PLAIN, 20));
        userSelectRadio.setFont(new Font("SansSerif", Font.PLAIN, 20));

        radioGroup = new ButtonGroup();
        radioGroup.add(fullStoryRadio);
        radioGroup.add(userSelectRadio);

        selectButton = new JButton("Continue");
        selectButton.addActionListener(this);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        GroupLayout layout = new GroupLayout(menuPanel);
        menuPanel.setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(mainTitle)
                                .addComponent(descriptionText))
                        .addGap(10, 15, 20)
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addComponent(fullStoryRadio)
                                        .addGap(5, 5, 5)
                                        .addComponent(userSelectRadio))
                        .addGap(10)
                        .addComponent(selectButton));
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addComponent(mainTitle)
                        .addComponent(descriptionText)
                        .addComponent(fullStoryRadio)
                        .addComponent(userSelectRadio)
                        .addComponent(selectButton));

        frame.add(mainPanel, BorderLayout.CENTER);
        mainPanel.add(menuPanel, "menu");
        mainPanel.add(inputPanel, "input");

        setStoryValues(storyMap);
        for (String i : storyMap.keySet()) {
            mainPanel.add(new DayPanel("Day " + i, storyMap.get(i).text, storyMap.get(i).verse, mainLayout, mainPanel),
                    "day-" + i);
        }
        frame.pack();
        frame.setSize(new Dimension(700, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("The Genesis Story");
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fullIsSelected = fullStoryRadio.isSelected();

        if (fullIsSelected == true) {
            this.mainLayout.show(mainPanel, "day-1");
        } else {
            this.mainLayout.show(mainPanel, "input");
        }
    }

    public void setStoryValues(HashMap<String, DayObject> days) {
        days.put("1", new DayObject(
                "<html>God created the Heavens and the earth.<br/>Then God said let there be light, and there was light.<br> The light was called day, and the darkness was called night.</html>",
                "Genesis 1:1-5"));
        days.put("2", new DayObject(
                "<html>God made the firmament, and divided the waters above the firmament and below the firmament.<br/>God called the firmament Heaven</html>",
                "Genesis 1:6-8"));
        days.put("3", new DayObject(
                "<html>God made the waters under the heaven gather into one place.<br> The gathering of waters was called seas and the dry land that appeared was called Earth.<br>God made the earth bring forth grass, trees and fruits.</html>",
                "Genesis 1:9-13"));
        days.put("4", new DayObject(
                "<html>God made two great lights in the firmament. The greater light to rule over the day and the lesser light to rule over the night.<br>God also created the stars</html>",
                "Genesis 1:14-19"));
        days.put("5", new DayObject(
                "<html>God made the waters full of living creatures, birds, sea creatures and every living thing that moves.<br>God blessed them and said 'Be fruitful and multiply, and fill the waters in the seas and let birds multiply on the earth'</html>",
                "Genesis 1:20-23"));
        days.put("6", new DayObject(
                "<html>God made living creatures on land, each according to their own kind.<br>Then God created man in their own image.</html>",
                "Genesis 1:24-31"));
        days.put("7", new DayObject("<html>God rested.</html>", "Genesis 2:1-3"));
    }
}

class DayPanel extends JPanel implements ActionListener {
    private JLabel title;
    private JLabel text;
    private JLabel verse;
    private JButton nextButton;
    private JButton previousButton;
    private JButton homeButton;
    private JPanel mainPanel;
    private CardLayout panelLayout;
    private String index;

    public DayPanel(String dayTitle, String dayText, String dayVerse, CardLayout panelLayout, JPanel mainPanel) {
        this.mainPanel = mainPanel;
        this.panelLayout = panelLayout;

        this.index = dayTitle.split(" ")[1];

        this.title = new JLabel(dayTitle);
        this.title.setFont(new Font("SansSerif", Font.BOLD, 30));

        this.text = new JLabel(dayText);
        this.text.setFont(new Font("SansSerif", Font.PLAIN, 22));

        this.verse = new JLabel(dayVerse);
        this.verse.setFont(new Font("SansSerif", Font.PLAIN, 14));

        this.homeButton = new JButton("Back to Home");
        this.homeButton.setBackground(Color.BLACK);
        this.homeButton.setForeground(Color.white);
        this.homeButton.addActionListener(this);

        this.previousButton = new JButton("Previous Day");
        this.previousButton.setBackground(Color.LIGHT_GRAY);
        this.previousButton.addActionListener(this);

        this.nextButton = new JButton("Next Day");
        this.nextButton.setBackground(Color.BLUE);
        this.nextButton.setForeground(Color.white);
        this.nextButton.addActionListener(this);

        GroupLayout grouplayout = new GroupLayout(this);
        this.setLayout(grouplayout);

        grouplayout.setHorizontalGroup(
                grouplayout.createParallelGroup()
                        .addComponent(title)
                        .addComponent(text)
                        .addComponent(verse)
                        .addGroup(
                                grouplayout.createSequentialGroup()
                                        .addComponent(homeButton)
                                        .addGap(30)
                                        .addComponent(previousButton)
                                        .addGap(30)
                                        .addComponent(nextButton)));
        grouplayout.setVerticalGroup(
                grouplayout.createSequentialGroup()
                        .addGroup(
                                grouplayout.createSequentialGroup()
                                        .addComponent(title)
                                        .addGap(10)
                                        .addComponent(text)
                                        .addGap(5)
                                        .addComponent(verse))
                        .addGap(20)
                        .addGroup(
                                grouplayout.createParallelGroup()
                                        .addComponent(homeButton)
                                        .addComponent(previousButton)
                                        .addComponent(nextButton)));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.homeButton) {
            panelLayout.show(mainPanel, "menu");
        } else if (e.getSource() == this.previousButton) {
            if (!this.previousButton.isEnabled()) {
                return;
            }
            String prevDay = "day-" + (Integer.parseInt(this.index) - 1);
            panelLayout.show(mainPanel, prevDay);
        } else {
            if (!this.nextButton.isEnabled()) {
                return;
            }
            String nextDay = "day-" + (Integer.parseInt(this.index) + 1);
            panelLayout.show(mainPanel, nextDay);
        }
    }
}

class InputPanel extends JPanel implements ActionListener {
    private JLabel title;
    private JButton nextButton;
    private JTextField userInput;
    private CardLayout mainLayout;
    private JPanel mainPanel;

    public InputPanel(CardLayout mainLayout, JPanel mainPanel) {
        this.setLayout(mainLayout);
        this.title = new JLabel("Which day do you want to view?");
        this.title.setFont(new Font("SansSerif", Font.BOLD, 30));

        this.mainLayout = mainLayout;
        this.mainPanel = mainPanel;

        this.userInput = new JTextField("");
        this.userInput.setPreferredSize(new Dimension(200, 20));
        this.userInput.setMaximumSize(this.userInput.getPreferredSize());

        this.nextButton = new JButton("Continue");
        this.nextButton.setBackground(Color.blue);
        this.nextButton.setForeground(Color.white);
        this.nextButton.addActionListener(this);

        GroupLayout groupLayout = new GroupLayout(this);
        this.setLayout(groupLayout);

        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup()
                .addComponent(this.title)
                .addComponent(this.userInput)
                .addComponent(this.nextButton));
        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addComponent(this.title)
                .addGap(20)
                .addComponent(this.userInput)
                .addGap(10)
                .addComponent(this.nextButton));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // This is wrong on so many levels but I don't really have time :)

        if (userInput.getText().equals("1") || userInput.getText().equals("one") || userInput.getText().equals("One")) {
            System.out.println("Hi 2");
            this.mainLayout.show(mainPanel, "day-1");
        }
        if (userInput.getText().equals("2") || userInput.getText().equals("two") || userInput.getText().equals("Two")) {
            this.mainLayout.show(mainPanel, "day-2");
        }
        if (userInput.getText().equals("3") || userInput.getText().equals("three")
                || userInput.getText().equals("Three")) {
            this.mainLayout.show(mainPanel, "day-3");
        }
        if (userInput.getText().equals("4") || userInput.getText().equals("four")
                || userInput.getText().equals("Four")) {
            this.mainLayout.show(mainPanel, "day-4");
        }
        if (userInput.getText().equals("5") || userInput.getText().equals("five")
                || userInput.getText().equals("Five")) {
            this.mainLayout.show(mainPanel, "day-5");
        }
        if (userInput.getText().equals("6") || userInput.getText().equals("six") || userInput.getText().equals("Six")) {
            this.mainLayout.show(mainPanel, "day-6");
        }
        if (userInput.getText().equals("7") || userInput.getText().equals("seven")
                || userInput.getText().equals("Seven")) {
            this.mainLayout.show(mainPanel, "day-7");
        }
    }
}