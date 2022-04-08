package progettoPON;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	TextField tClasse = new TextField();
	TextField tDocente = new TextField();

	// Spinner spin=new Spinner();

	RadioButton classe = new RadioButton("Classe");
	RadioButton docente = new RadioButton("Docente");

	ArrayList<String[]> righeFile = new ArrayList<>();
	
	int pos = 3;
		
	@Override
	public void start(Stage Finestra) throws Exception {
		ToggleGroup gruppo = new ToggleGroup();
		classe.setToggleGroup(gruppo);
		docente.setToggleGroup(gruppo);
		tDocente.setDisable(true);
		
		classe.setSelected(true);
		
		area.add(lOra, 0, 0);
		area.add(tOra, 1, 0);
		area.add(lGiorni, 0, 1);
		area.add(tGiorni, 1, 1);
		area.add(classe, 0, 2);
		area.add(docente, 1, 2);
		area.add(pCerca, 0, 4);
		area.add(tClasse, 0, 3);
		area.add(tDocente, 1, 3);
		area.add(lRisposta, 1, 4);
		// area.add(spin, 0, 3);

		Scene scena = new Scene(area, 600, 600);
		Finestra.setScene(scena);
		Finestra.setTitle("Bot Telegram");
		Finestra.show();
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
					righeFile.add(rigaLetta.split(";"));
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
		
		String classe = tClasse.getText();
		String docente = tDocente.getText();
		
		for(int i = 0; i<righeFile.size(); i++) {
			// docente;giorno;ora;classe;sezione
			// 0,1,2,3,4
			if (righeFile.get(i)[pos].equals(classe) && righeFile.get(i)[2].equals(ora) && righeFile.get(i)[1].equals(giorno)) {
				trovato=true;
				lRisposta.setText(righeFile.get(i)[0]+", numero aula: "+righeFile.get(i)[4]);
				break;
			} else if (righeFile.get(i)[pos].equals(docente) && righeFile.get(i)[2].equals(ora) && righeFile.get(i)[1].equals(giorno)) {
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
			tDocente.setText("");
			tClasse.setDisable(false);
			tDocente.setDisable(true);
		}else {
			pos = 0;
			tClasse.setText("");
			tDocente.setDisable(false);
			tClasse.setDisable(true);
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
