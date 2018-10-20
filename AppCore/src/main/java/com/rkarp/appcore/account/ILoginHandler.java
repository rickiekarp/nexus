package com.rkarp.appcore.account;

import com.rkarp.appcore.view.layout.LoginMaskLayout;

public interface ILoginHandler {
    void setAppContextLoginBehaviour(LoginMaskLayout loginMaskLayout);
    void setOnLogout();
}
