package progettoPON;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class JTrovaAScuola extends Application {
	
	GridPane area = new GridPane();

	Button pCerca = new Button("CERCA");

	Label lOra = new Label("Inserisci ora:");
	Label lGiorni = new Label("Giorni");
	Label lRisposta = new Label();
	
	TextField tOra = new TextField();
	TextField tGiorni = new TextField();

	ComboBox<String> comboClassi = new ComboBox<>();
	ComboBox<String> comboDocenti = new ComboBox<>();
	
	// Spinner spin=new Spinner();

	RadioButton classe = new RadioButton("Classe");
	RadioButton docente = new RadioButton("Docente");

	ArrayList<String[]> righeFile = new ArrayList<>();
	
	boolean trovato = false, trovatoC = false;
	int pos = 3, c = 0;
		
	@Override
	public void start(Stage Finestra) throws Exception {
		ToggleGroup gruppo = new ToggleGroup();
		classe.setToggleGroup(gruppo);
		docente.setToggleGroup(gruppo);
		comboClassi.setDisable(true);
		docente.setSelected(true);
		area.add(lOra, 0, 0);
		area.add(tOra, 1, 0);
		area.add(lGiorni, 0, 1);
		area.add(tGiorni, 1, 1);
		area.add(comboDocenti, 1, 2);
		area.add(docente, 0, 2);
		area.add(pCerca, 0, 4, 2, 1);
		area.add(comboClassi, 1, 3);
		area.add(classe, 0, 3);
		area.add(lRisposta, 0, 5, 2, 1);
		
		
		lRisposta.setMaxWidth(Integer.MAX_VALUE);
		pCerca.setMaxWidth(Integer.MAX_VALUE);
		
		// area.add(spin, 0, 3);

		Scene scena = new Scene(area, 250, 220);
		Finestra.setScene(scena);
		Finestra.setTitle("Bot Telegram");
		Finestra.show();
		Finestra.setResizable(false);
		scena.getStylesheets().add("progettoPON/ricerca.css");
		area.setId("pannello");
		pCerca.setOnAction(e -> cerca());
		classe.setOnAction(e -> classe());
		docente.setOnAction(e -> classe());
		
		String rigaLetta;
		
		try {
			FileInputStream fis = new FileInputStream(
					"C:\\Users\\alessandromaggio\\Desktop\\Workspace\\jTrovaAScuola\\OreClassiDocenti.csv");
			InputStreamReader isr = new InputStreamReader(fis, "ISO8859-1");
			BufferedReader input = new BufferedReader(isr);

			do {
				rigaLetta = input.readLine();
				if (rigaLetta != null) {
					trovato = trovatoC = false;
					righeFile.add(rigaLetta.split(";"));
					for (int i = 0; i<comboDocenti.getItems().size(); i++) {
						if ((righeFile.get(c)[0]).equals(comboDocenti.getItems().get(i))) {
							trovato = true;
							break;
						}
					}
					for (int i = 0; i<comboClassi.getItems().size(); i++) {
						if ((righeFile.get(c)[3]).equals(comboClassi.getItems().get(i))) {
							trovatoC = true;
							break;
						}
					}
					if (!trovato) {
						comboDocenti.getItems().add(righeFile.get(c)[0]);
					}
					if (!trovatoC) {
						comboClassi.getItems().add(righeFile.get(c)[3]);
					}
					c++;
				}
			} while (rigaLetta != null);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cerca() {
		boolean trovato=false;
		
		String ora = tOra.getText();
		String giorno = tGiorni.getText();
		
		for(int i = 0; i<righeFile.size(); i++) {
			// docente;giorno;ora;classe;sezione
			// 0,1,2,3,4
			if (righeFile.get(i)[pos].equals(comboClassi.getValue()) && righeFile.get(i)[2].equals(ora) && righeFile.get(i)[1].equals(giorno)) {
				trovato=true;
				lRisposta.setText(righeFile.get(i)[0]+", numero aula: "+righeFile.get(i)[4]);
				break;
			} else if (righeFile.get(i)[pos].equals(comboDocenti.getValue()) && righeFile.get(i)[2].equals(ora) && righeFile.get(i)[1].equals(giorno)) {
				lRisposta.setText("classe: "+righeFile.get(i)[3]+", numero aula: "+righeFile.get(i)[4]);
				trovato=true;
				break;
			}
			
			if(!trovato) {
				lRisposta.setText("non trovato");
			}
			
		}
		
				
	}
	
	private void classe() {
		if (classe.isSelected()) {
			pos = 3;
			comboClassi.setDisable(false);
			comboDocenti.setDisable(true);
		}else {
			pos = 0;
			comboDocenti.setDisable(false);
			comboClassi.setDisable(true);
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
/*
 * 					if(i!=0 && !righeFile.get(i)[0].equals(righeFile.get(i-1)[0])) {
						comboDocenti.getItems().add(righeFile.get(i-1)[0]);
					}
					i++;
					
 * */
