package br.bayclass.main;

import java.awt.EventQueue;

import br.bayclass.ui.JanelaPrincipal;

public class Aplicacao {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaPrincipal frame = new JanelaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
