package app;

import java.util.ArrayList;
import java.util.List;

import automationFramework.MediaMarktDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

public class ControllerViewApp {

    @FXML
    private ComboBox<String> comboArticulo;

    @FXML
    private CheckBox checkMediaMarkt;

    @FXML
    private CheckBox checkFnac;

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
    public void click_Buscar(Event event) {
    	List<String> marcasMarcadas= obtenerMarcasMarcadas();
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
		comboArticulo.setItems(FXCollections.observableArrayList(MediaMarktDriver.getCategorias()));		
	}
}