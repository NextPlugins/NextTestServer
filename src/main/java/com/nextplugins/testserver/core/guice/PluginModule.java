package com.nextplugins.testserver.core.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.henryfabio.sqlprovider.executor.SQLExecutor;
import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.api.model.group.utils.GroupUtils;
import com.nextplugins.testserver.core.api.model.player.User;
import com.nextplugins.testserver.core.api.model.player.repository.adapter.UserAdapter;
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

        bind(SQLExecutor.class)
                .toInstance(new SQLExecutor(nextTestServer.getSqlConnector()));

        requestStaticInjection(User.class, UserAdapter.class, GroupUtils.class);

    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

}
