package com.rkarp.appcore.view.layout;

import com.rkarp.appcore.AppContext;
import com.rkarp.appcore.account.Account;
import com.rkarp.appcore.account.ILoginHandler;
import com.rkarp.appcore.view.MainScene;
import com.rkarp.appcore.view.MessageDialog;
import com.rkarp.appcore.view.login.AccountScene;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Main Login Mask layout class.
 */
public class LoginMaskLayout {
    private VBox main;
    private GridPane grid;
    private Label loginLabel;
    private Button loginButton, registerButton;
    private ProgressBar loadBar;
    private Task<Boolean> loginTask;
    private TextField username;
    private PasswordField password;
    private CheckBox rememberPass, autoLogin;

    public LoginMaskLayout() {
        main = new VBox();
        main.setSpacing(20);
        main.setAlignment(Pos.CENTER);
        grid = new GridPane();
        //grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        loginLabel = new Label("Login");
        main.getChildren().add(loginLabel);

        Label usernameLabel = new Label("User");
        grid.add(usernameLabel, 0, 1);

        username = new TextField();
        grid.add(username, 1, 1);

        Label passwordLabel = new Label("Password");
        grid.add(passwordLabel, 0, 2);

        password = new PasswordField();
        grid.add(password, 1, 2);

        rememberPass = new CheckBox("Remember password?");
        grid.add(rememberPass, 0, 3);

        autoLogin = new CheckBox("Auto login?");
        grid.add(autoLogin, 0, 4);

        loginButton = new Button("Login");
        loginButton.setOnAction(arg0 -> {
            if (!getUsername().isEmpty() && !getPassword().isEmpty()) {
                new Thread(loginTask).start();
            } else {
                new MessageDialog(0, "Enter all account details!", 400, 200);
            }
        });

        registerButton = new Button("Register");
        registerButton.setOnAction(arg0 -> {
            MainScene.mainScene.getSceneViewStack().push(new RegistrationLayout().getMaskNode());
        });

        main.getChildren().add(grid);
        main.getChildren().add(loginButton);
        main.getChildren().add(registerButton);

        if (AppContext.getContext().getAccountManager().getAccount() != null) {
            username.setText(AppContext.getContext().getAccountManager().getAccount().getUser());
            password.setText(AppContext.getContext().getAccountManager().getAccount().getPassword());
            rememberPass.setSelected(AppContext.getContext().getAccountManager().isRememberPass());
            autoLogin.setSelected(AppContext.getContext().getAccountManager().isAutoLogin());
        }

        loginTask = doLogin();
    }

    public Node getMaskNode() {
        return main;
    }

    public Task<Boolean> doLogin() {
        Task<Boolean> loginTask = new Task<Boolean>() {
            @Override
            protected Boolean call() {
                try {
                    Boolean result;

                    loadBar = new ProgressBar(0);
                    loadBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

                    Platform.runLater(() -> {
                        main.getChildren().remove(registerButton);
                        main.getChildren().remove(loginButton);
                        main.getChildren().add(loadBar);
                        grid.setDisable(true);
                    });

                    Account account = AppContext.getContext().getAccountManager().getAccount();
                    if (account == null || account.getUser().isEmpty() || account.getPassword().isEmpty()) {
                        account = new Account(getUsername(), getPassword());
                    }

                    if (requestLogin(account)) {
                        AppContext.getContext().getAccountManager().setAccount(account);
                        result = Boolean.TRUE;
                    } else {
                        Platform.runLater(() -> {
                            setStatus("Login not possible!");
                        });
                        result = Boolean.FALSE;
                    }
                    Platform.runLater(() -> {
                        main.getChildren().remove(loadBar);
                        main.getChildren().add(loginButton);
                        main.getChildren().add(registerButton);
                        grid.setDisable(false);
                    });
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        };

        loginTask.setOnFailed(t -> {
            // This handler will be called if exception occured during your task execution
            // E.g. network or db connection exceptions
            setStatus("Login failed!");
        });

        return loginTask;
    }

    private boolean requestLogin(Account account) {
        AppContext.getContext().getAccountManager().setAccount(account);
        if (AppContext.getContext().getAccountManager().updateAccessToken()) {
            AppContext.getContext().getAccountManager().createActiveProfile(rememberPass.isSelected(), autoLogin.isSelected());
            //AppContext.getContext().getAccountManager().updateSession();
            return true;
        } else {
            AppContext.getContext().getAccountManager().setAccount(null);
            return false;
        }
    }

    private void doLogout(ILoginHandler ILoginHandler) {
        AppContext.getContext().getAccountManager().setAccount(null);

        this.setLoginTask(this.doLogin());
        ILoginHandler.setAppContextLoginBehaviour(this);

        //show login layout
        MainScene.mainScene.getBorderPane().setTop(null);
        MainScene.mainScene.getBorderPane().setCenter(this.getMaskNode());

        //remove account menu from title bar
        MainScene.mainScene.getWindowScene().getWin().getTitlebarButtonBox().getChildren().remove(MainScene.mainScene.getWindowScene().getWin().getTitlebarButtonBox().getChildren().size() - 1);
    }

    public void addAccountMenu(ILoginHandler loginHandler) {
        MenuItem menuItem1 = new MenuItem("Account");
        menuItem1.setOnAction(event -> {
            new AccountScene();
        });

        MenuItem menuItem2 = new MenuItem("Logout");
        menuItem2.setOnAction(event -> {
            doLogout(loginHandler);
            loginHandler.setOnLogout();
            MainScene.mainScene.getWindowScene().getWin().getContentController().removeSidebarItemByIdentifier("pluginmanager");
        });

        MenuButton menuButton = new MenuButton(AppContext.getContext().getAccountManager().getAccount().getUser(), null, menuItem1, menuItem2);
        MainScene.mainScene.getWindowScene().getWin().getTitlebarButtonBox().getChildren().addAll(menuButton);
    }

    public Task<Boolean> getLoginTask() {
        return loginTask;
    }

    private String getUsername() {
        return username.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public void setStatus(String text) {
        loginLabel.setText(text);
    }

    public void setLoginTask(Task<Boolean> loginTask) {
        this.loginTask = loginTask;
    }
}
