package View;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public interface IView {
    void saveGame(ActionEvent actionEvent);
    void loadGame(ActionEvent actionEvent);
    void setProperties(ActionEvent actionEvent);
    void help_i();
    void aboutGame_i();
    void aboutProducers_i();
    void exitMaze_i();
    void muteUnmute(ActionEvent actionEvent);
}
