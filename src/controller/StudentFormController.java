package controller;

import View.tm.StudentTM;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentFormController {
    public JFXTextField txtNIC;
    public JFXTextField txtEmail;
    public JFXTextField txtAddress;
    public JFXTextField txtStudentName;
    public JFXTextField txtContact;
    public JFXTextField txtStudentId;
    public TableColumn colNIC;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colEmail;
    public TableColumn colStudentName;
    public TableColumn colStudentID;
    public TableView tblStudent;
    public MenuItem Update;
    public MenuItem Delete;


    public void initialize(){

        colStudentID.setCellValueFactory(new PropertyValueFactory("id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colContact.setCellValueFactory(new PropertyValueFactory("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colNIC.setCellValueFactory(new PropertyValueFactory("nic"));


        try {
            loadAllStudent();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllStudent() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Student");
        ObservableList<Student> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Student(
                            result.getString("student_id"),
                            result.getString("student_name"),
                            result.getString("email"),
                            result.getString("contact"),
                            result.getString("address"),
                            result.getString("nic")

                    )
            );
        }
        tblStudent.setItems(obList);

    }




    public void btnAddStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Student stu = new Student(
                txtStudentId.getText(),txtStudentName.getText(), txtEmail.getText(),txtContact.getText(),txtAddress.getText(),txtNIC.getText()
        );

        try {
            if (CrudUtil.execute("INSERT INTO Student VALUES (?,?,?,?,?,?)",stu.getId(),stu.getName(),stu.getEmail(),stu.getContact(),stu.getAddress(),stu.getNic())){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllStudent();
    }


    public void menuUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Student selectedItem = (Student) tblStudent.getSelectionModel().getSelectedItem();

        txtStudentId.setText(selectedItem.getId());
        txtStudentName.setText(selectedItem.getName());
        txtEmail.setText(selectedItem.getEmail());
       txtContact.setText(selectedItem.getContact());
        txtAddress.setText(selectedItem.getAddress());
        txtNIC.setText(selectedItem.getNic());

        loadAllStudent();




    }
    public void menuDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Student selectedItem = (Student) tblStudent.getSelectionModel().getSelectedItem();

        if (CrudUtil.execute("DELETE FROM Student WHERE student_id = ?",selectedItem.getId())){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted.").show();

        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again!").show();
        }    }
   


    }


