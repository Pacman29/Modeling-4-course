import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.linear.GaussianSolver;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mainform{

    private JButton addState_btn;
    private JButton calculate_btn;
    private JButton delState_btn;
    private JTable state_table;
    private JTable result_table;
    private JPanel panelMain;
    private JScrollPane Result_scrl;
    private JScrollPane state_scrl;


    private int state_count = 0;

    public Mainform(){
        DefaultTableModel stateModel = (DefaultTableModel) state_table.getModel();
        DefaultTableModel resultModel = (DefaultTableModel) result_table.getModel();
        delState_btn.setEnabled(false);

        addState_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state_count ++;
                delState_btn.setEnabled(true);
                stateModel.setColumnCount(state_count);
                stateModel.setRowCount(state_count);

                for(int i = 0; i<state_count-1; ++i)
                    stateModel.setValueAt(0.0, i,state_count-1);

                for(int i = 0; i<state_count; ++i)
                    stateModel.setValueAt(0.0, state_count-1,i);

                resultModel.setColumnCount(state_count);
                if(state_count == 1)
                    resultModel.setRowCount(2);
            }
        });
        delState_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state_count--;
                if(state_count == 0){
                    delState_btn.setEnabled(false);
                }
                stateModel.setColumnCount(state_count);
                stateModel.setRowCount(state_count);

                resultModel.setColumnCount(state_count);

                if(state_count == 0)
                    resultModel.setRowCount(0);

            }
        });
        calculate_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel stateModel = (DefaultTableModel) state_table.getModel();
                DefaultTableModel resultModel = (DefaultTableModel) result_table.getModel();

                double[][] states = new double[state_count][state_count];
                double[] r = new double[state_count];
                for(int i = 0; i< state_count; ++i){
                    for(int j = 0; j< state_count; ++j){
                        states[i][j] += new Double(stateModel.getValueAt(i,j).toString());
                        states[i][i] -= new Double(stateModel.getValueAt(j,i).toString());
                    }
                    r[i] = 0.;
                }

                double[] lSum = new double[state_count];
                for(int i = 0; i < state_count; ++i){
                    for(int j = 0; j < state_count; ++j){
                        lSum[i] += new Double(stateModel.getValueAt(j,i).toString());
                    }
                }

                for(int i = 0; i<state_count; ++i)
                    states[0][i] = 1;

                r[0] = 1;

                GaussianSolver gaussianSolver = new GaussianSolver(Matrix.from2DArray(states));
                Vector results = gaussianSolver.solve(Vector.fromArray(r));

                for (int i = 0; i<state_count; ++i){
                    resultModel.setValueAt(results.get(i),0,i);
                    resultModel.setValueAt(results.get(i)/lSum[i],1,i);
                }

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lab02");
        frame.setContentPane(new Mainform().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
