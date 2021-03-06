package com.mygdx.baitapnhomcuoiky;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlWriter;

import java.io.StringWriter;

public class ScoreTable implements Input.TextInputListener {

    //Scoreboard with name and score
    public enum State {RequestPlayerName, ShowScores};

    private static final String SCORES_FILE = "data/scores.xml";
    private static final int MAX_ENTRIES = 5;

    // State, game and points
    private State _state;
    private FreegemasReprogrammed _game;
    private LanguagesManager _lang;
    private int _points;

    // List with names and points (stored as strings)
    private Array<Pair<String, String> > _scores;

    // Resources
    private BitmapFont _fontTitle;
    private BitmapFont _fontText;

    // Used strings
    String _titleText;

    // Positions
    private Vector2 _titlePos;
    private Vector2 _firstScorePos;
    private int _scoreYGap;

    GlyphLayout _layout;

    public ScoreTable(FreegemasReprogrammed game, int points) {
        // Initial state and game
        _game = game;
        _state = State.RequestPlayerName;
        _points = points;
        _lang = LanguagesManager.getInstance();

        // Create scores
        _scores = new Array<Pair<String, String>>();

        // Parse scores
        parseScore();

        _layout = new GlyphLayout();

        // Load resources
        _fontTitle = FreegemasReprogrammed.getPlatformResolver().loadFont("data/menuFont.fnt", "data/menu.ttf", 60);
        _fontText = FreegemasReprogrammed.getPlatformResolver().loadFont("data/normalFont.fnt", "data/normal.ttf", 60);

        // Load strings
        _titleText = _lang.getString("Best scores");

        // Positions
        _layout.setText(_fontTitle, _titleText);
        _titlePos = new Vector2(548 + (FreegemasReprogrammed.VIRTUAL_WIDTH - 548 - 80 - _layout.width) / 2, 144);
        _firstScorePos = new Vector2(695, 260);
        _scoreYGap = 60;

        // Launch text input if score is better than any of the already recorded or if there are less than 5
        int numScores = _scores.size;
        boolean newScore = numScores < 5;

        for (int i = 0; i < numScores && !newScore; ++i) {
            newScore = _points > Integer.parseInt(_scores.get(i).getSecond());
        }

        if (newScore) {
            Gdx.input.getTextInput(this, _lang.getString("You're top 5! Enter your name"), "", "");
        }
        else
        {
            _state = State.ShowScores;
        }
    }

    public void draw() {
        SpriteBatch batch = _game.getSpriteBatch();

        if (_state == State.ShowScores) {
            // Render title
            _fontTitle.setColor(0.0f, 0.0f, 0.0f, 0.5f);
            _fontTitle.draw(batch, _titleText, (int)_titlePos.x + 4, (int)_titlePos.y + 4);
            _fontTitle.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            _fontTitle.draw(batch, _titleText, (int)_titlePos.x, (int)_titlePos.y);

            // Render table
            int numScores = _scores.size;

            for (int i = 0; i < numScores; ++i) {
                _fontText.draw(batch, _scores.get(i).getFirst(), (int)_firstScorePos.x, (int)_firstScorePos.y + i * _scoreYGap);
                _fontText.draw(batch, _scores.get(i).getSecond(), (int)_firstScorePos.x + 300, (int)_firstScorePos.y + i * _scoreYGap);
            }
        }
    }

    public void parseScore() {
        XmlReader reader = new XmlReader();
        XmlReader.Element root;
        try {
            if (Gdx.files.external(SCORES_FILE).exists())
            {
                root = reader.parse(Gdx.files.external(SCORES_FILE));
                Array<XmlReader.Element> scores =  root.getChildrenByNameRecursively("score");

                for (int i = 0; i < scores.size; ++i) {
                    XmlReader.Element score = scores.get(i);
                    _scores.add(new Pair<String, String>(score.getAttribute("name"), score.getAttribute("points")));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveScore() {
        try {
            StringWriter writer = new StringWriter();
            XmlWriter xml = new XmlWriter(writer);

            xml = xml.element("scores");

            int numScores = _scores.size;
            for (int i = 0; i < numScores; ++i) {
                xml = xml.element("score").attribute("name", _scores.get(i).getFirst()).attribute("points", _scores.get(i).getSecond()).pop();
            }

            xml = xml.pop();

            FileHandle file = Gdx.files.external(SCORES_FILE);
            file.writeString(writer.toString(), false);


            System.out.println(writer.toString());
        }
        catch (Exception e) {

        }
    }

    @Override
    public void canceled() {
        // Show scores
        _state = State.ShowScores;
    }

    @Override
    public void input(String text) {
        if (!text.equals("")) {
            // Add new entry in order
            int numScores = _scores.size;
            boolean added = false;
            for (int i = 0; i < numScores; ++i) {
                if (Integer.parseInt(_scores.get(i).getSecond()) < _points) {
                    if (text.length() > 15) {
                        text = text.substring(0, 15);
                    }
                    _scores.insert(i, new Pair<String, String>(text, "" + _points));
                    added = true;
                    break;
                }
            }

            if (!added) {
                if (text.length() > 15) {
                    text = text.substring(0, 15);
                }
                _scores.add(new Pair<String, String>(text, "" + _points));
            }

            // Delete last entry if it exceeds MAX_ENTRIES
            numScores = _scores.size;

            if (numScores > MAX_ENTRIES) {
                _scores.removeIndex(numScores - 1);
            }

            // Save scores
            saveScore();

            // Show scores
            _state = State.ShowScores;
        }
    }
}
