package com.interapt.togglit.ui.base;


import com.interapt.togglit.common.Constants;

/**
 * Created by miller.barrera
 */
public interface MvpView {

    /**
     * Show the error event.
     */
    void showError(String message, @Constants.ErrorType int errorType);

    /**
     * Show loader component
     *
     * @param show : show if it is true.
     */
    void showRefresh(boolean show);

}
