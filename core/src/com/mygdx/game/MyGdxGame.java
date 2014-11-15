package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class MyGdxGame implements Screen {

	final Drop game;

	Texture dropImage;
	Texture bucketImage;
	Texture background;
	Sound dropSound;
	Music rainMusic;
	OrthographicCamera camera;
	SpriteBatch batch;
	Rectangle bucket; // to help draw the bucket
	Array<Rectangle> raindrops;
	long lastDropTime;// stores the time in nanoseconds
	int dropsGathered;
	double rainspeed = 0.25; // increase speed

	public MyGdxGame(final Drop gam) {
		this.game = gam;
		// load background
		background = new Texture(Gdx.files.internal("bgs.png"));

		// load the images for the droplet and the bucket, 64x64 pixels each
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));

		// load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		// start the playback of the background music immediately
		rainMusic.setLooping(true);
		rainMusic.play();

		// creating the camera

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// creating the spritebatch for the textures

		batch = new SpriteBatch();

		// creates the bucket shapes into the game world...
		bucket = new Rectangle();// center the bucket horizontally
		bucket.x = 800 / 2 - 64 / 2; // bottom left corner of the bucket is 20
										// pixels above the bottom screen edge
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;
		// units above represents the bucket

		// drops our first rain drop method
		raindrops = new Array<Rectangle>();
		spawnRaindrop();

	}

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800 - 64);// offering various maths
													// related static methods
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(1, 1, 1, 1);// other than zero an "f" has to be
										// used cos its a float
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the bucket and
		// all drops

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		game.batch.begin();

		batch.draw(background, 0, 0);
		batch.draw(bucketImage, bucket.x, bucket.y);

		for (Rectangle raindrop : raindrops) {
			batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		game.font.draw(batch, "Drops Collected: " + dropsGathered, 0, 480);

		batch.end();
		game.batch.end();

		// process user input
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
			// can delete this just testing the y-cordinates
			bucket.y = touchPos.y - 25;
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT))
			bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			bucket.x += 200 * Gdx.graphics.getDeltaTime();

		// make sure the bucket stays within the screen bounds
		if (bucket.x < 0)
			bucket.x = 0;
		if (bucket.x > 800 - 64)
			bucket.x = 800 - 64;

		// check if we need to create a new raindrop
		if (TimeUtils.nanoTime() - lastDropTime> 1000000000)
		
			
			spawnRaindrop();
		  

		// move the raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the later case we play back
		// a sound effect as well.
		Iterator<Rectangle> iter = raindrops.iterator();
		while (iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime() + rainspeed;
			if (raindrop.y + 64 < 0)
				iter.remove();

			// rectangle ovelasp() methods checks if rectangle has overlapped
			if (raindrop.overlaps(bucket)) {
				dropsGathered++;
				dropSound.play();
				rainspeed+=5;
				iter.remove();
			}

			// game over screen... setting the gameover screen when the rain
			// drops
			// make sure to add life.
			if (raindrop.y + 64 < 0) {
				game.setScreen(new GameOver(game));
			}

		}

	}

	@Override
	public void dispose() {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
		background.dispose();
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
}