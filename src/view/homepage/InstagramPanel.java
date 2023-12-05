package view.homepage;
import interface_adapter.homepage.HomepageController;
import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * InstagramComponent contains methods that display the user's Instagram data to the user that is retrieved using the Instagram Graph API
 */
public class InstagramPanel {

    private static InstagramDataSet instagramDataSet;
    public JFrame application;

    /**
     * Creates a new InstagramPanel object on the given JFrame
     * @param application
     */
    public InstagramPanel(JFrame application) {
        instagramDataSet = new InstagramDataSet();
        this.application = application;
    }

    /**
     * Updates the Instagram data when the Homepage state changes
     * @param newState
     */
    public void updatePanel(HomepageState newState) {
        instagramDataSet.updateStats(newState.getInstagramStatsHashMap());
    }

    /**
     * This method builds the InstagramPanel that the user sees
     * @param homepageViewModel
     * @param homepageController
     * @return a JPanel that displays the relevant Instagram data
     */
    public static JPanel getPanel(HomepageViewModel homepageViewModel, HomepageController homepageController) {
        JPanel homePanel = new JPanel();
        homePanel.setBackground(HomepageViewModel.BACKGROUND_COLOR);
        homePanel.setLayout(new BorderLayout());
        instagramDataSet.updateStats(homepageViewModel.getState().getInstagramStatsHashMap());
        homePanel.add(instagramDataSet, BorderLayout.CENTER);


        // Configure facebookDataSet just like this pls
        return homePanel;
    }

}

/**
 * InstagramDataSet includes the Instagram data retrieved using the Instagram Graph API
 */
class InstagramDataSet extends JPanel {

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

    /**
     * Updates the Instagram data and displays the changes to the user
     * @param stats
     */
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

            Border emptyBorder = BorderFactory.createEmptyBorder(0, 300, 50, 300);
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
        Border emptyBorder = BorderFactory.createEmptyBorder(50, 200, 50, 200);
        Border outlineBorder = BorderFactory.createLineBorder(Color.BLACK);
        Border border = new CompoundBorder(emptyBorder, outlineBorder);

        JPanel panel = new JPanel(new GridLayout());
        panel.setBorder(border);
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel(username + "'s Instagram Stats");
        titleLabel.setFont(new HomepageViewModel().getComfortaaMedium());
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
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 50, 50, 50);
        chartPanel.setBorder(emptyBorder);

        return chartPanel;
    }
}
