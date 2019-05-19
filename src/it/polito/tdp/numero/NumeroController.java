package it.polito.tdp.numero;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.numero.model.NumeroModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class NumeroController {
	
	//devo collegare il modello al controller è solo un riferimento non sto creando qua il modello
	private NumeroModel model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private HBox boxControllopartita;

	@FXML
	private TextField txtRimasti;
	// numero di tentativi rimasti ancora da provare

	@FXML
	private HBox boxControlloTentativi;

	@FXML
	private TextField txtTentativo;
	// tentativo inserito dall'utente

	@FXML
	private TextArea txtMessaggi;

	@FXML
	void handleNuovaPartita(ActionEvent event) {
		//Gestisce l'inizio di una nuova partita
		// Gestione dell'interfaccia
		boxControllopartita.setDisable(true);
		boxControlloTentativi.setDisable(false);
		txtMessaggi.clear();
		txtTentativo.clear();
		
		//con il binding non mi serve più settare il testo di tentativi
		//txtRimasti.setText(Integer.toString(model.getTMAX()));

		// Comunico al modello di iniziare una nuova partita
		model.newGame();
	}

	@FXML
	void handleProvaTentativo(ActionEvent event) {

		// Leggi il valore del tentativo
		String ts = txtTentativo.getText();

		// Controlla se è valido il tipo di dato

		int tentativo ;
		try {
			tentativo = Integer.parseInt(ts);
		} catch (NumberFormatException e) {
			// la stringa inserita non è un numero valido
			txtMessaggi.appendText("Non è un numero valido\n");
			return ;
		}
		// se ho inserito un numero continua il codice
		if(!model.tentativoValido(tentativo)) {
			txtMessaggi.appendText("Range non valido!\n");
			return;
		}
		int risultato= model.tentativo(tentativo);
		
		// Controlla se il risultato è troppo alto o basso e stampa messaggio
		if(risultato==0){
			txtMessaggi.appendText("Complimenti, hai indovinato in "+ model.getTentativiFatti() +" tentativi\n");
			boxControllopartita.setDisable(false);
			boxControlloTentativi.setDisable(true);
			//non mi serve settare inGioco = false perchè quando chiamo il metodo tentativo controlla già se la partita è finita e in caso cambia lui il valore di inGioco
		} else if(risultato<0){
				txtMessaggi.appendText("Tentativo troppo BASSO\n");
			} else 
				txtMessaggi.appendText("Tentativo troppo ALTO\n");

		// Aggiornare interfaccia con n. tentativi rimasti
		//con il binding non mi serve più settare il testo di tentativi
		//txtRimasti.setText(Integer.toString(model.getTMAX()-model.getTentativiFatti()));

		if(!model.isInGioco()) {
			//la partita è finita!
			//posso avere due casi, se ho indovinato o se non ho indovinato, il caso in cui ho indovinato l'ho già stampato prima quindi non lo considero
			if(risultato!=0) {
				txtMessaggi.appendText("Hai perso!");
				txtMessaggi.appendText("Il numero segreto era: " + model.getSegreto() +"\n");
				boxControllopartita.setDisable(false);
				boxControlloTentativi.setDisable(true);
			}
		}
	}

	@FXML
	void initialize() {
		assert boxControllopartita != null : "fx:id=\"boxControllopartita\" was not injected: check your FXML file 'Numero.fxml'.";
		assert txtRimasti != null : "fx:id=\"txtRimasti\" was not injected: check your FXML file 'Numero.fxml'.";
		assert boxControlloTentativi != null : "fx:id=\"boxControlloTentativi\" was not injected: check your FXML file 'Numero.fxml'.";
		assert txtTentativo != null : "fx:id=\"txtTentativo\" was not injected: check your FXML file 'Numero.fxml'.";
		assert txtMessaggi != null : "fx:id=\"txtMessaggi\" was not injected: check your FXML file 'Numero.fxml'.";

	}
	
	public void setModel(NumeroModel model) {
		this.model = model;
		//aggiunto il collegamento modello-vista con le properties per eliminare le set test di tentativi 
		txtRimasti.textProperty().bind(Bindings.convert(model.tentativiFattiProperty()));
	}

}
