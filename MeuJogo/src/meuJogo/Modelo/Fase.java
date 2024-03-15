package meuJogo.Modelo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Fase extends JPanel implements ActionListener {
	private Image fundo;
	private Player player;
	private Timer timer;
	private List<Enemy1> enemy1;
	private List<Stars> stars;
	private boolean emJogo;

	public Fase() {
		setFocusable(true);
		setDoubleBuffered(true);

		ImageIcon referencia = new ImageIcon("ress//meuJogoImg//bgc.png");
		fundo = referencia.getImage();

		player = new Player();
		player.load();

		addKeyListener(new TecladoAdapter());
		timer = new Timer(5, this);
		timer.start();
		
		inicializaInimigos();
		inicializaStars();
		emJogo = true;
	}
	
	public void inicializaInimigos() {
		int cordenadas [] = new int [40];
		enemy1 = new ArrayList<Enemy1>();
		
		for (int i = 0; i < cordenadas.length; i++) {
			int x = (int)(Math.random()*8000+1024);
			int y = (int)(Math.random()*650+30);
			enemy1.add(new Enemy1(x, y));
		}
	}
	
	public void inicializaStars() {
		int cordenadas [] = new int [100];
		stars = new ArrayList<Stars>();
		
		for (int i = 0; i < cordenadas.length; i++) {
			int x = (int)(Math.random()*1024+0);
			int y = (int)(Math.random()*768+0);
			stars.add(new Stars(x, y));
		}
	}
	

	public void paint(Graphics g) {
		Graphics2D graficos = (Graphics2D) g;
		if(emJogo == true) {
			graficos.drawImage(fundo, 0, 0, null);
			
			
			for (int q = 0; q < stars.size(); q++) {
				Stars y = stars.get(q);
				y.load();
				graficos.drawImage(y.getImagem(), y.getX(), y.getY(), this);
			}			
			
			graficos.drawImage(player.getImagem(), player.getX(), player.getY(), this);

			List<Tiro> tiros = player.getTiros();
			for (int i = 0; i < tiros.size(); i++) {
				Tiro m = tiros.get(i);
				m.load();
				graficos.drawImage(m.getImagem(), m.getX(), m.getY(), this);
			}
			
			for(int o = 0; o < enemy1.size(); o++) {
				Enemy1 in = enemy1.get(o);
				in.load();
				graficos.drawImage(in.getImagem(), in.getX(), in.getY(), this);
			}
		}else {
			ImageIcon fimJogo = new ImageIcon("ress//meuJogoImg//gameOver.png");
			graficos.drawImage(fimJogo.getImage(), 0, 0, null);
		}
		

		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		player.update();

		if(player.isTurbo()) {
			timer.setDelay(2);
		}
		if(player.isTurbo()== false) {
			timer.setDelay(5);
		}
		
		
		for (int q = 0; q < stars.size(); q++) {
			Stars on = stars.get(q);
			if(on.isVisivel()) {
				on.update();
			}else {
				stars.remove(q);
			}
		}

		List<Tiro> tiros = player.getTiros();
		for (int p = 0; p < tiros.size(); p++) {
			Tiro m = tiros.get(p);
			if (m.isVisivel()){
				m.update();
				if(player.isTurbo()) {
					tiros.get(p).setVELOCIDADE(-1);
				}
				if(player.isTurbo()== false) {
					tiros.get(p).setVELOCIDADE(2);
				}
			}
			else {
				tiros.remove(p);
			}
		}
		
		
		for(int o = 0; o < enemy1.size(); o++) {
			Enemy1 in = enemy1.get(o);
			if(in.isVisivel()) {
				in.update();
			}else {
				enemy1.remove(o);
			}
		}
		
		checarColisoes();

		repaint();
	}
	
	public void checarColisoes() {
		Rectangle formaNave = player.getBounds();
		Rectangle formaEnemy1;
		Rectangle formaTiro;
		
		for(int i = 0; i < enemy1.size(); i++) {
			Enemy1 tempEnemy1 = enemy1.get(i);
			formaEnemy1 = tempEnemy1.getBounds();
			if(formaNave.intersects(formaEnemy1)) {
				if(player.isTurbo()) {
					tempEnemy1.setVisivel(false);		
				}else {
					player.setVisivel(false);
					tempEnemy1.setVisivel(false);	
					emJogo = false;
				}

			}
		}
		
		List<Tiro> tiros = player.getTiros();
		for(int j = 0; j< tiros.size(); j++) {
			Tiro tempoTiro = tiros.get(j);
			formaTiro = tempoTiro.getBounds();
			for(int o = 0; o< enemy1.size(); o++) {
				Enemy1 tempEnemy1 = enemy1.get(o);
				formaEnemy1 = tempEnemy1.getBounds();
				if(formaTiro.intersects(formaEnemy1)) {
					tempEnemy1.setVisivel(false);
					tempoTiro.setVisivel(false);
				}
			}
		}
		
	}

	private class TecladoAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			player.KeyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			player.KeyReleased(e);
		}
	}

}
