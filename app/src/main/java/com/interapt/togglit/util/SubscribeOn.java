package com.interapt.togglit.util;

import rx.Scheduler;

/**
 * Created by stevebowling on 3/21/17.
 */

public interface SubscribeOn {

    Scheduler getScheduler();

}
