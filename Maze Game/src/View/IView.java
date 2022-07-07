package View;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public interface IView {
    void saveGame(ActionEvent actionEvent);
    void loadGame(ActionEvent actionEvent);
    void properties(ActionEvent actionEvent);
    void help(MouseEvent mouseEvent);
    void about(MouseEvent mouseEvent);
    void exit();
    void muteUnmute(ActionEvent actionEvent);
}
