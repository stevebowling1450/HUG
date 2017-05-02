package com.interapt.togglit.ui.forgotPassword;

import com.interapt.togglit.ui.base.MvpView;

/**
 * Created by miller.barrera on 16/11/2016.
 */

public interface ForgotPasswordView extends MvpView {
    void showSuccessForgotPassword();

    void isValidEmail(Boolean aBoolean);

}
