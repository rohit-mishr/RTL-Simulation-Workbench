import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
//import java.awt.event.*;
import java.io.*;

public class VerilogSimulatorGUI {
    private JTextArea verilogTextArea, testbenchTextArea;
    private JFrame frame;
    private File verilogFile = null, testbenchFile = null;

    public VerilogSimulatorGUI() {
        frame = new JFrame("Verilog Project Manager");
        frame.setSize(900, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Verilog Code Editor
        verilogTextArea = new JTextArea();
        JScrollPane verilogScrollPane = new JScrollPane(verilogTextArea);
        verilogScrollPane.setBorder(BorderFactory.createTitledBorder("Verilog Code"));

        // Testbench Editor
        testbenchTextArea = new JTextArea();
        JScrollPane testbenchScrollPane = new JScrollPane(testbenchTextArea);
        testbenchScrollPane.setBorder(BorderFactory.createTitledBorder("Testbench"));

        // Split Pane for Side-by-Side View
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, verilogScrollPane, testbenchScrollPane);
        splitPane.setDividerLocation(450);
        frame.add(splitPane, BorderLayout.CENTER);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem openVerilog = new JMenuItem("Open Verilog File");
        JMenuItem openTestbench = new JMenuItem("Open Testbench File");
        JMenuItem saveVerilog = new JMenuItem("Save Verilog File");
        JMenuItem saveTestbench = new JMenuItem("Save Testbench File");

        openVerilog.addActionListener(e -> openFile(true));
        openTestbench.addActionListener(e -> openFile(false));
        saveVerilog.addActionListener(e -> saveFile(true));
        saveTestbench.addActionListener(e -> saveFile(false));

        fileMenu.add(openVerilog);
        fileMenu.add(openTestbench);
        fileMenu.add(saveVerilog);
        fileMenu.add(saveTestbench);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        JButton compileButton = new JButton("Compile");
        JButton runButton = new JButton("Run Simulation");
        JButton viewWaveform = new JButton("View GTKWave");

        compileButton.addActionListener(e -> compileVerilog());
        runButton.addActionListener(e -> runCommand("vvp test.out"));
        viewWaveform.addActionListener(e -> runCommand("gtkwave test.vcd"));

        buttonPanel.add(compileButton);
        buttonPanel.add(runButton);
        buttonPanel.add(viewWaveform);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void openFile(boolean isVerilog) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Verilog Files", "v"));
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                if (isVerilog) {
                    verilogFile = selectedFile;
                    verilogTextArea.read(reader, null);
                } else {
                    testbenchFile = selectedFile;
                    testbenchTextArea.read(reader, null);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void saveFile(boolean isVerilog) {
        File targetFile = isVerilog ? verilogFile : testbenchFile;
        if (targetFile == null) {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                targetFile = fileChooser.getSelectedFile();
                if (isVerilog) verilogFile = targetFile;
                else testbenchFile = targetFile;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile))) {
            if (isVerilog) verilogTextArea.write(writer);
            else testbenchTextArea.write(writer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void compileVerilog() {
        if (verilogFile == null || testbenchFile == null) {
            JOptionPane.showMessageDialog(frame, "Please open or save both Verilog and Testbench files first.");
            return;
        }
        String command = "iverilog -o test.out " + verilogFile.getAbsolutePath() + " " + testbenchFile.getAbsolutePath();
        runCommand(command);
    }

    private void runCommand(String command) {
        try {
            System.out.println("Running: " + command);

            ProcessBuilder builder = new ProcessBuilder("powershell.exe", "/c", command);
            builder.redirectErrorStream(true);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            process.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new VerilogSimulatorGUI();
    }
}