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
import javafx.collections.ListChangeListener;
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
    private Label lblCargando;
    
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
		
    	lblCargando.setVisible(true);
    	btnBuscar.setDisable(true);
    	
    	observableCafeteras.clear();
    	
    	Thread thread = new Thread() {
    		public void run() {
		    	//Control de excepciones no controladas.

		    	try {
		    		if(checkMediaMarkt.isSelected()) MediaMarktDriver.Search(articulo, marcasMarcadas, categoriasPermitidas, observableCafeteras);
		    		if(checkElCorteIngles.isSelected()) ElCorteInglesDriver.Search(articulo, marcasMarcadas, categoriasPermitidas, observableCafeteras);
		    	}
		    	catch(Exception ex) {
		    		showExceptionAlert(ex);
		    	}
		    	

		    	tablaResultados.setItems(observableCafeteras);
		    	
		    	lblCargando.setVisible(false);
		    	btnBuscar.setDisable(false);
    		}
    	};
    	thread.start();
    }

    //Muestra el stacktrace de una excepción no controlada.
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

	//Crea una lista con las marcas marcadas por los checkboxes tras pulsar Buscar.
    public List<String> obtenerMarcasMarcadas() {
    	List<String> marcasMarcadas = new ArrayList<String>();
    	
    	if(chTaurus.isSelected()) marcasMarcadas.add("TAURUS");
    	if(chDelongui.isSelected())marcasMarcadas.add("DELONGUI");
    	if(chBosch.isSelected()) marcasMarcadas.add("BOSCH");
    	if(chPhilips.isSelected()) marcasMarcadas.add("PHILIPS");
    	if(chJata.isSelected()) marcasMarcadas.add("JATA");;
    	if(chKrups.isSelected()) marcasMarcadas.add("KRUPS");
    	if(chJura.isSelected())marcasMarcadas.add("JURA");
    	
    	return marcasMarcadas;
    }

    //Binding de las columnas y Filling del combobox.
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
		
		lblCargando.setVisible(false);
		
		colModelo.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getModelo()));
		colMarca.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getMarca()));
		colMediaMarkt.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getPrecioMediaMarkt()).asObject());
		colElCorteIngles.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getPrecioCorteIngles()).asObject());
	
    	List<Cafetera> resultado = new ArrayList<Cafetera>();
    	observableCafeteras = FXCollections.observableArrayList(resultado);
		observableCafeteras.addListener(new ListChangeListener<Cafetera>() {

			@Override
			public void onChanged(Change<? extends Cafetera> c) {
		    	tablaResultados.setItems(observableCafeteras);
				tablaResultados.refresh();
			}
			
		});
	}
}