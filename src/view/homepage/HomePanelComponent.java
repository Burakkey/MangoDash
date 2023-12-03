package view.homepage;
import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import entity.SocialMediaStats.FacebookStats;
import interface_adapter.homepage.HomepageController;
import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePanelComponent {

    private InstagramDataSet instagramDataSet;
    private FacebookDataSet facebookDataSet;

    public HomePanelComponent() {
        instagramDataSet = new InstagramDataSet();
        facebookDataSet = new FacebookDataSet();
    }

    public void updatePanel(HomepageState newState) {
        instagramDataSet.updateStats(newState.getInstagramStatsHashMap());
        facebookDataSet.updateStats(newState.getFacebookStatsHashMap());
    }

    public static JPanel getPanel(HomepageViewModel homepageViewModel, HomepageController homepageController, ViewManagerModel viewManagerModel, JFrame application) {
        JPanel homePanel = new JPanel();

        homePanel.setLayout(new GridBagLayout());
        homePanel.setBackground(HomepageViewModel.BACKGROUND_COLOR);

        homePanel.setPreferredSize(new Dimension(200, 30));

        try {
             BufferedImage picture = ImageIO.read(new File("src/assets/homepage/Home.png"));
            JLabel picLabel = new JLabel(new ImageIcon(picture));
            Border border = BorderFactory.createEmptyBorder(0, 0, 50, 0);
            picLabel.setBorder(border);
            picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            homePanel.add(picLabel);
        } catch (IOException ignored){}

        JPanel buttons = new JPanel();
        buttons.setBackground(HomepageViewModel.BACKGROUND_COLOR);

        JButton instagramButton = new JButton(HomepageViewModel.INSTAGRAM);
        instagramButton.setBackground(HomepageViewModel.BUTTON_ORANGE);
        instagramButton.setFont(homepageViewModel.getComfortaaSmall());
        buttons.add(instagramButton);

        JButton facebookButton = new JButton(HomepageViewModel.FACEBOOK);
        facebookButton.setBackground(HomepageViewModel.BUTTON_ORANGE);
        facebookButton.setFont(homepageViewModel.getComfortaaSmall());
        buttons.add(facebookButton);

        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
        homePanel.add(buttons);

        instagramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(instagramButton)) {
                    JDialog instagramPage = new JDialog(application, "InstagramPage", true);
                    instagramPage.setSize(1200,650);
                    JPanel instagramStats = InstagramPanel.getPanel(homepageViewModel, homepageController);
                    instagramPage.add(instagramStats);
                    instagramPage.setVisible(true);
                }
            }
        });

//        homePanel.setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.weightx = 1;
//        gbc.weighty = 1;
//        gbc.fill = GridBagConstraints.BOTH;
//
//        instagramDataSet.updateStats(homepageViewModel.getState().getInstagramStatsHashMap());
//        facebookDataSet.updateStats(homepageViewModel.getState().getFacebookStatsHashMap());
//
//        homePanel.add(facebookDataSet, gbc);
//        gbc.gridx = 1;  // Move to the next column
//        gbc.weighty = 1;
//
//        homePanel.add(instagramDataSet, gbc);

        return homePanel;
    }

}

class FacebookDataSetHome extends JPanel {

    private int followersCount;
    private int maxLikes;
    private int maxComments;
    private int totalLikes;
    private int totalComments;
    private double averageLikes;
    private double averageComments;
    private List<Integer> likesPerPost;
    private List<Integer> commentsPerPost;
    private String username;
    public FacebookDataSet() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void updateStats(HashMap<String, Object> stats) {
        if (stats == null) {
            // Reset all fields to default values
            followersCount = 0;
            maxLikes = 0;
            maxComments = 0;
            totalLikes = 0;
            totalComments = 0;
            averageLikes = 0.0;
            averageComments = 0.0;
            likesPerPost = Collections.emptyList();
            commentsPerPost = Collections.emptyList();
            username = null;
        } else {
            // Set fields from stats
            this.followersCount = (int) stats.get("followersCount");
            this.maxLikes = (int) stats.get("maxLikes");
            this.maxComments = (int) stats.get("maxComments");
            this.totalLikes = (int) stats.get("totalLikes");
            this.totalComments = (int) stats.get("totalComments");
            this.maxComments = (int) stats.get("maxComments");
            this.averageLikes = (double) stats.get("averageLikes");
            this.averageComments = (double) stats.get("averageComments");
            this.likesPerPost = (List<Integer>) stats.get("likesPerPost");
            this.commentsPerPost = (List<Integer>) stats.get("commentsPerPost");
            this.username = (String) stats.get("username");
        }

        refreshDisplay();
    }

