package com.interapt.togglit.ui.schoolDirectory;

import com.interapt.togglit.data.model.lists.Schools;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Matthew.Watson on 3/21/17.
 */

public interface SchoolDirectoryUseCase {

    void getSchools(Subscriber<List<Schools>> subscriber);

    void destroy();
}
