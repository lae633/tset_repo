package com.jdc.View;

import javafx.scene.control.Dialog;

import java.io.File;
import java.util.List;

import com.jdc.model.Employee;
import com.jdc.model1.EmployeeModel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;


public class CollectionController {

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtPhone;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtSalary;

	@FXML
	private Label lblTotal;

	@FXML
	private TableView<Employee> tblEmployeeList;
	
	@FXML
	private TableColumn<Employee, Integer> colSalary;

	private EmployeeModel model;
	private Employee emp;

	@FXML
	private void initialize() {
		model = EmployeeModel.getInstance();

		createContextMenu();
		tblEmployeeList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		search();
	}

	private void createContextMenu() {
		MenuItem edit = new MenuItem("Edit");
		edit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				edit();
			}
		});

		MenuItem delete = new MenuItem("Delete");
		delete.setOnAction(new DeleteController());

		ContextMenu menu = new ContextMenu(edit, delete);
		tblEmployeeList.setContextMenu(menu);
		
		
	}

	private void edit() {
		Employee selectedItem = tblEmployeeList.getSelectionModel().getSelectedItem();
		emp = selectedItem;
		txtName.setText(selectedItem.getName());
		txtEmail.setText(selectedItem.getMail());
		txtPhone.setText(selectedItem.getPhone());
		txtSalary.setText(String.valueOf(selectedItem.getSalary()));
	}

	@FXML
	void add() {

		try {
			String name = txtName.getText();
			if (name.isEmpty()) {
				throw new RuntimeException("Name cannot be empty!");
			}

			String email = txtEmail.getText();
			 if (email.isEmpty()) {
				throw new RuntimeException("Email cannot be empty!");

			}
			String phone = txtPhone.getText();
			 if (phone.isEmpty()) {
				throw new RuntimeException("Phone cannot be empty!");
			}
			String salary = txtSalary.getText();
			 if (salary.isEmpty()) {
				throw new RuntimeException("Salary cannot be empty!");
			}else {
				if (Integer.parseInt(salary)<=0) {
					throw new RuntimeException("Salary must be greater than o!");
				}
			}
			if (null == emp) {
				emp = new Employee();
			}
			emp.setName(name);
			emp.setMail(email);
			emp.setPhone(phone);
			emp.setSalary(Integer.parseInt(salary));

			model.save(emp);
			emp=null;
			clear();
			search();

		} catch (NumberFormatException e) {
			errorBox(e);
		} catch (Exception e) {
			errorBox(e);
		}

	}

	private void errorBox(Exception e) {
		Dialog<String> dialog = new Dialog<String>();
		dialog.setTitle("Sthin Wrong!");
		dialog.setContentText(e instanceof NumberFormatException? "Salary must be digit": e.getMessage());
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.show();

	}

	private void clear() {
		txtName.clear();
		txtEmail.clear();
		txtPhone.clear();
		txtSalary.clear();
		emp = null;
	}

	@FXML
	void search() {
		tblEmployeeList.getItems().clear();
		List<Employee> list = model.find(txtName.getText(), txtEmail.getText(), txtPhone.getText(),
				txtSalary.getText().isEmpty() ? 0 : Integer.parseInt(txtSalary.getText()));
		tblEmployeeList.getItems().addAll(list);
		setTotalLabel(list);
	}

	private <T> void setTotalLabel(List<T> list) {

		String text = "Total: ";

		if (list.isEmpty()) {
			lblTotal.setText(text.concat("0 item"));
		} else if (list.size() == 1) {
			lblTotal.setText(text.concat("1 item"));
		} else {
			lblTotal.setText(text.concat(list.size() + " items"));
		}
	}
	
	
	@FXML
	public void upload() {
		FileChooser fc=new FileChooser();
		fc.setTitle("Choose File");
		fc.setInitialDirectory(new File(System.getProperty("user.home", "Desktop")));
		FileChooser.ExtensionFilter filter=new FileChooser.ExtensionFilter("TEXT File", "*.txt", "*.csv", "*.tsv");
		fc.getExtensionFilters().add(filter);
		File file=fc.showOpenDialog(lblTotal.getScene().getWindow());
		model.upload(file);
		search();
	}

	private class DeleteController implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {

			List<Employee> list = tblEmployeeList.getSelectionModel().getSelectedItems();
			model.delete(list);
			search();

		}

	}

}
