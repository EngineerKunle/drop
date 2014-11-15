package com.mygdx.game;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GameOver implements Screen{

	final Drop game;

	OrthographicCamera camera;
	Texture gameOver;
	Rectangle go;
	
	int touch1 = 100;
	int touch2 = 150;
	

	public GameOver(final Drop gam) {
		game = gam;
		
		gameOver = new Texture(Gdx.files.internal("Game Over.png"));
		
		go = new Rectangle();
		go.x = 270;
		go.y = 140;
		go.width = gameOver.getWidth();
		go.height = gameOver.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		//game.font.draw(game.batch, "Game Over", 100, 150);
		game.batch.draw(gameOver, go.x, go.y);
		
		//Rectangle bounds = new Rectangle(randomX2, randomY2, boxImage.getWidth(), boxImage.getHeight());
				Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(tmp);
				
				
				if (go.contains(tmp.x, tmp.y)){
					if (Gdx.input.isTouched()) { 
					game.setScreen(new MyGdxGame(game));
				      dispose();
				      }
				    }
		game.batch.end();
		
		
/*
 * Timer.schedule(new Task() {
 
            
            @Override
            public void run() {
               changeScreen();
            }

         }, 15);
         */

		/* if (Gdx.input.isTouched()) { 
			game.setScreen(new MyGdxGame(game));
		      dispose();
		      }*/
	
	}



	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}


}
