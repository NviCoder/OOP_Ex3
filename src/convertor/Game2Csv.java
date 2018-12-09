package convertor;

import gameObjects.Game;

public class Game2Csv {

	private Game game;
	private String path;
	private String fileName;
	
	public Game2Csv(Game game, String path, String fileName) {
		this.game = game;
		this.path = path;
		this.fileName = fileName;
	}
	
	public void export() {
		StringBuilder csv = new StringBuilder();
		//TODO
	}
	
}
