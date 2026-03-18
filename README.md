```md
# Verilog Simulator GUI

A Java-based desktop application that provides a simple graphical interface for writing, compiling, simulating, and visualizing Verilog designs using the Icarus Verilog toolchain.

## 🚀 Features

- Dual editor interface for:
  - Verilog source code
  - Testbench code
- File operations:
  - Open `.v` files
  - Save Verilog and testbench files
- Integrated simulation workflow:
  - Compile using `iverilog`
  - Run simulation using `vvp`
  - View waveforms using `GTKWave`
- Command execution via PowerShell (Windows)

## 🛠️ Tech Stack

- **Language:** Java (Swing)
- **Tools:** Icarus Verilog, GTKWave
- **Concepts:** Process execution, file handling, GUI design

## 📂 Project Structure

```

VerilogSimulatorGUI.java   # Main application file

````

## ⚙️ Requirements

Make sure the following tools are installed and added to your system PATH:

- [Icarus Verilog](http://iverilog.icarus.com/)
- [GTKWave](http://gtkwave.sourceforge.net/)

## ▶️ How to Run

1. Compile the Java program:
   ```bash
   javac VerilogSimulatorGUI.java
````

2. Run the application:

   ```bash
   java VerilogSimulatorGUI
   ```

## 🧪 Usage Workflow

1. Open or write:

   * Verilog module
   * Testbench file

2. Click:

   * **Compile** → Generates `test.out`
   * **Run Simulation** → Executes simulation
   * **View GTKWave** → Opens waveform viewer (`test.vcd`)

## 🔧 How It Works

* Uses `ProcessBuilder` to execute shell commands:

  * `iverilog` for compilation
  * `vvp` for simulation
  * `gtkwave` for waveform visualization
* Captures and prints command output to console
* Uses Swing components for UI:

  * `JTextArea` for code editing
  * `JFileChooser` for file operations
  * `JSplitPane` for layout

## ⚠️ Notes

* Designed primarily for **Windows (PowerShell)** environment
* Ensure `.vcd` file is generated in your testbench for waveform viewing
* Both Verilog and testbench files must be loaded before compilation

## 📌 Future Improvements

* Console output panel inside GUI
* Error highlighting for compilation issues
* Cross-platform shell support (Linux/macOS)
* Syntax highlighting for Verilog
* Project workspace support

## 👨‍💻 Author

Rohit Mishra

```
```
