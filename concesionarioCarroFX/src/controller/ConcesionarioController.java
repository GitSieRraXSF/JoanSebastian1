package controller;

import java.sql.Connection;

import application.Main;
import data.CarroDAO;
import data.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Carro;

public class ConcesionarioController {

    @FXML
    private TableColumn<Carro, Integer> columnCantidadD;

    @FXML
    private TableColumn<Carro, String> columnColorE;

    @FXML
    private TableColumn<Carro, String> columnMarcaD;

    @FXML
    private TableColumn<Carro, String> columnMarcaE;

    @FXML
    private TableColumn<Carro, String> columnModeloD;

    @FXML
    private TableColumn<Carro, String> columnModeloE;

    @FXML
    private TableView<Carro> tableCarrosDisponibles;

    @FXML
    private TableView<Carro> tableCarrosEliminados;
 
    // Singleton Connection
  	private Connection connection = DBConnection.getInstance().getConnection();
  	// BookDAO Instance
  	private CarroDAO carroDAO = new CarroDAO(connection);

  	@FXML
  	public void initialize() {
  		
  		//Tabla de los carros disponibles
  		ObservableList<Carro> carrosDisponibles = FXCollections.observableArrayList();
  		for (Carro carro : carroDAO.fetchDisponibles()) {
			carrosDisponibles.add(carro);
		}
  		
  		columnCantidadD.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
  		columnMarcaD.setCellValueFactory(new PropertyValueFactory<>("marca"));
  		columnModeloD.setCellValueFactory(new PropertyValueFactory<>("modelo"));
  		
  		tableCarrosDisponibles.setItems(carrosDisponibles);
  		
  		//Tabla de los carros eliminados
  		ObservableList<Carro> carrosEliminados = FXCollections.observableArrayList();
  		for (Carro carro : carroDAO.fetchEliminados()) {
  			carrosEliminados.add(carro);
		}
  		
  		columnColorE.setCellValueFactory(new PropertyValueFactory<>("color"));
  		columnMarcaE.setCellValueFactory(new PropertyValueFactory<>("marca"));
  		columnModeloE.setCellValueFactory(new PropertyValueFactory<>("modelo"));
  		
  		tableCarrosEliminados.setItems(carrosEliminados);
  	}
  	
    @FXML
    void eliminar(ActionEvent event) {

    	if (!tableCarrosDisponibles.getSelectionModel().isEmpty()) {
    		Carro carro = tableCarrosDisponibles.getSelectionModel().getSelectedItem();
    		carroDAO.eliminar(carro.getReferencia());
    		initialize();
    	} else {
    		Main.showAlert("Seleccione un registro", "Seleccione un registro", "Debe seleccionar un dato de la tabla", AlertType.WARNING);
    	}
    	initialize();
    }

    @FXML
    void recuperar(ActionEvent event) {

    	if (!tableCarrosEliminados.getSelectionModel().isEmpty()) {
    		Carro carro = tableCarrosEliminados.getSelectionModel().getSelectedItem();
    		carroDAO.recuperar(carro.getReferencia());
    		initialize();
    	} else {
    		Main.showAlert("Seleccione un registro", "Seleccione un registro", "Debe seleccionar un dato de la tabla", AlertType.WARNING);
    	}
    	initialize();
    }
}