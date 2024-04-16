
import javax.swing.*;
        import java.awt.event.*;
        import java.awt.*;

public class SudokuSolverGUI extends JFrame implements ActionListener {
    private JTextField[][] gridFields;
    private JButton solveButton;

    public SudokuSolverGUI() {
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 9));

        gridFields = new JTextField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gridFields[i][j] = new JTextField(1);
                add(gridFields[i][j]);
            }
        }

        solveButton = new JButton("Solve");
        solveButton.addActionListener(this);
        add(solveButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == solveButton) {
            int[][] grid = new int[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String value = gridFields[i][j].getText();
                    if (!value.isEmpty()) {
                        grid[i][j] = Integer.parseInt(value);
                    } else {
                        grid[i][j] = 0;
                    }
                }
            }

            if (solve(grid, 0, 0)) {
                updateGrid(grid);
                JOptionPane.showMessageDialog(this, "Sudoku solved successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No solution found.");
            }
        }
    }

    public boolean isValid(int[][] grid, int r, int c, int k) {
        return !isInRow(grid, r, k) && !isInColumn(grid, c, k) && !isInBox(grid, r - r % 3, c - c % 3, k);
    }

    public boolean isInRow(int[][] grid, int r, int k) {
        for (int i = 0; i < 9; i++) {
            if (grid[r][i] == k) {
                return true;
            }
        }
        return false;
    }

    public boolean isInColumn(int[][] grid, int c, int k) {
        for (int i = 0; i < 9; i++) {
            if (grid[i][c] == k) {
                return true;
            }
        }
        return false;
    }

    public boolean isInBox(int[][] grid, int startRow, int startCol, int k) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i + startRow][j + startCol] == k) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean solve(int[][] grid, int r, int c) {
        if (r == 9) {
            return true;
        } else if (c == 9) {
            return solve(grid, r + 1, 0);
        } else if (grid[r][c] != 0) {
            return solve(grid, r, c + 1);
        } else {
            for (int k = 1; k <= 9; k++) {
                if (isValid(grid, r, c, k)) {
                    grid[r][c] = k;
                    if (solve(grid, r, c + 1)) {
                        return true;
                    }
                    grid[r][c] = 0;
                }
            }
            return false;
        }
    }

    public void updateGrid(int[][] grid) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gridFields[i][j].setText(Integer.toString(grid[i][j]));
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuSolverGUI sudokuSolverGUI = new SudokuSolverGUI();
            int[][] inputGrid = {
                    {0, 0, 4, 0, 5, 0, 0, 0, 0},
                    {9, 0, 0, 7, 3, 4, 6, 0, 0},
                    {0, 0, 3, 0, 2, 1, 0, 4, 9},
                    {0, 3, 5, 0, 9, 0, 4, 8, 0},
                    {0, 9, 0, 0, 0, 0, 0, 3, 0},
                    {0, 7, 6, 0, 1, 0, 9, 2, 0},
                    {3, 1, 0, 9, 7, 0, 2, 0, 0},
                    {0, 0, 9, 1, 8, 2, 0, 0, 3},
                    {0, 0, 0, 0, 6, 0, 1, 0, 0}
            };
            sudokuSolverGUI.setInitialGrid(inputGrid);
        });
    }

    public void setInitialGrid(int[][] grid) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] != 0) {
                    gridFields[i][j].setText(Integer.toString(grid[i][j]));
                    gridFields[i][j].setEditable(false);
                }
            }
        }
    }
}