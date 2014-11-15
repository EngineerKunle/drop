package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Drop extends Game {
	
	/*Screens are fundamental to any game with multiple components.
	 * Game abstract class provides an implementation of applicationListner 
	 * along with some helper methods to set and handle screen rendering
	 * 
	 */
	
	   public SpriteBatch batch;
	   public BitmapFont font;
	
	public void create() {
		   batch = new SpriteBatch();
	        //Use LibGDX's default Arial font.
	        font = new BitmapFont();
	        this.setScreen(new MainMenuScreen(this));
	}
	
	public void render() {
        super.render(); //important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}
