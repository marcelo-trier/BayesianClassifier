package br.bayclass.ui;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.border.EmptyBorder;

import br.bayclass.main.ClassificadorBayesiano;
import br.bayclass.util.Classe;

public class JanelaPrincipal extends JFrame {

	private JDesktopPane contentPane;
	private ButtonGroup group;

	public void clickBayesUmPraTodos(Classe umaC) {
		ClassificadorBayesiano cb = new ClassificadorBayesiano();
		//cb.setInfo( numClasses, numAtrib );
		cb.setClassNumber( 2 );
		//cb.setNumeroAprendizagem( Classe.NUMERO_PONTOS );
		for( Classe c : Classe.values() ) {
			int classNumber = 1;
			int numeroLinhas = 10;
			if( c == umaC ) {
				classNumber = 0;
				numeroLinhas = 30;
			}
			int[] pix = { 0, 0, 0, 0 };
			for( int i=0; i<numeroLinhas; i++ ) {
				int x = Classe.PONTOSS_V3[ umaC.ordinal() ][ i ][ 0 ];
				int y = Classe.PONTOSS_V3[ umaC.ordinal() ][ i ][ 1 ];
				pix = rastImg.getPixel( x, y, pix );
				hsb[] = Color.RGBtoHSB( pix[0], pix[1], pix[2], null );
				cb.addTupla( classNumber, hsb );
			}
		}
		cb.learn(); // aqui gera media e sigma

		int[] hsb = { 0, 0, 0, 0 };
		for( int x=0; x<w; x++ ) {
			for( int y=0; y<h; y++ ) {
				getRgb( x, y, pix );
				hsb = Color.RGBtoHSB( pix[0], pix[1], pix[2], hsb );
				int classeBayes = cb.preveja( hsb ); // aqui retorna 0 ou 1... ou o numero da classe!!
				if( classeBayes == 0 ) {
					pinta preto
				}
				else
					pinta( Classe.Cores[ umaC.ordinal() ] );
			}
		}
	}

	public void clickTeste() {
	}

	public void clickDefinePontos(Classe umaC) {
		TelaInterna ti = (TelaInterna) contentPane.getSelectedFrame();
		ti.registraPonto(umaC);
		ti.atualizaPontos();
	}

	public void clickMostrePontos() {
		TelaInterna ti = (TelaInterna) contentPane.getSelectedFrame();
		for (Classe c : Classe.values()) {
			ti.mostrePontos(Classe.PONTOSS_V3[c.ordinal()], c);
		}

	}

	public void clickLimpaPontos() {
		TelaInterna ti = (TelaInterna) contentPane.getSelectedFrame();
		ti.pintaImagem();
	}

	public void mostraImagem(String titulo, BufferedImage imgOut) {
		TelaInterna interno = new TelaInterna(titulo, imgOut);
		contentPane.add(interno);
		interno.setVisible(true);

	}

	public void mostraImagem(BufferedImage imgOut) {
		mostraImagem("", imgOut);
	}

	public BufferedImage getImage() {
		// pega a janela ativa...
		TelaInterna ti = (TelaInterna) contentPane.getSelectedFrame();
		BufferedImage img;
		img = ti.getImage();
		return img;
	}

	public void clickSave() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		File umDir = new File(System.getProperty("user.dir"));
		fileChooser.setCurrentDirectory(umDir);
		if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File salvar = fileChooser.getSelectedFile();
		// ImageIO.w
		BufferedImage img = getImage();
		ImageIO.write(img, "bmp", salvar);
	}

	public void clickOnLoad() throws Exception {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		File umDir = new File(System.getProperty("user.dir"));
		fileChooser.setCurrentDirectory(umDir);
		if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File file = fileChooser.getSelectedFile();
		BufferedImage imagem = ImageIO.read(file);
		TelaInterna interno = new TelaInterna(imagem);
		contentPane.add(interno);
		interno.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public JanelaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 701, 399);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOpen = new JMenuItem("Load...");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clickOnLoad();
				} catch (Exception ex) {

				}float tup.
			}
		});
		mnFile.add(mntmOpen);

		JMenuItem mntmSave = new JMenuItem("Save...");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clickSave();
				} catch (Exception ex) {

				}
			}
		});
		mnFile.add(mntmSave);

		JMenu mnImagens = new JMenu("Pontos");
		menuBar.add(mnImagens);

		JMenuItem mntmTeste = new JMenuItem("Teste");
		mntmTeste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickTeste();
			}
		});
		mnImagens.add(mntmTeste);

		JMenu mnDefineClassePontos = new JMenu("Define Pontos(Classe)");
		mnImagens.add(mnDefineClassePontos);

		JMenuItem mntmLimpapontos = new JMenuItem("LimpaPontos");
		mnImagens.add(mntmLimpapontos);

		JMenuItem mntmMostrePontos = new JMenuItem("Mostre Pontos");
		mnImagens.add(mntmMostrePontos);
		mntmMostrePontos.addActionListener(new ActionListener() {
			public void actionPerformed(Actionfloat tup.Event e) {
				clickMostrePontos();
			}
		});
		mntmLimpapontos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickLimpaPontos();
			}
		});

		JMenu mnProcessamento = new JMenu("Processamento");
		menuBar.add(mnProcessamento);

		JMenu menuBayesUmPraTodos = new JMenu("Bayesiano um para todos...");
		mnProcessamento.add(menuBayesUmPraTodos);

		JMenuItem mntmMix = new JMenuItem("Mix");
		mntmMix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnProcessamento.add(mntmMix);

		ActionListener alMenu = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Classe umaClasse : Classe.values()) {
					if (e.getActionCommand().equals(umaClasse.toString()))
						clickBayesUmPraTodos(umaClasse);
				}
			}
		};

		ActionListener acaoMenuPontos = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Classe umaC : Classe.values()) {
					if (e.getActionCommand().equals(umaC.toString()))
						clickDefinePontos(umaC);
				}
			}
		};

		Classe asClasses[] = Classe.values();
		JMenuItem umMenu;
		JMenuItem menuPontos;
		for (Classe umaClasse : asClasses) {
			umMenu = new JMenuItem(umaClasse.name());
			umMenu.addActionListener(alMenu);
			menuBayesUmPraTodos.add(umMenu);

			menuPontos = new JMenuItem(umaClasse.name());
			menuPontos.addActionListener(acaoMenuPontos);
			mnDefineClassePontos.add(menuPontos);

		}

		contentPane = new JDesktopPane();
		contentPane.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}
}
