package view;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by wanderson on 17/05/17.
 */
public class CorPeao {
    public CorPeao() {
    }

    public Color[] getCores() {
        Color[] cores = new Color[6];

        cores[0] = Color.rgb(255, 0, 0);
        cores[1] = Color.rgb(0, 0, 255);
        cores[2] = Color.rgb(0, 255, 0);
        cores[3] = Color.rgb(100, 20, 0);
        cores[4] = Color.rgb(200, 0, 255);
        cores[5] = Color.rgb(0, 250, 255);
        return cores;
    }
}
