package GUI;

import BLL.InFoTaiKhoan.DanhSachTK;
import BLL.InFoTaiKhoan.TaiKhoan;
import DAL.TaiKhoanDAO;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import mainscript.quanlytiemnet.MainController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button bp_backBtn;

    @FXML
    private Button fb_backBtn;

    @FXML
    private Button fp_proceedBtn;

    @FXML
    private AnchorPane fp_questionForm;

    @FXML
    private TextField fp_sdt;

    @FXML
    private TextField fp_username;

    @FXML
    private Button np_changePasswordBtn;

    @FXML
    private PasswordField np_confirmPassword;

    @FXML
    private AnchorPane np_newPassForm;

    @FXML
    private PasswordField np_newPassword;

    @FXML
    private Hyperlink si_forgotPass;

    @FXML
    private Button si_loginBtn;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_AlreadyHave;

    @FXML
    private Button side_CreateBtn;

    @FXML
    private AnchorPane side_form;

    @FXML
    private PasswordField su_password;

    @FXML
    private TextField su_sdt;

    @FXML
    private Button su_signupBtn;

    @FXML
    private AnchorPane su_signupForm;

    @FXML
    private TextField su_username;


    public void backToLoginForm() {
        si_loginForm.setVisible(true);
        fp_questionForm.setVisible(false);

    }

    public void backToQuestionForm() {
        fp_questionForm.setVisible(true);
        np_newPassForm.setVisible(false);


    }

    public void switchForgotPass() {
        fp_questionForm.setVisible(true);
        si_loginForm.setVisible(false);

    }

    public void switchForm(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == side_CreateBtn) {
            slider.setNode(side_form);
            slider.setToX(400);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_AlreadyHave.setVisible(true);
                side_CreateBtn.setVisible(false);

                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);
            });
            slider.play();
        } else if (event.getSource() == side_AlreadyHave) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_AlreadyHave.setVisible(false);
                side_CreateBtn.setVisible(true);


                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);
            });
            slider.play();
        }
    }

    public void handleLoginButtonAction(ActionEvent event) {
        String username = si_username.getText();
        String password = si_password.getText();
        TaiKhoanDAO dao = new TaiKhoanDAO();
        try {
            TaiKhoan tk = dao.KtraTenvaPassTK(username, password);
            if (tk != null) {
                // Nếu có kết quả từ cơ sở dữ liệu, thông báo đăng nhập thành công
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Đăng nhập thành công!");
                alert.showAndWait();

                try {
                    new DanhSachTK();
                    DanhSachTK.setTaiKhoanDangNhap(tk);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainscript/quanlytiemnet/MainGiaoDien.fxml"));
                    Parent root = loader.load();

                    // Lấy controller của mainForm.fxml
                    MainController controller = loader.getController();

                    // Hiển thị mainForm.fxml trong cửa sổ hiện tại
                    Stage stage = (Stage) si_username.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else {
                // Nếu không có kết quả, thông báo đăng nhập thất bại
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Đăng nhập không thành công. Vui lòng kiểm tra lại thông tin đăng nhập.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void handleSignupButtonAction(ActionEvent event) {
        try {
            // Xử lý khi nút Đăng kí được nhấn
            // Lấy dữ liệu từ các trường nhập liệu (TextField, PasswordField, v.v.)
            TaiKhoan tk = new TaiKhoan();
            tk.setUsername(su_username.getText());
            tk.setSdt(su_sdt.getText());
            tk.setPassword(su_password.getText());

            // Thực hiện việc chèn dữ liệu vào cơ sở dữ liệu
            TaiKhoanDAO dao = new TaiKhoanDAO();
            boolean isSuccess = dao.DangKyTK(tk);

            if (isSuccess) {
                // Hiển thị hộp thoại thông báo khi đăng ký thành công
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Đăng ký thành công!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleProceedButtonAction(ActionEvent event) {
        try {
            String username = fp_username.getText();
            String sdt = fp_sdt.getText();
            TaiKhoanDAO dao = new TaiKhoanDAO();
            // Thực hiện truy vấn để kiểm tra thông tin đăng nhập
            if (dao.KtraTenvaPassTK(username, sdt) != null) {
                // Nếu thông tin đăng nhập đúng, chuyển sang AnchorPane np_newPassForm
                fp_questionForm.setVisible(false);
                np_newPassForm.setVisible(true);
            } else {
                // Nếu thông tin đăng nhập không đúng, có thể hiển thị một thông báo lỗi
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Thông tin đăng nhập không đúng. Vui lòng kiểm tra lại.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Pair<String, String> getUsernameAndSdt(String username, String sdt) {
        try {
            TaiKhoanDAO dao = new TaiKhoanDAO();
            TaiKhoan res = dao.KtraTenvaPassTK(username, sdt);
            if (res != null) {
                return new Pair<>(res.getUsername(), res.getSdt());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Hiển thị thông báo việc cập nhật mật khẩu thành công hay không
    private void showSuccessDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setHeaderText(null);
        alert.setContentText("Mật khẩu đã được cập nhật thành công!");
        alert.showAndWait();
    }

    private void showFailureDialog(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    public void handleChangePasswordButtonAction(ActionEvent event) {
        String newPassword = np_newPassword.getText();
        String confirmPassword = np_confirmPassword.getText();

        try {
            // Kiểm tra mật khẩu mới và xác nhận mật khẩu
            if (!newPassword.equals(confirmPassword)) {
                showFailureDialog("Cập nhật mật khẩu không thành công");
                // Hiển thị thông báo lỗi nếu mật khẩu không khớp
                // ...
            } else {
                String username = fp_username.getText();
                String sdt = fp_sdt.getText();

                Pair<String, String> usernameAndSdt = getUsernameAndSdt(username, sdt);
                if (usernameAndSdt != null) {
                    String retrievedUsername = usernameAndSdt.getKey();
                    String retrievedSdt = usernameAndSdt.getValue();

                    TaiKhoanDAO dao = new TaiKhoanDAO();
                    TaiKhoan tk = new TaiKhoan();
                    tk.setUsername(retrievedUsername);
                    tk.setSdt(retrievedSdt);
                    tk.setPassword(newPassword);
//                boolean updateSuccess = updatePasswordInDatabase(retrievedUsername, retrievedSdt, newPassword);
                    if (dao.LaylaiMatKhau(tk)) {
                        // Chuyển sang giao diện đăng nhập
                        showSuccessDialog();
                        switchToLoginForm();

                    }
                } else {

                    System.out.println("Lỗi");
                    // Hiển thị thông báo lỗi nếu thông tin đăng nhập không hợp lệ
                    // ...
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void switchToLoginForm() {
        np_newPassForm.setVisible(false);
        si_loginForm.setVisible(true);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
