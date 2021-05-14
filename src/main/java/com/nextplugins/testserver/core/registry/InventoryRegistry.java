package com.nextplugins.testserver.core.registry;

import com.nextplugins.testserver.core.api.model.player.view.UserView;
import lombok.Getter;

import javax.inject.Singleton;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Getter
@Singleton
public class InventoryRegistry {

    private UserView userView;

    public void init() {
        this.userView = new UserView().init();
    }

}
