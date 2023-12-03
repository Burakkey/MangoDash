package view.homepage;

import interface_adapter.ViewManagerModel;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class InstagramPageView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "Instapage";
    private final HomepageViewModel homepageViewModel;
    private final ViewManagerModel viewManagerModel;
    private InstagramDataSet instagramDataSet;

    public InstagramPageView(HomepageViewModel homepageViewModel, ViewManagerModel viewManagerModel) {
        this.setBackground(HomepageViewModel.BACKGROUND_COLOR);
        this.setLayout(new BorderLayout());

        this.homepageViewModel = homepageViewModel;
        this.viewManagerModel = viewManagerModel;
        this.homepageViewModel.addPropertyChangeListener(this);
        instagramDataSet = new InstagramDataSet();


        instagramDataSet.updateStats(homepageViewModel.getState().getInstagramStatsHashMap());
        this.add(instagramDataSet, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
