import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mainform {
    private JPanel panelMain;
    private JTable tbl_algo;
    private JTable tbl_tabl;
    private JTable tbl_custom;
    private JButton btnGenerate;
    private JSpinner cbxRange;
    private JButton btnCheck;
    private JLabel lbl1;
    private JLabel lbl10;
    private JLabel lbl100;
    private JLabel lbl_custom;

    private IRandom algRandom1;
    private IRandom tabRandom1;
    private IRandom algRandom10;
    private IRandom tabRandom10;
    private IRandom algRandom100;
    private IRandom tabRandom100;

    public Mainform() {
        ((DefaultTableModel) tbl_algo.getModel()).setColumnCount(3);
        ((DefaultTableModel) tbl_tabl.getModel()).setColumnCount(3);
        ((DefaultTableModel) tbl_custom.getModel()).setColumnCount(1);

        tbl_algo.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("*");
        tbl_algo.getTableHeader().getColumnModel().getColumn(1).setHeaderValue("**");
        tbl_algo.getTableHeader().getColumnModel().getColumn(2).setHeaderValue("***");
        tbl_tabl.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("*");
        tbl_tabl.getTableHeader().getColumnModel().getColumn(1).setHeaderValue("**");
        tbl_tabl.getTableHeader().getColumnModel().getColumn(2).setHeaderValue("***");
        tbl_custom.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("Пользовательская последовательность");
        cbxRange.getModel().setValue(10);

        int millis = (int) (System.currentTimeMillis() % 1000);

        algRandom1 = new AlgorithmicRandom(millis);
        algRandom1.setRange(0,9);
        algRandom10 = new AlgorithmicRandom(millis);
        algRandom10.setRange(10,99);
        algRandom100 = new AlgorithmicRandom(millis);
        algRandom100.setRange(100,999);

        tabRandom1 = new TableRandom(millis);
        tabRandom1.setRange(0,9);
        tabRandom10 = new TableRandom(millis);
        tabRandom10.setRange(10,99);
        tabRandom100 = new TableRandom(millis);
        tabRandom100.setRange(100,999);

        btnGenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int size = (Integer) cbxRange.getModel().getValue();
                DefaultTableModel modelTbl_algo = ((DefaultTableModel) tbl_algo.getModel());
                DefaultTableModel modelTbl_tabl =((DefaultTableModel) tbl_tabl.getModel());
                DefaultTableModel modelTbl_custom =((DefaultTableModel) tbl_custom.getModel());

                modelTbl_algo.setRowCount(size);
                modelTbl_tabl.setRowCount(size);
                modelTbl_custom.setRowCount(size);

                for(int i = 0; i< size; ++i){
                    modelTbl_algo.setValueAt(algRandom1.getNext(),i,0);
                    modelTbl_algo.setValueAt(algRandom10.getNext(),i,1);
                    modelTbl_algo.setValueAt(algRandom100.getNext(),i,2);
                    modelTbl_tabl.setValueAt(tabRandom1.getNext(),i,0);
                    modelTbl_tabl.setValueAt(tabRandom10.getNext(),i,1);
                    modelTbl_tabl.setValueAt(tabRandom100.getNext(),i,2);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lab03");
        frame.setContentPane(new Mainform().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
