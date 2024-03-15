package meuJogo.Modelo;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Player {
	private int x, y;
	private int dx, dy;
	private Image imagem;
	private int altura, largura;
	private List <Tiro> tiros;
	private boolean isVisivel;
	
	public Player() {
		this.x = 100;
		this.y = 100;
		
		isVisivel = true;
		
		tiros = new ArrayList<Tiro>();
	}
	public void load() {
		ImageIcon referencia = new ImageIcon("ress//meuJogoImg//nave.png");
		imagem = referencia.getImage();
		altura = imagem.getHeight(null);
		largura = imagem.getWidth(null);
	}
	public void update() {
		x += dx;
		y += dy;
	}
	public void tirosSimples() {
		this.tiros.add(new Tiro(x+largura, y+(altura/2)));
	}
	public List<Tiro> getTiros() {
		return tiros;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, largura, altura);
	}
	
	public void KeyPressed(KeyEvent tecla) {
		int codigo = tecla.getKeyCode();
		
		if(codigo == KeyEvent.VK_R) {
			tirosSimples();
		}
		
		if(codigo == KeyEvent.VK_W) {
			dy=-3;
		}
		if(codigo == KeyEvent.VK_S) {
			dy=3;
		}
		if(codigo == KeyEvent.VK_A) {
			dx=-3;
		}
		if(codigo == KeyEvent.VK_D) {
			dx=3;
		}
	}
	public void KeyReleased(KeyEvent tecla) {
		int codigo = tecla.getKeyCode();
		
		if(codigo == KeyEvent.VK_W) {
			dy=0;
		}
		if(codigo == KeyEvent.VK_S) {
			dy=0;
		}
		if(codigo == KeyEvent.VK_A) {
			dx=0;
		}
		if(codigo == KeyEvent.VK_D) {
			dx=0;
		}
	}
	
	
	
	public boolean isVisivel() {
		return isVisivel;
	}
	public void setVisivel(boolean isVisivel) {
		this.isVisivel = isVisivel;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Image getImagem() {
		return imagem;
	}
	
	

}
