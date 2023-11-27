package view.homepage;

import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class HomePanelComponent {
    public static JPanel getPanel(){
        JPanel homePanel = new JPanel();
        homePanel.setBackground(HomepageViewModel.BACKGROUND_COLOR);
        JButton instagramGraph = new JButton(HomepageViewModel.SHOW_INSTAGRAM_GRAPH_LABEL);
        homePanel.add(instagramGraph);
        instagramGraph.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(instagramGraph)) {
//                            HomepageState currentState = homepagestate.getInstagramStatsHashMap();
//
//                            currentState.executeInstagramGraphing(
//                                    currentState.getName(),
//                                    currentState.getUsername(),
//                                    currentState.getPassword(),
//                                    currentState.getRepeatPassword()
//                            );
                            SwingUtilities.invokeLater(() -> {
                                JFrame frame = new JFrame("Bar Graph Example");
                                frame.setSize(800, 600);
                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                HashMap<String, Object> example_data = new HashMap<>();
                                example_data.put("followers", "234"); //TODO How WILL i GET THE DATA

                                CategoryDataset dataset = createDataset(example_data);
                                JFreeChart chart = createBarChart(
                                        "Bar Graph",
                                        "Category",
                                        "Number",
                                        dataset
                                );

                                ChartPanel chartPanel = new ChartPanel(chart);
                                frame.getContentPane().add(chartPanel);

                                frame.setVisible(true);
                            });


                        }
                    }
                }
        );
        return homePanel;
    }
    public static CategoryDataset createDataset(HashMap<String, Object> instagramStatsHashMap) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int index = 0;
        for (Map.Entry<String, Object> entry : instagramStatsHashMap.entrySet()) {
            String key = entry.getKey();
//            Object value = entry.getValue();
            int value = Integer.parseInt((String) entry.getValue());
            /*TODO Change value to INTEGER*/
            dataset.addValue(value, "Series" + String.valueOf(index), key);

        }
        // Add more data points or series as needed
        return dataset;
    }

    public static JFreeChart createBarChart(
            String title,
            String categoryAxisLabel,
            String valueAxisLabel,
            CategoryDataset dataset) {

        return ChartFactory.createBarChart(
                title,
                categoryAxisLabel,
                valueAxisLabel,
                dataset
        );
    }

}
