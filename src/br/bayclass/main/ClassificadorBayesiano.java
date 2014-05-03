package br.bayclass.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import br.bayclass.util.Classe;

public class ClassificadorBayesiano {

	ClasseBayes[] asClasses = null;
	BufferedImage img;
	Classe umaC;
	BufferedImage out;

	public ClassificadorBayesiano(BufferedImage umaImg, int qtdeClasses,
			Classe c) {
		img = umaImg;
		out = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		asClasses = new ClasseBayes[qtdeClasses];
		for (int i = 0; i < qtdeClasses; i++)
			asClasses[i] = new ClasseBayes();

		asClasses[0].setColor(Classe.Cores[c.ordinal()]);
		umaC = c;
	}

	public void populeClasses() {
		Raster rastImg = img.getData();

		for (Classe c : Classe.values()) {
			int classNumber = 1;
			int numeroLinhas = 10;
			if (c == umaC) { // se for a classe positiva, pega 30 numeros
				classNumber = 0;
				numeroLinhas = 30;
			}

			int[] pix = { 0, 0, 0, 0 };
			int arr[][] = Classe.PONTOSS_V3[umaC.ordinal()];
			for (int i = 0; i < numeroLinhas; i++) {
				int x = arr[i][0];
				int y = arr[i][1];
				pix = rastImg.getPixel(x, y, pix);
				float hsb[] = Classe.convertRGBtoHSL( pix, null );
				//float hsb[] = Color.RGBtoHSB(pix[0], pix[1], pix[2], null);
				asClasses[classNumber].addTupla(hsb);
			}
		}
	}

	public void learn() {
		for (ClasseBayes cb : asClasses) {
			cb.execute();
		}
	}

	public void percorraTodosPixels() {
		Raster rastImg = img.getData();
		int w = img.getWidth();
		int h = img.getHeight();
		WritableRaster rastOut = out.getRaster();
		float resultados[] = new float[asClasses.length];
		int pix[] = { 0, 0, 0, 0 };
		float hsb[] = { 0, 0, 0, 0 };
		for (int y = 0; y < h; y++)
			for (int x = 0; x < w; x++) {
				pix = rastImg.getPixel(x, y, pix);
				hsb = Classe.convertRGBtoHSL( pix, hsb );
				//hsb = Color.RGBtoHSB(pix[0], pix[1], pix[2], hsb);
				for (int i = 0; i < asClasses.length; i++) {
					resultados[i] = asClasses[i].getProbabilidade(hsb);
				}

				int umaCor[] = asClasses[0].corPixel;

				if (resultados[0] < resultados[1])
					umaCor = asClasses[1].corPixel;

				rastOut.setPixel(x, y, umaCor );
			}
	}

	public void execute() {
		populeClasses();
		learn();
		percorraTodosPixels();
	}

	public BufferedImage getImage() {
		return out;
	}
	
}
