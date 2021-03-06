import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigDecimal;
import javax.swing.*;


public class MultiscaleModellingGui extends JFrame implements ActionListener {

    public static final int WIDTH = 606;
    public static final int HEIGHT = 650;

    private static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(WIDTH, HEIGHT);
    private static final Dimension MINIMUM_WINDOW_SIZE = new Dimension(WIDTH, HEIGHT);

    private static final String[] importExportFormats = {ImportExportTxt.format, ImportExportBmp.format};
    private static final String[] incusionTypes = {Cells.CIRCLE, Cells.SQUARE};
    private static final String[] substructure = {Cells.DUAL, Cells.SUB};

    private JMenuBar menuBar;
    private JMenu menuFile, menuSimulation;
    private JMenuItem menuItemFileImport, menuItemFileExport, exitMenuItem, addRandomCellMenuItem,
        addRandomInclusionMenuItem, initMenuItem;
    private JMenuItem startSimulationMenuItem, resetSimulationMenuItem, resetWithSelectedMenuItem,
        boundaryColorMenuItem, boundaryColorSelectedMenuItem, clearWithSelectedBoundariesMenuItem,
        clearWithAllBoundariesMenuItem;
    private JFileChooser fileChooser = new JFileChooser();

    private MultiscaleModellingPanel multiscaleModellingPanel;

    private ImportExport importExportBmp = new ImportExportBmp();
    private ImportExport importExportTxt = new ImportExportTxt();

    public MultiscaleModellingGui() throws HeadlessException {
        guiInit();
        jFrameConfig();
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
        resetSimulationMenuItem = new JMenuItem("Reset");
        resetSimulationMenuItem.addActionListener(this);
        resetWithSelectedMenuItem = new JMenuItem("Reset with selected");
        resetWithSelectedMenuItem.addActionListener(this);
        boundaryColorMenuItem = new JMenuItem("Color all boundaries");
        boundaryColorMenuItem.addActionListener(this);
        clearWithSelectedBoundariesMenuItem = new JMenuItem("Clear with selected boundaries");
        clearWithSelectedBoundariesMenuItem.addActionListener(this);
        clearWithAllBoundariesMenuItem = new JMenuItem("Clear with all boundaries");
        clearWithAllBoundariesMenuItem.addActionListener(this);
        boundaryColorSelectedMenuItem = new JMenuItem("Color selected boundaries");
        boundaryColorSelectedMenuItem.addActionListener(this);
        addRandomCellMenuItem = new JMenuItem("Add Random Cell");
        addRandomCellMenuItem.addActionListener(this);
        initMenuItem = new JMenuItem("Init Cell");
        initMenuItem.addActionListener(this);
        addRandomInclusionMenuItem = new JMenuItem("Add Random Inclusion");
        addRandomInclusionMenuItem.addActionListener(this);
        menuSimulation.add(initMenuItem);
        menuSimulation.add(addRandomCellMenuItem);
        menuSimulation.add(addRandomInclusionMenuItem);
        menuSimulation.add(new JSeparator());
        menuSimulation.add(boundaryColorMenuItem);
        menuSimulation.add(boundaryColorSelectedMenuItem);
        menuSimulation.add(clearWithAllBoundariesMenuItem);
        menuSimulation.add(clearWithSelectedBoundariesMenuItem);
        menuSimulation.add(new JSeparator());
        menuSimulation.add(startSimulationMenuItem);
        menuSimulation.add(resetSimulationMenuItem);
        menuSimulation.add(resetWithSelectedMenuItem);
        multiscaleModellingPanel = new MultiscaleModellingPanel();
        add(multiscaleModellingPanel);
        System.out.println("GUI initialized successfully");
    }

    private void jFrameConfig() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Multiscale Modelling");
        setSize(DEFAULT_WINDOW_SIZE);
        setMinimumSize(MINIMUM_WINDOW_SIZE);
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2,
            (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2);
        setVisible(true);