    private void refreshDisplay() {
        removeAll(); // Clear existing components

        if (!likesPerPost.isEmpty() || !commentsPerPost.isEmpty()) {
            add(createTitle());

            Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 50, 0);
            Border outlineBorder = BorderFactory.createLineBorder(Color.BLACK);
            Border border = new CompoundBorder(emptyBorder, outlineBorder);
            GridLayout layout = new GridLayout(0, 2);
            JPanel stats = new JPanel(layout);

            stats.setBorder(border);
            stats.setBackground(HomepageViewModel.BACKGROUND_COLOR);

            stats.add(createStatPanel("Friend Count", followersCount));
            stats.add(createStatPanel("Max Likes", maxLikes));
            stats.add(createStatPanel("Total Likes", totalLikes));
            stats.add(createStatPanel("Total Comments", totalComments));
            stats.add(createStatPanel("Average Likes", averageLikes));
            stats.add(createStatPanel("Average Comments", averageComments));
            stats.add(createStatPanel("Max Comments", maxComments));
            stats.add(createStatPanel());

            add(stats);

            JPanel graphs = new JPanel(new GridLayout(0, 2));
            if (!likesPerPost.isEmpty()) {
                graphs.add(createBarChart("Likes Per Post", likesPerPost));
            }
            if (!commentsPerPost.isEmpty()) {
                graphs.add(createBarChart("Comments Per Post", commentsPerPost));
            }
            add(graphs);
        }

        revalidate();
        repaint();
    }


    private JPanel createStatPanel(String label, Object value) {
        JPanel panel = new JPanel();
        JLabel text = new JLabel(label + ": " + value);
        HomepageViewModel viewModel = new HomepageViewModel();
        text.setFont(viewModel.getComfortaaSmall());
        panel.add(text);
        panel.setBackground(HomepageViewModel.GRAPH_ORANGE);
        return panel;
    }

    // This method overloading, this one will create an empty panel.
    private JPanel createStatPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel());
        panel.setBackground(HomepageViewModel.GRAPH_ORANGE);
        return panel;
    }

    private JPanel createTitle() {
        Border emptyBorder = BorderFactory.createEmptyBorder(50, 0, 50, 0);
        Border outlineBorder = BorderFactory.createLineBorder(Color.BLACK);
        Border border = new CompoundBorder(emptyBorder, outlineBorder);

        JPanel panel = new JPanel(new GridLayout());
        panel.setBorder(border);
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel(username + "'s Facebook Stats");
        titleLabel.setFont(new HomepageViewModel().getComfortaaSmall());
        titlePanel.add(titleLabel);
        titlePanel.setBackground(HomepageViewModel.GRAPH_ORANGE);

        panel.add(titlePanel);
        panel.setBackground(HomepageViewModel.BACKGROUND_COLOR);
        return panel;
    }


    private ChartPanel createBarChart(String title, List<Integer> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < data.size(); i++) {
            dataset.addValue(data.get(i), title, "Post " + (i + 1));
        }


        JFreeChart barChart = ChartFactory.createBarChart(
                title,
                "Post",
                "Count",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.getRenderer().setSeriesPaint(0, HomepageViewModel.BUTTON_ORANGE);
        barChart.setBackgroundPaint(HomepageViewModel.GRAPH_ORANGE);
        barChart.getPlot().setBackgroundPaint(HomepageViewModel.OFF_WHITE);
        barChart.setBorderPaint(Color.BLACK);
        barChart.setBorderStroke(new BasicStroke(2.0f));
        barChart.setBorderVisible(true);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setBackground(HomepageViewModel.BACKGROUND_COLOR);
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 5, 50, 5);
        chartPanel.setBorder(emptyBorder);

        return chartPanel;
    }
}

class InstagramDataSetHome extends JPanel {

