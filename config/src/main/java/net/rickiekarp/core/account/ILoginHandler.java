package net.rickiekarp.core.account;

import net.rickiekarp.core.view.layout.LoginMaskLayout;

public interface ILoginHandler {
    void setAppContextLoginBehaviour(LoginMaskLayout loginMaskLayout);
    void setOnLogout();
}
