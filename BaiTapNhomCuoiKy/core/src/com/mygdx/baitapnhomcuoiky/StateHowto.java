package com.mygdx.baitapnhomcuoiky;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class StateHowto extends State {

    private enum State {Loading, Active};

    // State
    private State _state;

    // Resources
    private TextureRegion _imgBackground;
    private BitmapFont _fontTitle;
    private BitmapFont _fontText;
    private BitmapFont _fontLoading;
    private Sound _selectSFX;

    // Languages manager
    private LanguagesManager _lang;

    // Strings
    String _loadingText;
    String _titleText;
    String _helpText;

    // Positions
    private Vector2 _loadingPos;
    private Vector2 _titlePos;
    private Vector2 _helpPos;

    private boolean _readyToChange;

    GlyphLayout _layout;

    public StateHowto (FreegemasReprogrammed freegemas) {
        super(freegemas);

        // Languages manager
        _lang = LanguagesManager.getInstance();

        // Initial state
        _state = State.Loading;

        // Resources are initially null
        _imgBackground = null;
        _fontTitle = null;
        _fontText = null;

        // Load font resource
        _fontLoading = FreegemasReprogrammed.getPlatformResolver().loadFont("data/loadingFont.fnt", "data/normal.ttf", 70);

        // Load strings
        _loadingText = _lang.getString("Loading...");
        _titleText = _lang.getString("How to play");
        _helpText = _lang.getString("help_text");

        // Set loading position
        _layout = new GlyphLayout();
        _layout.setText(_fontLoading, _loadingText);
        _loadingPos = new Vector2((FreegemasReprogrammed.VIRTUAL_WIDTH - _layout.width) / 2, (FreegemasReprogrammed.VIRTUAL_HEIGHT - _layout.height) / 2);

        _readyToChange = false;
    }


    @Override
    public void load() {
        AssetManager assetManager = _parent.getAssetManager();

        // Load textures
        assetManager.load("data/howtoScreen.png", Texture.class);

        // Load fonts
        _fontTitle = FreegemasReprogrammed.getPlatformResolver().loadFont("data/menuFont.fnt", "data/menu.ttf", 60);
        _fontText = FreegemasReprogrammed.getPlatformResolver().loadFont("data/helpFont.fnt", "data/normal.ttf", 37);
        assetManager.load("data/select.ogg", Sound.class);
    }

    @Override
    public void unload() {
        // Set references to null
        _imgBackground = null;
        _fontTitle = null;
        _fontText = null;
        _selectSFX = null;

        // Unload resources
        AssetManager assetManager = _parent.getAssetManager();
        assetManager.unload("data/howtoScreen.png");
        assetManager.unload("data/select.ogg");
    }

    @Override
    public void assignResources() {
        // Retrieve resources
        AssetManager assetManager = _parent.getAssetManager();
        _imgBackground = new TextureRegion(assetManager.get("data/howtoScreen.png", Texture.class));
        _selectSFX = assetManager.get("data/select.ogg", Sound.class);

        _imgBackground.flip(false, true);

        // Set positions now that we now about sizes
        _layout.setText(_fontTitle, _titleText);
        _titlePos = new Vector2(315 + (FreegemasReprogrammed.VIRTUAL_WIDTH - 400 - _layout.width) / 2, 55);
        _helpPos = new Vector2(375, 175);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void update(double deltaT) {
        if (_state == State.Loading) {
            if (_parent.getAssetManager().update()) {
                assignResources();
                _state = State.Active;
            }

            return;
        }
    }

    @Override
    public void render () {
        SpriteBatch batch = _parent.getSpriteBatch();

        // STATE LOADING - Just render loading
        if (_state == State.Loading) {
            _fontLoading.draw(batch, _loadingText, _loadingPos.x, _loadingPos.y);
            return;
        }

        // STATE ACTIVE
        batch.draw(_imgBackground, 0, 0);

        _fontTitle.setColor(0.0f, 0.0f, 0.0f, 0.5f);
        _fontTitle.draw(batch, _titleText, _titlePos.x + 4, _titlePos.y + 4);
        _fontTitle.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        _fontTitle.draw(batch, _titleText, _titlePos.x, _titlePos.y);

        _fontText.draw(batch, _helpText, _helpPos.x, _helpPos.y, FreegemasReprogrammed.VIRTUAL_WIDTH - 450, Align.left, true);
    }

    @Override
    public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
        _readyToChange = true;

        _selectSFX.play();

        return false;
    }

    @Override
    public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
        // Left click
        if (arg3 == 0 && _readyToChange) {
            _parent.changeState("StateMenu");
        }

        return false;
    }

    @Override
    public boolean keyDown(int arg0) {
        if(arg0 == Input.Keys.BACK){
            _parent.changeState("StateMenu");
        }

        return false;
    }

    @Override
    public void resume() {
        _readyToChange = false;
        _state = State.Loading;
    }
}