    private int followersCount;
    private int maxLikes;
    private int maxComments;
    private int totalLikes;
    private int totalComments;
    private double averageLikes;
    private double averageComments;
    private List<Integer> likesPerPost;
    private List<Integer> commentsPerPost;
    private String username;
    public InstagramDataSet() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void updateStats(HashMap<String, Object> stats) {
        if (stats == null) {
            // Reset all fields to default values
            followersCount = 0;
            maxLikes = 0;
            maxComments = 0;
            totalLikes = 0;
            totalComments = 0;
            averageLikes = 0.0;
            averageComments = 0.0;
            likesPerPost = Collections.emptyList();
            commentsPerPost = Collections.emptyList();
            username = null;
        } else {
            // Set fields from stats
            this.followersCount = (int) stats.get("followersCount");
            this.maxLikes = (int) stats.get("maxLikes");
            this.maxComments = (int) stats.get("maxComments");
            this.totalLikes = (int) stats.get("totalLikes");
            this.totalComments = (int) stats.get("totalComments");
            this.maxComments = (int) stats.get("maxComments");
            this.averageLikes = (double) stats.get("averageLikes");
            this.averageComments = (double) stats.get("averageComments");
            this.likesPerPost = (List<Integer>) stats.get("likesPerPost");
            this.commentsPerPost = (List<Integer>) stats.get("commentsPerPost");
            this.username = (String) stats.get("username");
        }

        refreshDisplay();
    }

    private void refreshDisplay() {
        removeAll(); // Clear existing components

        if (!likesPerPost.isEmpty() || !commentsPerPost.isEmpty()) {
            add(createTitle());

            Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 50, 0);
            Border outlineBorder = BorderFactory.createLineBorder(Color.BLACK);
            Border border = new CompoundBorder(emptyBorder, outlineBorder);
            GridLayout layout = new GridLayout(0, 2);
            JPanel stats = new JPanel(layout);

            stats.setBorder(border);
            stats.setBackground(HomepageViewModel.BACKGROUND_COLOR);

            stats.add(createStatPanel("Followers Count", followersCount));
            stats.add(createStatPanel("Max Likes", maxLikes));
            stats.add(createStatPanel("Total Likes", totalLikes));
            stats.add(createStatPanel("Total Comments", totalComments));
            stats.add(createStatPanel("Average Likes", averageLikes));
            stats.add(createStatPanel("Average Comments", averageComments));
            stats.add(createStatPanel("Max Comments", maxComments));
            stats.add(createStatPanel());

            add(stats);

            JPanel graphs = new JPanel(new GridLayout(0, 2));
            if (!likesPerPost.isEmpty()) {
                graphs.add(createBarChart("Likes Per Post", likesPerPost));
            }
            if (!commentsPerPost.isEmpty()) {
                graphs.add(createBarChart("Comments Per Post", commentsPerPost));
            }
            add(graphs);
        }

        revalidate();
        repaint();
    }


    private JPanel createStatPanel(String label, Object value) {
        JPanel panel = new JPanel();
        JLabel text = new JLabel(label + ": " + value);
        HomepageViewModel viewModel = new HomepageViewModel();
        text.setFont(viewModel.getComfortaaSmall());
        panel.add(text);
        panel.setBackground(HomepageViewModel.GRAPH_ORANGE);
        return panel;
    }

    // This method overloading, this one will create an empty panel.
    private JPanel createStatPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel());
        panel.setBackground(HomepageViewModel.GRAPH_ORANGE);
        return panel;
    }

    private JPanel createTitle() {
        Border emptyBorder = BorderFactory.createEmptyBorder(50, 0, 50, 0);
        Border outlineBorder = BorderFactory.createLineBorder(Color.BLACK);
        Border border = new CompoundBorder(emptyBorder, outlineBorder);

        JPanel panel = new JPanel(new GridLayout());
        panel.setBorder(border);
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel(username + "'s Instagram Stats");
        titleLabel.setFont(new HomepageViewModel().getComfortaaSmall());
        titlePanel.add(titleLabel);
        titlePanel.setBackground(HomepageViewModel.GRAPH_ORANGE);

        panel.add(titlePanel);
        panel.setBackground(HomepageViewModel.BACKGROUND_COLOR);
        return panel;
    }


    private ChartPanel createBarChart(String title, List<Integer> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < data.size(); i++) {
            dataset.addValue(data.get(i), title, "Post " + (i + 1));
        }


        JFreeChart barChart = ChartFactory.createBarChart(
                title,
                "Post",
                "Count",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.getRenderer().setSeriesPaint(0, HomepageViewModel.BUTTON_ORANGE);
        barChart.setBackgroundPaint(HomepageViewModel.GRAPH_ORANGE);
        barChart.getPlot().setBackgroundPaint(HomepageViewModel.OFF_WHITE);
        barChart.setBorderPaint(Color.BLACK);
        barChart.setBorderStroke(new BasicStroke(2.0f));
        barChart.setBorderVisible(true);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setBackground(HomepageViewModel.BACKGROUND_COLOR);
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 5, 50, 5);
        chartPanel.setBorder(emptyBorder);

        return chartPanel;
    }
}
