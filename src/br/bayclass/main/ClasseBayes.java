package br.bayclass.main;

import java.awt.Color;
import java.util.ArrayList;

import br.bayclass.util.Classe;

public class ClasseBayes {
	ArrayList<float[]> tuplas = new ArrayList<float[]>();
	float[] soma = { 0, 0, 0 };
	float[] media = { 0, 0, 0 };
	float[] variancia = { 0, 0, 0 };
	public int corPixel[] = { 0, 0, 0, 255 };
	
	public void addTupla( float[] atributos ) {
		for( int i=0; i<3; i++ )
			soma[ i ] = soma[ i ] + atributos[ i ];

		tuplas.add( atributos );
	}
	
	public void setColor( Color umaCor ) {
		corPixel[0] = umaCor.getRed();
		corPixel[1] = umaCor.getGreen();
		corPixel[2] = umaCor.getBlue();
	}
	
	public float getProbabilidade( float[] osAttr ) {
		float produtorio = 1;
		
		boolean z2EhPositivo = true;
		for( int i=0; i<3; i++ ) {
			float z1 = (osAttr[i]-media[i])/variancia[i];
			int z2 = ( int )( z1 * 100 );
			if( z2 < 0 ) {
				z2EhPositivo = false;
				z2 *= -1;
			}

			if( z2 >= Classe.tabela_z.length )
				z2 = Classe.tabela_z.length-1;

			float valor = Classe.tabela_z[ z2 ];
			if( z2EhPositivo )
				valor = valor - 0.5f;
			else
				valor = 1 - valor;

			produtorio *= valor;
		}
		return produtorio;
	}
	
	public void execute() {
		float quantidade = ( float )tuplas.size();
		for( int i=0; i<3; i++ ) {
			media[ i ] = soma[ i ] / quantidade;
		}

		//float [] tmp = { 0, 0, 0 };
		for( float[] attr : tuplas ) {
			for( int i=0; i<3; i++ ) {
				float tmp = attr[i]-media[i];
				soma[i] += ( float )Math.pow( tmp, 2 );
			}
		}
		
		float tmp;
		for( int i=0; i<3; i++ ) {
			tmp = soma[ i ] / quantidade;
			variancia[i] = ( float )Math.sqrt( tmp );
		}
		
	}
	
}
