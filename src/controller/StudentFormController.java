package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;
import util.CrudUtil;
import view.TM.CartTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentFormController {
    public TableView<CartTM> tblStudent;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colNic;
    public JFXTextField txtStudentId;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXTextField txtEmail;
    public JFXTextField txtAddress;
    public JFXTextField txtNic;
    public JFXButton btnSave;
    public JFXButton btnDelete;

    public void initialize(){
        btnDelete.setDisable(true);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setTableSelectionData(newValue);
        });

        loadAllData();
    }

    private void setTableSelectionData(CartTM newValue) {
        if (newValue!=null) {
            btnDelete.setDisable(false);
            btnSave.setText("Update");

            txtStudentId.setText(newValue.getId());
            txtName.setText(newValue.getName());
            txtEmail.setText(newValue.getEmail());
            txtContact.setText(newValue.getContact());
            txtAddress.setText(newValue.getAddress());
            txtNic.setText(newValue.getNic());
        }
    }

    private void loadAllData() {
        try {
            ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Student");

            ObservableList<CartTM> list= FXCollections.observableArrayList();

            while (rst.next()) {
                list.add(new CartTM(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6)
                   ));
            }

            tblStudent.setItems(list);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

        }

    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        Student s = new Student(txtStudentId.getText(), txtName.getText(), txtEmail.getText(), txtContact.getText(), txtAddress.getText(), txtNic.getText());

        try {
            boolean b = CrudUtil.executeUpdate("INSERT INTO Student VALUES (?,?,?,?,?,?)", s.getId(), s.getName(), s.getEmail(), s.getContact(), s.getAddress(), s.getNic());

            if (b) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!!!").show();
                clear();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

        }
    }

    public void btnDeleteOnAction (ActionEvent actionEvent){

    }

    public void clear(){
        txtStudentId.clear();
        txtName.clear();
        txtEmail.clear();
        txtContact.clear();
        txtAddress.clear();
        txtNic.clear();
    }

}
