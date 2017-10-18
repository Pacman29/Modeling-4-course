import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class Controller {
    public Button add_state_btn;
    public Button del_state_btn;
    public Button calculate_btn;
    public TableView state_table;
    public TableColumn side_header_state_table;
    public TableColumn slide_header_result_table;
    public TableView result_table;

    @FXML
    public void initialize(){
        add_state_btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                List columns = result_table.getColumns();
                list rows = result_table
                String header = String.format("S%d",columns.size());
                TableColumn col = new TableColumn(header);
                columns.get(0);

                columns.add(col);
                columns = state_table.getColumns();
                columns.add(col);
            }
        });
    }
}
