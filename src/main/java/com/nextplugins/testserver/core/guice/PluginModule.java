package com.nextplugins.testserver.core.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.api.model.group.utils.GroupUtils;
import com.nextplugins.testserver.core.api.model.player.Account;
import com.nextplugins.testserver.core.api.model.player.dao.adapter.AccountAdapter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.logging.Logger;

@EqualsAndHashCode(callSuper = false)
@Data(staticConstructor = "of")
public class PluginModule extends AbstractModule {

    private final NextTestServer nextTestServer;

    @Override
    protected void configure() {

        bind(NextTestServer.class)
                .toInstance(nextTestServer);

        bind(Logger.class)
                .annotatedWith(Names.named("main"))
                .toInstance(nextTestServer.getLogger());

        requestStaticInjection(Account.class, AccountAdapter.class, GroupUtils.class);

    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

}
