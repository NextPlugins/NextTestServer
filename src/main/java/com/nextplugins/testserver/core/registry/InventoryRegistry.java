package com.nextplugins.testserver.core.registry;

import com.nextplugins.testserver.core.api.model.player.view.AccountView;
import lombok.Getter;

import javax.inject.Singleton;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Getter
@Singleton
public class InventoryRegistry {

    private AccountView accountView;

    public void init() {
        this.accountView = new AccountView().init();
    }

}
