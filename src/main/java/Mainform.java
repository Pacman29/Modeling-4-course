import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Mainform {
    private JPanel panelMain;
    private JTable tbl_algo;
    private JTable tbl_tabl;
    private JTable tbl_custom;
    private JButton btnGenerate;
    private JSpinner cbxRange;
    private JButton btnCheck;
    private JLabel algo1;
    private JLabel table1;
    private JLabel userTestLabel;
    private JSpinner usrRange;
    private JLabel algo2;
    private JLabel algo3;
    private JLabel table2;
    private JLabel table3;

    private Map<String,RandomGenerator> algorithmsRandomMap = new HashMap<>();
    private Map<String,RandomGenerator> tablesRandomMap = new HashMap<>();

    private Map<String,JLabel> algorithmsLablesMap= new HashMap<>();
    private Map<String,JLabel> tablesLablesMap= new HashMap<>();


    public Mainform() {
        algorithmsRandomMap.put("1",new RandomGenerator(new AlgorithmicRandom(),0,9));
        algorithmsRandomMap.put("2",new RandomGenerator(new AlgorithmicRandom(),10,99));
        algorithmsRandomMap.put("3",new RandomGenerator(new AlgorithmicRandom(),100,999));

        tablesRandomMap.put("1",new RandomGenerator(new TableRandom(),0,9));
        tablesRandomMap.put("2",new RandomGenerator(new TableRandom(),10,99));
        tablesRandomMap.put("3",new RandomGenerator(new TableRandom(),100,999));

        algorithmsLablesMap.put("1",algo1);
        algorithmsLablesMap.put("2",algo2);
        algorithmsLablesMap.put("3",algo3);

        tablesLablesMap.put("1",table1);
        tablesLablesMap.put("2",table2);
        tablesLablesMap.put("3",table3);

        ((DefaultTableModel) tbl_algo.getModel()).setColumnCount(3);
        ((DefaultTableModel) tbl_tabl.getModel()).setColumnCount(3);
        ((DefaultTableModel) tbl_custom.getModel()).setColumnCount(1);
        ((DefaultTableModel) tbl_custom.getModel()).setRowCount(10);

        tbl_algo.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("*");
        tbl_algo.getTableHeader().getColumnModel().getColumn(1).setHeaderValue("**");
        tbl_algo.getTableHeader().getColumnModel().getColumn(2).setHeaderValue("***");
        tbl_tabl.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("*");
        tbl_tabl.getTableHeader().getColumnModel().getColumn(1).setHeaderValue("**");
        tbl_tabl.getTableHeader().getColumnModel().getColumn(2).setHeaderValue("***");
        tbl_custom.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("Пользовательская последовательность");
        cbxRange.getModel().setValue(10);
        usrRange.getModel().setValue(10);

        btnGenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int size = (Integer) cbxRange.getModel().getValue();
                DefaultTableModel modelTbl_algo = ((DefaultTableModel) tbl_algo.getModel());
                DefaultTableModel modelTbl_tabl =((DefaultTableModel) tbl_tabl.getModel());

                modelTbl_algo.setRowCount(size);
                modelTbl_tabl.setRowCount(size);

                final int[] i = {0};
                algorithmsRandomMap.forEach( (key, value) -> {
                    List<Integer> seq = value.generateRandomArray(size);
                    for (int j = 0; j<size; ++j)
                        modelTbl_algo.setValueAt(seq.get(j),j, i[0]);
                    i[0]++;

                    algorithmsLablesMap.get(key).setText("<html>"+value.getTestLog()+"</html>");
                });

                i[0] = 0;
                tablesRandomMap.forEach( (key, value) -> {
                    List<Integer> seq = value.generateRandomArray(size);
                    for (int j = 0; j<size; ++j)
                        modelTbl_tabl.setValueAt(seq.get(j),j, i[0]);
                    i[0]++;

                    tablesLablesMap.get(key).setText("<html>"+value.getTestLog()+"</html>");
                });

            }
        });
        usrRange.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                DefaultTableModel modelTbl = ((DefaultTableModel) tbl_custom.getModel());
                modelTbl.setRowCount((Integer) usrRange.getValue());
            }
        });
        btnCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> seq = new ArrayList<>();
                DefaultTableModel modelTbl = ((DefaultTableModel) tbl_custom.getModel());
                for(int i = 0; i< (int)usrRange.getValue(); ++i){
                    seq.add(Integer.valueOf((String) modelTbl.getValueAt(i,0)));
                }
                ICreterea cretereas[] = new ICreterea[] {new CorrelationCriterea(),new DifferenceEnthropyCriterea(),
                        new EnthropyCriterea(), new Hi2Criteria()};

                String logTest = "";
                Integer min = Collections.min(seq);
                Integer max = Collections.max(seq);
                for (ICreterea creterea: cretereas){
                    creterea.setRange(min,max);
                    logTest += "<br>"+creterea.name() + ": "+ String.format("%.3f",creterea.calculate(seq))+"</br>";
                }

                userTestLabel.setText("<html>"+logTest+"</html>");
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
