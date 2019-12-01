import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MultiscaleModellingGui extends JFrame implements ActionListener {

    public static final int WIDTH = 606;
    public static final int HEIGHT = 650;

    private static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(WIDTH, HEIGHT);
    private static final Dimension MINIMUM_WINDOW_SIZE = new Dimension(WIDTH, HEIGHT);

    private JMenuBar menuBar;
    private JMenu menuFile, menuSimulation;
    private JMenuItem menuItemFileImport, menuItemFileExport, exitMenuItem, addRandomCellMenuItem;
    private JMenuItem startSimulationMenuItem, stopSimulationMenuItem, resetSimulationMenuItem;

    private MultiscaleModellingPanel multiscaleModellingPanel;
    private ImportExport importExport;

    public MultiscaleModellingGui() throws HeadlessException {
        guiInit();
        jFrameConfig();

        importExport = new ImportExportTxt();
    }

    private void guiInit() {
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuFile = new JMenu("File");
        menuBar.add(menuFile);
        menuSimulation = new JMenu("Simulation");
        menuBar.add(menuSimulation);

        menuItemFileImport = new JMenuItem("Import");
        menuItemFileImport.addActionListener(this);
        menuItemFileExport = new JMenuItem("Export");
        menuItemFileExport.addActionListener(this);
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(this);
        menuFile.add(menuItemFileExport);
        menuFile.add(menuItemFileImport);
        menuFile.add(new JSeparator());
        menuFile.add(exitMenuItem);
        startSimulationMenuItem = new JMenuItem("Start");
        startSimulationMenuItem.addActionListener(this);
        stopSimulationMenuItem = new JMenuItem("Stop");
        stopSimulationMenuItem.setEnabled(false);
        stopSimulationMenuItem.addActionListener(this);
        resetSimulationMenuItem = new JMenuItem("Reset");
        resetSimulationMenuItem.addActionListener(this);
        addRandomCellMenuItem = new JMenuItem("Add Random Cell");
        addRandomCellMenuItem.addActionListener(this);
        menuSimulation.add(addRandomCellMenuItem);
        menuSimulation.add(new JSeparator());
        menuSimulation.add(startSimulationMenuItem);
        menuSimulation.add(stopSimulationMenuItem);
        menuSimulation.add(resetSimulationMenuItem);
        multiscaleModellingPanel = new MultiscaleModellingPanel();
        add(multiscaleModellingPanel);

        System.out.println("GUI initialized successfully");
    }


    private void jFrameConfig() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Multiscale Modelling");
        setSize(DEFAULT_WINDOW_SIZE);
        setMinimumSize(MINIMUM_WINDOW_SIZE);
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth())/2,
            (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight())/2);
        setVisible(true);

        System.out.println("Config initialized successfully");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(exitMenuItem)) {
            System.out.println("Application closed");
            System.exit(0);
        }  else if (ae.getSource().equals(resetSimulationMenuItem)) {
            resetSimulation();
        } else if (ae.getSource().equals(startSimulationMenuItem)) {
            startSimulation();
        } else if (ae.getSource().equals(stopSimulationMenuItem)) {
            stopSimulation();
        } else if (ae.getSource().equals(addRandomCellMenuItem)) {
            addRandomCell();
        } else if (ae.getSource().equals(menuItemFileImport)) {
            importFile();
        } else if (ae.getSource().equals(menuItemFileExport)) {
            exportFile();
        }
    }

    private void importFile() {
        multiscaleModellingPanel.setCells(importExport.importFile());
    }

    private void exportFile() {
        importExport.exportFile(multiscaleModellingPanel.getCells());
        multiscaleModellingPanel.resetSimulation();
    }

    private void addRandomCell() {
        multiscaleModellingPanel.addRandomCell();
        System.out.println("Cell successfully added");
    }

    private void stopSimulation() {
        startSimulationMenuItem.setEnabled(true);
        stopSimulationMenuItem.setEnabled(false);
        resetSimulationMenuItem.setEnabled(true);
        multiscaleModellingPanel.stopSimulation();
        System.out.println("Simulation stopped");
    }

    private void startSimulation() {
        startSimulationMenuItem.setEnabled(false);
        stopSimulationMenuItem.setEnabled(true);
        resetSimulationMenuItem.setEnabled(false);
        multiscaleModellingPanel.startSimulation();
        System.out.println("Simulation started");
    }

    private void resetSimulation() {
        multiscaleModellingPanel.resetSimulation();
        multiscaleModellingPanel.repaint();
        System.out.println("Simulation reset");
    }

}
