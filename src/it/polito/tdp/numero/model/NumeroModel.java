package it.polito.tdp.numero.model;

import java.security.InvalidParameterException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class NumeroModel {
	
	private final int NMAX = 100;
	private final int TMAX = 8; 	//perchè dimezzando ogni volta mi basterebbero 7 tentativi, 
									//infatti 2 alla 7 =128

	private int segreto;
	private IntegerProperty tentativiFatti;
	private boolean inGioco;
	
	public NumeroModel() {
		inGioco=false;
		//tentativi fatti è un oggetto quindi devo fare una new e crearlo
		tentativiFatti= new SimpleIntegerProperty();
	}

	/**
	 * Avvia nuova partita
	 */
	public void newGame() { //la definisco void perchè non deve restituirmi niente
		// Gestisce l'inizio di una nuova partita
		this.segreto = (int) (Math.random() * NMAX) + 1;
		this.tentativiFatti.set(0);
		this.inGioco = true;
	}
	/**
	 * Metodo per effettuare un tentativo
	 * @param t è il tentativo
	 * @return  0 se ho indovinato
	 * 			1 se è troppo alto
	 * 		   -1 se è troppo basso
	 */
	public int tentativo(int t) {
		
		//controllo se la partita è in corso
		if(!inGioco) {
			throw new IllegalStateException("La partita è terminata!");
		}
		//controllo se il tentativo è valido
		if(!tentativoValido(t)) {
			throw new InvalidParameterException(String.format("Devi inserire un numero tra %d e %d ", 1, NMAX));
		}
		//gestisco i tentativi 
		this.tentativiFatti.set(this.tentativiFatti.get()+1);
		
		if(this.tentativiFatti.get()==TMAX) {
			//la partita è finita perchè ho esaurito i tentativi
			//ma devo comunque controllare che il tentativo che stavo 
			//facendo è giusto o sbagliato, potrei aver indovinato all'ultimo
			//tentativo quindi vincere anche se ho settato inGioco =false;
			this.inGioco=false;
		}
		if( t==this.segreto) {
			inGioco=false;
			return 0;
		}
		if( t>this.segreto) {
			return 1;
		}
		return -1;	
	}
	
	public int getSegreto() {
		return segreto;
	}

	public boolean isInGioco() {
		return inGioco;
	}

	public int getTMAX() {
		return TMAX;
	}
	
	public final IntegerProperty tentativiFattiProperty() {
		return this.tentativiFatti;
	}
	
	public final int getTentativiFatti() {
		return this.tentativiFattiProperty().get();
	}

	public final void setTentativiFatti(final int tentativiFatti) {
		this.tentativiFattiProperty().set(tentativiFatti);
	}
	
	public boolean tentativoValido(int t ) {
		//controllo se l'input è nel range corretto
		if(t<1 || t>NMAX) {
			return false;
		}
		return true;
	}
	
}