        System.out.println("Config initialized successfully");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(exitMenuItem)) {
            System.out.println("Application closed");
            System.exit(0);
        } else if (ae.getSource().equals(resetSimulationMenuItem)) {
            resetSimulation();
        } else if (ae.getSource().equals(resetWithSelectedMenuItem)) {
            resetWithSelected();
        } else if (ae.getSource().equals(boundaryColorMenuItem)) {
            colorBoundaries();
        } else if (ae.getSource().equals(clearWithAllBoundariesMenuItem)) {
            clearAllWithBoundaries();
        } else if (ae.getSource().equals(boundaryColorSelectedMenuItem)) {
            colorSelectedBoundaries();
        } else if (ae.getSource().equals(clearWithSelectedBoundariesMenuItem)) {
            clearWithBoundaries();
        } else if (ae.getSource().equals(startSimulationMenuItem)) {
            startSimulation();
        } else if (ae.getSource().equals(addRandomCellMenuItem)) {
            addCells();
        } else if (ae.getSource().equals(addRandomInclusionMenuItem)) {
            addInclussions();
        } else if (ae.getSource().equals(initMenuItem)) {
            initCells();
        } else if (ae.getSource().equals(menuItemFileImport)) {
            importFile();
        } else if (ae.getSource().equals(menuItemFileExport)) {
            exportFile();
        }
    }

    private void clearAllWithBoundaries() {
        multiscaleModellingPanel.clearWithAllBoundaries();
        multiscaleModellingPanel.repaint();
    }

    private void clearWithBoundaries() {
        final JFrame f_options = new JFrame();
        f_options.setTitle("Clear");
        f_options.setSize(500, 100);
        f_options.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - f_options.getWidth()) / 2,
            (Toolkit.getDefaultToolkit().getScreenSize().height - f_options.getHeight()) / 2);
        f_options.setResizable(false);
        f_options.setAlwaysOnTop(true);
        JPanel p_options = new JPanel();
        p_options.setOpaque(false);
        f_options.add(p_options);
        p_options.add(new JLabel("Number of inclusions:"));
        JButton addButton = new JButton("Clear");
        addButton.setBounds(25, 0, 20, 20);
        addButton.addActionListener(ae -> {

        });
        p_options.add(addButton);
        f_options.setVisible(true);
    }

    private void initCells() {
        final JFrame f_options = new JFrame();
        f_options.setTitle("Init cells");
        f_options.setSize(400, 60);
        f_options.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - f_options.getWidth()) / 2,
            (Toolkit.getDefaultToolkit().getScreenSize().height - f_options.getHeight()) / 2);
        f_options.setResizable(false);
        f_options.setAlwaysOnTop(true);
        JPanel p_options = new JPanel();
        p_options.setOpaque(false);
        f_options.add(p_options);
        p_options.add(new JLabel("X:"));
        final JTextField xTextField = new JTextField("300");
        p_options.add(xTextField);
        p_options.add(new JLabel("Y:"));
        final JTextField yTextField = new JTextField("300");
        p_options.add(yTextField);
        p_options.add(new JLabel("Percentage:"));
        final JTextField percetage = new JTextField("10");
        p_options.add(percetage);
        JButton addButton = new JButton("Init");
        addButton.setBounds(25, 0, 20, 20);
        addButton.addActionListener(ae -> {
            int xSize = Integer.parseInt(xTextField.getText());
            int ySize = Integer.parseInt(yTextField.getText());
            int percentage = Integer.parseInt(percetage.getText());
            multiscaleModellingPanel.init(xSize, ySize, percentage);
            System.out.println("Cells successfully init");
            f_options.dispose();
        });
        p_options.add(addButton);
        f_options.setVisible(true);
    }

    private void addInclussions() {
        final JFrame f_options = new JFrame();
        f_options.setTitle("Add inclusions");
        f_options.setSize(500, 100);
        f_options.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - f_options.getWidth()) / 2,
            (Toolkit.getDefaultToolkit().getScreenSize().height - f_options.getHeight()) / 2);
        f_options.setResizable(false);
        f_options.setAlwaysOnTop(true);
        JPanel p_options = new JPanel();
        p_options.setOpaque(false);
        f_options.add(p_options);
        p_options.add(new JLabel("Number of inclusions:"));
        final JTextField numberOfInclusionsTextField = new JTextField("10");
        p_options.add(numberOfInclusionsTextField);
        p_options.add(new JLabel("Size of inclusions:"));
        final JTextField sizeOfInclusionsTextField = new JTextField("10");
        p_options.add(sizeOfInclusionsTextField);
        p_options.add(new JLabel("Type:"));
        final JComboBox cb_seconds = new JComboBox(incusionTypes);
        cb_seconds.setSelectedItem(incusionTypes[0]);
        p_options.add(cb_seconds);
        JButton addButton = new JButton("Add");
        addButton.setBounds(25, 0, 20, 20);
        addButton.addActionListener(ae -> {
            int inclusionsToAdd = Integer.parseInt(numberOfInclusionsTextField.getText());
            int inclusionSize = Integer.parseInt(sizeOfInclusionsTextField.getText());
            String selected = (String) cb_seconds.getSelectedItem();
            for (int i = 0; i < inclusionsToAdd; i++) {
                multiscaleModellingPanel.addRandomInclusion(selected, inclusionSize);
            }
            System.out.println("Inclusions successfully added");
            f_options.dispose();
        });
        p_options.add(addButton);
        f_options.setVisible(true);
    }

    private void importFile() {
        final JFrame f_options = new JFrame();
        f_options.setTitle("Import");
        f_options.setSize(300, 60);
        f_options.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - f_options.getWidth()) / 2,
            (Toolkit.getDefaultToolkit().getScreenSize().height - f_options.getHeight()) / 2);
        f_options.setResizable(false);
        JPanel p_options = new JPanel();
        p_options.setOpaque(false);
        f_options.add(p_options);
        p_options.add(new JLabel("Format"));

        JButton importButton = new JButton("Import");
        final JComboBox cb_seconds = new JComboBox(importExportFormats);
        p_options.add(cb_seconds);
        cb_seconds.setSelectedItem(importExportFormats[0]);
        importButton.addActionListener(ae -> {
            String selected = (String) cb_seconds.getSelectedItem();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    if (ImportExportTxt.format.equals(selected)) {
                        multiscaleModellingPanel.setCells(importExportTxt.importFile(file));
                    } else {
                        multiscaleModellingPanel.setCells(importExportBmp.importFile(file));
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                        e.getCause(), "Source", JOptionPane.ERROR_MESSAGE);
                }
            }
            f_options.dispose();
        });
        p_options.add(importButton);
        f_options.setVisible(true);
    }

    private void exportFile() {
        final JFrame f_options = new JFrame();
        f_options.setTitle("Export");
        f_options.setSize(300, 60);
        f_options.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - f_options.getWidth()) / 2,
            (Toolkit.getDefaultToolkit().getScreenSize().height - f_options.getHeight()) / 2);
        f_options.setResizable(false);
        JPanel p_options = new JPanel();
        p_options.setOpaque(false);
        f_options.add(p_options);
        p_options.add(new JLabel("Format"));

        JButton importButton = new JButton("Export");
        final JComboBox cb_seconds = new JComboBox(importExportFormats);
        p_options.add(cb_seconds);
        cb_seconds.setSelectedItem(importExportFormats[0]);
        importButton.addActionListener(ae -> {
            String selected = (String) cb_seconds.getSelectedItem();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    if (ImportExportTxt.format.equals(selected)) {
                        importExportTxt.exportFile(multiscaleModellingPanel.getCells(), file);
                    } else {
                        importExportBmp.exportFile(multiscaleModellingPanel.getCells(), file);
                    }
                    multiscaleModellingPanel.resetSimulation();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                        e.getMessage(), "Source", JOptionPane.ERROR_MESSAGE);
                }
            }
            f_options.dispose();
        });
        p_options.add(importButton);
        f_options.setVisible(true);
    }

    private void addCells() {
        final JFrame f_options = new JFrame();
        f_options.setTitle("Add cells");
        f_options.setSize(300, 60);
        f_options.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - f_options.getWidth()) / 2,
            (Toolkit.getDefaultToolkit().getScreenSize().height - f_options.getHeight()) / 2);
        f_options.setResizable(false);
        f_options.setAlwaysOnTop(true);
        JPanel p_options = new JPanel();
        p_options.setOpaque(false);
        f_options.add(p_options);
        p_options.add(new JLabel("Number of cells:"));
        final JTextField numberOfCellsTextField = new JTextField("20");
        p_options.add(numberOfCellsTextField);
        JButton addButton = new JButton("Add");
        addButton.setBounds(25, 0, 20, 20);
        addButton.addActionListener(ae -> {
            int cellsToAdd = Integer.parseInt(numberOfCellsTextField.getText());
            for (int i = 0; i < cellsToAdd; i++) {
                multiscaleModellingPanel.addRandomCell();
            }
            System.out.println("Cells successfully added");
            f_options.dispose();
        });
        p_options.add(addButton);
        f_options.setVisible(true);
    }

    private void startSimulation() {
        multiscaleModellingPanel.startSimulation();
        System.out.println("Simulation started");
    }

    private void resetSimulation() {
        multiscaleModellingPanel.resetSimulation();
        multiscaleModellingPanel.repaint();
        System.out.println("Simulation reset");
    }

    private void resetWithSelected() {
        final JFrame f_options = new JFrame();
        f_options.setSize(500, 100);
        f_options.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - f_options.getWidth()) / 2,
            (Toolkit.getDefaultToolkit().getScreenSize().height - f_options.getHeight()) / 2);
        f_options.setResizable(false);
        f_options.setAlwaysOnTop(true);
        JPanel p_options = new JPanel();
        p_options.setOpaque(false);
        f_options.add(p_options);
        p_options.add(new JLabel("Type:"));
        final JComboBox cb_seconds = new JComboBox(substructure);
        cb_seconds.setSelectedItem(substructure[0]);
        p_options.add(cb_seconds);
        JButton addButton = new JButton("Add");
        addButton.setBounds(25, 0, 20, 20);
        addButton.addActionListener(ae -> {
            String selected = (String) cb_seconds.getSelectedItem();
            multiscaleModellingPanel.resetWithSelected(selected);
            multiscaleModellingPanel.repaint();
            f_options.dispose();
        });
        p_options.add(addButton);
        f_options.setVisible(true);
    }

    private void colorBoundaries() {
        final JFrame f_options = new JFrame();
        f_options.setTitle("Color boundaries");
        f_options.setSize(300, 60);
        f_options.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - f_options.getWidth()) / 2,
            (Toolkit.getDefaultToolkit().getScreenSize().height - f_options.getHeight()) / 2);
        f_options.setResizable(false);
        f_options.setAlwaysOnTop(true);
        JPanel p_options = new JPanel();
        p_options.setOpaque(false);
        f_options.add(p_options);
        p_options.add(new JLabel("GB size:"));
        final JTextField gbSizeNumber = new JTextField("1");
        p_options.add(gbSizeNumber);
        JButton addButton = new JButton("Add");
        addButton.setBounds(25, 0, 20, 20);
        addButton.addActionListener(ae -> {
            int cellsToAdd = Integer.parseInt(gbSizeNumber.getText());
            double percentage = multiscaleModellingPanel.colorBounaries(cellsToAdd);
            p_options.add(new JLabel("Number of inclusions:" + percentage));
            multiscaleModellingPanel.repaint();

        });
        p_options.add(addButton);
        f_options.setVisible(true);

    }

    private void colorSelectedBoundaries() {
        final JFrame f_options = new JFrame();
        f_options.setTitle("Color selected boundaries");
        f_options.setSize(300, 100);
        f_options.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - f_options.getWidth()) / 2,
            (Toolkit.getDefaultToolkit().getScreenSize().height - f_options.getHeight()) / 2);
        f_options.setResizable(false);
        f_options.setAlwaysOnTop(true);
        JPanel p_options = new JPanel();
        p_options.setOpaque(false);
        f_options.add(p_options);
        p_options.add(new JLabel("GB size:"));
        final JTextField gbSizeNumber = new JTextField("1");
        p_options.add(gbSizeNumber);
        JLabel label = new JLabel("Percentage:");
        p_options.add(label);
        JButton addButton = new JButton("Add");
        addButton.setBounds(25, 0, 20, 20);
        addButton.addActionListener(ae -> {
            int cellsToAdd = Integer.parseInt(gbSizeNumber.getText());
            double percentage = multiscaleModellingPanel.colorSelectedBounaries(cellsToAdd);
            Double d = Double.valueOf(percentage);
            label.setText(label.getText() + " " + d + "%");
            multiscaleModellingPanel.repaint();
        });
        p_options.add(addButton);
        f_options.setVisible(true);
    }

}
