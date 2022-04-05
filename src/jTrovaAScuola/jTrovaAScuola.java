package jTrovaAScuola;

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

public class jTrovaAScuola extends Application {
	GridPane Area = new GridPane();

	Button pCerca = new Button("CERCA");

	Label lOra = new Label("Inserisci ora:");
	Label lGiorni = new Label("Giorni");

	TextField tOra = new TextField();
	TextField tGiorni = new TextField();
	TextField tClasse = new TextField();
	TextField tDocente = new TextField();

	// Spinner spin=new Spinner();

	RadioButton classe = new RadioButton("Classe");
	RadioButton docente = new RadioButton("Docente");

	ArrayList<String[]> righeFile = new ArrayList<>();

	private void initialize() {
		String rigaLetta;
		try {
			FileInputStream fis = new FileInputStream(
					"C:\\Users\\alessandromaggio\\Desktop\\Workspace\\jTrovaAScuola\\OreClassiDocenti.csv");
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
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
		System.out.println(righeFile.get(0)[0]);

	}

	@Override
	public void start(Stage Finestra) throws Exception {
		ToggleGroup gruppo = new ToggleGroup();
		classe.setToggleGroup(gruppo);
		docente.setToggleGroup(gruppo);
		tClasse.setDisable(true);
		tDocente.setDisable(true);

		Area.add(lOra, 0, 0);
		Area.add(tOra, 1, 0);
		Area.add(lGiorni, 0, 1);
		Area.add(tGiorni, 1, 1);
		Area.add(classe, 0, 2);
		Area.add(docente, 1, 2);
		Area.add(pCerca, 0, 3);
		Area.add(tClasse, 0, 3);
		Area.add(tDocente, 1, 3);
		// Area.add(spin, 0, 3);

		Scene scena = new Scene(Area, 600, 600);
		Finestra.setScene(scena);
		Finestra.setTitle("Bot Telegram");
		Finestra.show();
		pCerca.setOnAction(e -> cerca());
		classe.setOnAction(e -> Classe(e));
		//docente.setOnAction(e -> Docente());
	}

	private void cerca() {
		
	}
	
	private void Classe(Event e) {
		if (classe.isSelected()) {
			tClasse.setDisable(false);
			tDocente.setDisable(true);
		}else {
			tDocente.setDisable(false);
			tClasse.setDisable(true);
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
