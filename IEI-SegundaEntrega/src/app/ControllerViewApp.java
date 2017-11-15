package app;

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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    	
    	if(checkMediaMarkt.isSelected()) MediaMarktDriver.Search(articulo, marcasMarcadas, categoriasPermitidas, resultado);
    	if(checkElCorteIngles.isSelected()) ElCorteInglesDriver.Search(articulo, marcasMarcadas, categoriasPermitidas, resultado);
    	
    	observableCafeteras = FXCollections.observableArrayList(MarcaFilter.filtrarPorMarcas(resultado, marcasMarcadas));
    	tablaResultados.setItems(observableCafeteras);
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