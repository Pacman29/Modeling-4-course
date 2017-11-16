import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm {
    private JButton calculate_btn;
    private JRadioButton rbtn_dt;
    private JTextField spinner_dt;
    private JRadioButton rbtn_event;
    private JTextField spinner_requests;
    private JTextField spinner_return;
    private JTextField spinner_A;
    private JTextField spinner_B;
    private JTextField spinner_M;
    private JLabel lbl_result;
    private JPanel panelMain;
    private JTextField spinner_Dev;
    private ButtonGroup RbtnGroupMethod;

    public MainForm() {
        SpinnerNumberModel model = new SpinnerNumberModel(0.0,-1000.,1000.,0.1);

        calculate_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Double a = Double.parseDouble(spinner_A.getText());
                Double b = Double.parseDouble(spinner_B.getText());
                Random.IRandom uni = new Random.UniformDistribution(a,b);

                Double mean = Double.parseDouble(spinner_M.getText());
                Double dev = Double.parseDouble(spinner_Dev.getText());
                Random.IRandom norm = new Random.NormalDistribution(mean,dev);

                double back = Double.parseDouble(spinner_return.getText()) / 100.;
                double dt = Double.parseDouble(spinner_dt.getText());
                int count = Integer.parseInt(spinner_requests.getText());

                int res;
                if(rbtn_dt.isSelected())
                    res = Modeling.MethodDT(new Generator(uni),new Processor(norm),count,back,dt);
                else
                    res = Modeling.MethodEvent(new Generator(uni),new Processor(norm),count,back,dt);

                lbl_result.setText(String.valueOf(res));
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lab04");
        frame.setContentPane(new MainForm().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
