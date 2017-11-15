package app;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import automationFramework.ElCorteInglesDriver;
import automationFramework.MediaMarktDriver;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class ControllerViewApp {

    @FXML
    private ComboBox<String> comboArticulo;

    @FXML
    private CheckBox checkMediaMarkt;

    @FXML
    private CheckBox checkElCorteIngles;

    @FXML
    private Button btnBuscar;

    @FXML
    private CheckBox chTaurus;

    @FXML
    private CheckBox chDelongui;

    @FXML
    private CheckBox chBosch;

    @FXML
    private CheckBox chPhilips;

    @FXML
    private CheckBox chJata;

    @FXML
    private CheckBox chKrups;

    @FXML
    private CheckBox chJura;

    @FXML
    private TableView<Cafetera> tablaResultados;

    @FXML
    private TableColumn<Cafetera,String> colModelo;

    @FXML
    private TableColumn<Cafetera,String> colMarca;

    @FXML
    private TableColumn<Cafetera,Double> colMediaMarkt;

    @FXML
    private TableColumn<Cafetera,Double> colElCorteIngles;
    
    ObservableList<Cafetera> observableCafeteras;
	List<String> categoriasPermitidas;
	
    @FXML
    public void click_Buscar(Event event) {
    	List<String> marcasMarcadas= obtenerMarcasMarcadas();
    	String articulo = comboArticulo.getSelectionModel().getSelectedItem();
    	List<Cafetera> resultado = new ArrayList<Cafetera>();
    	
    	try {
    		if(checkMediaMarkt.isSelected()) MediaMarktDriver.Search(articulo, marcasMarcadas, categoriasPermitidas, resultado);
    		if(checkElCorteIngles.isSelected()) ElCorteInglesDriver.Search(articulo, marcasMarcadas, categoriasPermitidas, resultado);
    	}
    	catch(Exception ex) {
    		showExceptionAlert(ex);
    	}
    	observableCafeteras = FXCollections.observableArrayList(resultado);
    	tablaResultados.setItems(observableCafeteras);
    }

	private void showExceptionAlert(Exception ex) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Exception Dialog");
		alert.setHeaderText("Excepción no controlada.");
		
		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("Stacktrace de la excepción:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);
		
		alert.getDialogPane().setExpandableContent(expContent);
		alert.showAndWait();
	}

    public List<String> obtenerMarcasMarcadas() {
    	List<String> marcasMarcadas = new ArrayList<String>();
    	
    	if(chTaurus.isSelected()) marcasMarcadas.add("Taurus");
    	if(chDelongui.isSelected())marcasMarcadas.add("Delongui");
    	if(chBosch.isSelected()) marcasMarcadas.add("Bosch");
    	if(chPhilips.isSelected()) marcasMarcadas.add("Philips");
    	if(chJata.isSelected()) marcasMarcadas.add("Jata");;
    	if(chKrups.isSelected()) marcasMarcadas.add("Krups");
    	if(chJura.isSelected())marcasMarcadas.add("Jura");
    	
    	return marcasMarcadas;
    }

	public void initializeLayout() {
		categoriasPermitidas = new ArrayList<String>();
		
		categoriasPermitidas.add("Cafeteras monodosis");
		categoriasPermitidas.add("Cafeteras express");
		categoriasPermitidas.add("Cafeteras de goteo");
		categoriasPermitidas.add("Cafeteras superautomáticas");
		categoriasPermitidas.add("Cafeteras tradicionales");
		categoriasPermitidas.add("Café y accesorios");
		categoriasPermitidas.add("Hervidores y teteras");
		
		comboArticulo.setItems(FXCollections.observableArrayList(categoriasPermitidas));
		
		colModelo.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getModelo()));
		colMarca.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getMarca()));
		colMediaMarkt.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getPrecioMediaMarkt()).asObject());
		colElCorteIngles.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getPrecioCorteIngles()).asObject());
	}
}