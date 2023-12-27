package mainscript.quanlytiemnet;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

public class LabelAnimation {
    private static final Duration FRAME_DURATION = Duration.millis(50);

    public static void startRainbowAnimation(Label label) {
        Timeline timeline = new Timeline(new KeyFrame(FRAME_DURATION, event -> changeColor(label)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private static void changeColor(Label label) {
        double hue = (System.currentTimeMillis() % 7000) / 7000.0 * 360.0;
        LinearGradient linearGradient = createLinearGradient(hue);
        label.setTextFill(linearGradient);
    }

    private static LinearGradient createLinearGradient(double hue) {
        Stop[] stops = {
                new Stop(0, Color.hsb(hue, 1.0, 1.0)),
                new Stop(0.5, Color.hsb((hue + 180) % 360, 1.0, 1.0)),
                new Stop(1, Color.hsb((hue + 360) % 360, 1.0, 1.0))
        };
        return new LinearGradient(0, 0, 1, 0, true, javafx.scene.paint.CycleMethod.NO_CYCLE, stops);
    }
}
