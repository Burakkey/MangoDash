package view.homepage;
import interface_adapter.homepage.HomepageController;
import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePanelComponent {

    private InstagramDataSet instagramDataSet;

    public HomePanelComponent() {
        instagramDataSet = new InstagramDataSet();
        // Initialize other components
    }

    public void updatePanel(HomepageState newState) {
        instagramDataSet.updateStats(newState.getInstagramStatsHashMap());
    }

    public JPanel getPanel(HomepageViewModel homepageViewModel, HomepageController homepageController) {
        JPanel homePanel = new JPanel();
        homePanel.setBackground(HomepageViewModel.BACKGROUND_COLOR);
        homePanel.setLayout(new BorderLayout());
        instagramDataSet.updateStats(homepageViewModel.getState().getInstagramStatsHashMap());
        homePanel.add(instagramDataSet, BorderLayout.CENTER);

        // Configure facebookDataSet just like this pls
        return homePanel;
    }

}

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
        }

        refreshDisplay();
    }

    private void refreshDisplay() {
        removeAll(); // Clear existing components

        if (!likesPerPost.isEmpty() || !commentsPerPost.isEmpty()) {
            add(createStatPanel("Followers Count", followersCount));
            add(createStatPanel("Max Likes", maxLikes));
            add(createStatPanel("Total Likes", totalLikes));
            add(createStatPanel("Total Comments", totalComments));
            add(createStatPanel("Average Likes", averageLikes));
            add(createStatPanel("Average Comments", averageComments));
            add(createStatPanel("Max Comments", maxComments));

            if (!likesPerPost.isEmpty()) {
                add(createBarChart("Likes Per Post", likesPerPost));
            }
            if (!commentsPerPost.isEmpty()) {
                add(createBarChart("Comments Per Post", commentsPerPost));
            }
        }

        revalidate();
        repaint();
    }


    private JPanel createStatPanel(String label, Object value) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(label + ": " + value));
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

        return new ChartPanel(barChart);
    }
}
