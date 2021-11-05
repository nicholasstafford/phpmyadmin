package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SampleController implements Initializable{

    @FXML
    private TableView<Recette> recetteTable;
    
    @FXML
    private TableColumn<Recette, String> idColumn;
    
    @FXML
    private Label lblID;
    
    private int myID=0;

    @FXML
    private TextField txtPrix;
    
    @FXML
    private Label lblResults;

    @FXML
    private TableColumn<Recette, String> prixColumn;

    @FXML
    private TableColumn<Recette, String> taxColumn;

    @FXML
    private ToggleGroup rdoBTN;

    @FXML
    private TableColumn<Recette, String> totalColumn;

    @FXML
    private RadioButton rdoCanada;

    @FXML
    private TextField txtTotal;

    @FXML
    private ComboBox<String> cboObj;
    
    @FXML
    private ComboBox <Recette> cboID;

    @FXML
    private Button btnCalcTax;
    
    @FXML
    private Button btnCalc;
    
    @FXML
    private Button btnEffacer;

    @FXML
    private Button btnRecommencer;

    @FXML
    private TableColumn<Recette, String> objetColumn;

    @FXML
    private Button btnModifier;

    @FXML
    private TextField txtTax;

    @FXML
    private TableColumn<Recette, String> destinationColumn;

    @FXML
    private RadioButton rdoUSA;

    @FXML
    private Button btnAjouter;

    @FXML
    private RadioButton rdoMex;
    
    private ObservableList<String> list = (ObservableList<String>) FXCollections.observableArrayList("Livre", "Bonbon", "Chemise", "Jouet");
    
    public ObservableList<Recette> recetteData = FXCollections.observableArrayList();
    
    private	ObservableList<Recette> RecetteList;
    
    public ObservableList<Recette> getrecetteData()
    {
    	return recetteData;
    }
    
	// Réinitialiser les champs
	@FXML
	public void reinitialiser()throws ClassNotFoundException, SQLException
	{
		RecetteList=RecuDAO.getAllRecords();
		cboID.setItems(RecetteList);
		
		
	}
    
    @FXML
	public void verifieNum()
	{
		txtPrix.accessibleTextProperty().addListener((observable, oldValue, newValue)->
		{
			if(!newValue.matches("^[0-9)(\\.[0-9]+)?$"))
			{
				txtPrix.setText(newValue.replaceAll("[^\\d*\\]", ""));
			}
		});
	}
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		cboObj.getItems().addAll(list);
		
		objetColumn.setCellValueFactory(new PropertyValueFactory<>("Objet"));
		prixColumn.setCellValueFactory(new PropertyValueFactory<>("Prix"));
		destinationColumn.setCellValueFactory(new PropertyValueFactory<>("Destination"));
		taxColumn.setCellValueFactory(new PropertyValueFactory<>("Tax"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<>("Total"));
		idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
		
		recetteTable.setItems(recetteData);
		
		btnModifier.setDisable(true);
		btnEffacer.setDisable(true);
		btnRecommencer.setDisable(true);
		
		try {
			RecetteList=RecuDAO.getAllRecords();
			cboID.setItems(RecetteList);
			cboID.valueProperty().addListener((obs, oldVal, newVal) ->
	        showRecette(newVal));
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		showRecette(null);
		
		recetteTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> showRecette(newValue));
	}

	
	public void showRecette(Recette recette)
	{
		if(recette != null)
		{
			cboObj.setValue(recette.getObjet());
			txtPrix.setText(Double.toString(recette.getPrix()));
			lblID.setText(String.valueOf(recette.getID()));
			txtTotal.setText(Double.toString(recette.getTotal()));
			
			myID=recette.getID();
			
			if(rdoMex.isSelected())
			{
				rdoMex.setText("Méxique");
				txtTax.setText("9.00$");
			}
			else if(rdoUSA.isSelected())
			{
				rdoUSA.setText("États-Unis");
				txtTax.setText("5.00$");
			}
			else if(rdoCanada.isSelected())
			{
				rdoCanada.setText("Canada");
				txtTax.setText("0.00$");
			}
			btnModifier.setDisable(false);
			btnEffacer.setDisable(false);
			btnRecommencer.setDisable(false);
		}
		else
		{
			clearFields();
		}
	}
	
	@FXML
	void ajouter()throws ClassNotFoundException, SQLException
	{
		Recette tmp = new Recette();

		tmp = new Recette();
		tmp.setPrix(Double.parseDouble(txtPrix.getText()));
		tmp.setObjet(cboObj.getValue());
		tmp.setTotal(Double.parseDouble(txtTotal.getText()));
		String D = "";
		if(rdoMex.isSelected())
		{
			tmp.setTax(Double.parseDouble("9.00"));
			tmp.setDestination("Méxique");
			D = "Méxique";
		}
		else
			if(rdoUSA.isSelected())
			{
				tmp.setTax(Double.parseDouble("5.00"));
				tmp.setDestination("États-Unis");
				D = "États-Unis";
			}
			else
				if(rdoCanada.isSelected())
				{
					tmp.setTax(Double.parseDouble("0.00"));
					tmp.setDestination("Canada");
					D = "Canada";
				}
		
		RecuDAO.insertRecu(cboObj.getValue(), D, Double.parseDouble(txtPrix.getText()), 
				Double.parseDouble(txtTax.getText()), Double.parseDouble(txtTotal.getText())); //ajouter le Double.parseDouble() pour convertir le text en double
		lblResults.setText("Les données de " + cboObj.getValue() + " ont été ajoutées");
		tmp.setTotal(Double.parseDouble(txtTotal.getText()));
		recetteData.add(tmp);
		reinitialiser();
		clearFields();
	}
	
	@FXML
	void clearFields()
	{
		cboObj.setValue(null);
		txtPrix.setText("");
		txtTax.setText("");
		txtTotal.setText("");
		rdoCanada.setSelected(false);
		rdoUSA.setSelected(false);
		rdoMex.setSelected(false);
		lblID.setText("");
	}
	
	@FXML
	void calculs(ActionEvent e)
	{
		Double num1 = Double.parseDouble(txtPrix.getText());
		Double num2 = Double.parseDouble(txtTax.getText());
		Double res = 0.0;
		
		Button btn = (Button)e.getSource();
		
		if(btn.getId().equals("btnCalc"))
		{
			res = num1 + num2;
		}
		txtTotal.setText(Double.toString(res));
	}
	
	@FXML
	void calcAllCan(ActionEvent e)
	{
		RadioButton rdo = (RadioButton)e.getSource();
		if(rdo.getId().contentEquals("rdoCanada"))
		{
		if(rdoCanada.isSelected())
		{
			txtTax.setText(Double.toString(0.0));
		}
		else
			if(rdoUSA.isSelected())
			{
				txtTax.setText(Double.toString(5.0));
			}
			else
				if(rdoMex.isSelected())
				{
					txtTax.setText(Double.toString(9.0));
				}}
		Double num1 = Double.parseDouble(txtPrix.getText());
		Double num2 = Double.parseDouble(txtTax.getText());
		Double res = 0.0;
		
		if(rdo.getId().equals("rdoCanada"))
		{
			res = num1 + num2;
		}
		txtTotal.setText(Double.toString(res));
		
	}
	
	@FXML
	void calcAllUSA(ActionEvent e)
	{
		RadioButton rdo = (RadioButton)e.getSource();
		if(rdo.getId().contentEquals("rdoUSA"))
		{
		if(rdoCanada.isSelected())
		{
			txtTax.setText(Double.toString(0.0));
		}
		else
			if(rdoUSA.isSelected())
			{
				txtTax.setText(Double.toString(5.0));
			}
			else
				if(rdoMex.isSelected())
				{
					txtTax.setText(Double.toString(9.0));
				}}
		Double num1 = Double.parseDouble(txtPrix.getText());
		Double num2 = Double.parseDouble(txtTax.getText());
		Double res = 0.0;
		
		if(rdo.getId().equals("rdoUSA"))
		{
			res = num1 + num2;
		}
		txtTotal.setText(Double.toString(res));
		
	}
	
	@FXML
	void putPrice(ActionEvent e)
	{
		
	}
	
	@FXML
	void calcAllMex(ActionEvent e)
	{
		RadioButton rdo = (RadioButton)e.getSource();
		if(rdo.getId().contentEquals("rdoMex"))
		{
		if(rdoCanada.isSelected())
		{
			txtTax.setText(Double.toString(0.0));
		}
		else
			if(rdoUSA.isSelected())
			{
				txtTax.setText(Double.toString(5.0));
			}
			else
				if(rdoMex.isSelected())
				{
					txtTax.setText(Double.toString(9.0));
				}}
		Double num1 = Double.parseDouble(txtPrix.getText());
		Double num2 = Double.parseDouble(txtTax.getText());
		Double res = 0.0;
		
		if(rdo.getId().equals("rdoMex"))
		{
			res = num1 + num2;
		}
		txtTotal.setText(Double.toString(res));
		
	}
	
	// Change les données dans le tableau
	@FXML
	public void updateRecette() throws ClassNotFoundException, SQLException
	{
		Recette recette = recetteTable.getSelectionModel().getSelectedItem();
		
		recette.setPrix(Double.parseDouble(txtPrix.getText()));
		recette.setTotal(Double.parseDouble(txtTotal.getText()));
		recette.setObjet(cboObj.getValue());
		recette.setTax(Double.parseDouble(txtTax.getText()));
			if(rdoCanada.isSelected())
			{
				recette.setDestination("Canada");
			}
			else
				if(rdoUSA.isSelected());
				{
					recette.setDestination("États-Unis");
				}
				if(rdoMex.isSelected())
					{
						recette.setDestination("Méxique");
					}
		
		String Destination = "";
		if(rdoCanada.isSelected())
		{
		Destination = "Canada";
		}
		else
			if(rdoUSA.isSelected())
			{
				Destination = "États-Unis";
			}
			else
				if(rdoMex.isSelected())
				{
					Destination = "Méxique";
				}
		RecuDAO.updateRecu(myID, cboObj.getValue(), Destination, Double.parseDouble(txtPrix.getText()), Double.parseDouble(txtTax.getText()), Double.parseDouble(txtTotal.getText()));
		lblResults.setText("Les données de " + cboObj.getValue() + " ont été mises à jour");
		reinitialiser();
		recetteTable.refresh();
	}
	
	@FXML
	public void deleteRecette() throws ClassNotFoundException, SQLException
	{
		int selectedIndex = recetteTable.getSelectionModel().getSelectedIndex();
		if(selectedIndex >= 0)
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Effacer");
			alert.setContentText("Confirer la suppression!");
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get()==ButtonType.OK)
				recetteTable.getItems().remove(selectedIndex); 
		}
		RecuDAO.deleteRecetteById(Integer.parseInt(lblID.getText()));
		lblResults.setText("Les données ont été effacées");
		reinitialiser();
	}
	
	public File getRecetteFilePath()
	{
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		String filePath = prefs.get("filePath", null);
		
		if(filePath != null)
		{
			return new File(filePath);
		}
		
		else
		{
			return null;
		}
	}
	
	public void setRecetteFilePath(File file)
	{
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		if(file != null)
		{
			prefs.put("filePath", file.getPath());
		}
		
		else
		{
			prefs.remove("filePath");
		}
	}
	
	public void loadRecetteDataFromFile(File file)
	{
		try {
			
			JAXBContext context = JAXBContext.newInstance(RecetteListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			
			RecetteListWrapper wrapper = (RecetteListWrapper) um.unmarshal(file);
			recetteData.clear();
			recetteData.addAll(wrapper.getRecettes());
			
			setRecetteFilePath(file);
			
			
			Stage pStage=(Stage) recetteTable.getScene().getWindow();
			pStage.setTitle(file.getName());
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Les données n'ont pas été trouvées");
			alert.setContentText("Les données ne pouvaient pas être trouvées dans le fichier : \n" +  file.getPath());
			alert.showAndWait();
		}
	}
	
	public void saveEtudiantDataToFile(File file)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(RecetteListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			RecetteListWrapper wrapper = new RecetteListWrapper();
			wrapper.setRecettes(recetteData);
			
			m.marshal(wrapper, file);
			
			setRecetteFilePath(file);
			
			Stage pStage = (Stage) recetteTable.getScene().getWindow();
			pStage.setTitle(file.getName());
		}
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erruer");
			alert.setHeaderText("Données non sauvegardées");
			alert.setContentText("Les données ne pouvaient pas être sauvegardées dans le fichier: \n" + file.getPath());
			alert.showAndWait();
		}
	}
	@FXML
	private void handlNew()
	{
		getrecetteData().clear();
		setRecetteFilePath(null);
	}
	
	@FXML
	private void handleOpen()
	{
		FileChooser fileChooser = new FileChooser();
		
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML file (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		File file = fileChooser.showOpenDialog(null);
		
		if(file != null)
		{
			loadRecetteDataFromFile(file);
		}
	}
	
	@FXML
	private void handleSave()
	{
		File etudiantFile = getRecetteFilePath();
		
		if (etudiantFile != null)
		{
			saveEtudiantDataToFile(etudiantFile);
		}
		else
		{
			handleSaveAs();
		}
	}
	
	@FXML
	private void handleSaveAs()
	{
		FileChooser fileChooser = new FileChooser();
		
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		File file = fileChooser.showSaveDialog(null);
		
		if(file != null)
		{
			if(!file.getPath().endsWith(".xml"))
			{
				file = new File(file.getPath() + ".xml");
			}
			saveEtudiantDataToFile(file);
		}
	}
	
	@FXML
	void handleStats()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("TotalStat.FXML"));
			AnchorPane pane = loader.load();
			Scene scene = new Scene(pane);
			TotalStat recettestat = loader.getController();
			recettestat.SetStats(recetteData);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Statistiques");
			stage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}


