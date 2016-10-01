package com.dmcapps.navigationfragment.v7.core;

import com.dmcapps.navigationfragment.R;

/**
 * Created by dcarmo on 2016-10-01.
 */
public class ConfigManager extends com.dmcapps.navigationfragment.common.core.ConfigManager {

    public ConfigManager() {
        setDefaultPresetAnim(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        setDefaultDismissAnim(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

}
