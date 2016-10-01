package com.dmcapps.navigationfragment.v17.core;

import com.dmcapps.navigationfragment.R;

/**
 * Created by dcarmo on 2016-10-01.
 */
public class ConfigManager extends com.dmcapps.navigationfragment.common.core.ConfigManager {

    public ConfigManager() {
        setDefaultPresetAnim(R.animator.slide_in_from_right, 0);
        setDefaultDismissAnim(0, 0);
    }

}
