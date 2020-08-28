package io.micronaut.configuration.ribbon

import com.netflix.client.config.CommonClientConfigKey
import com.netflix.client.config.IClientConfig
import com.netflix.loadbalancer.DummyPing
import com.netflix.loadbalancer.IPing
import com.netflix.loadbalancer.IRule
import com.netflix.loadbalancer.ServerListFilter
import com.netflix.loadbalancer.ZoneAffinityServerListFilter
import com.netflix.loadbalancer.ZoneAvoidanceRule
import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Prototype
import io.micronaut.inject.qualifiers.Qualifiers
import spock.lang.Specification

/**
 * @author graemerocher
 * @since 1.0
 */
class RibbonConfigSpec extends Specification {

    void "test that a custom default ping is possible"() {
        expect:
        ApplicationContext.run().getBean(IPing) instanceof MyPing
        ApplicationContext.run().getBean(IRule) instanceof MyZoneAvoidanceRule
        ApplicationContext.run().getBean(ServerListFilter) instanceof MyZoneAffinityFilter
    }

    void "test named IClientConfig configuration"() {
        given:
        ApplicationContext applicationContext = ApplicationContext.run('ribbon.clients.foo.VipAddress':'test')

        expect:
        applicationContext.getBean(IClientConfig, Qualifiers.byName("foo")).get(CommonClientConfigKey.VipAddress) == 'test'
    }

    @Prototype
    static class MyPing extends DummyPing {

    }

    @Prototype
    static class MyZoneAvoidanceRule extends ZoneAvoidanceRule {

    }

    @Prototype
    static class MyZoneAffinityFilter extends ZoneAffinityServerListFilter {
        MyZoneAffinityFilter(IClientConfig niwsClientConfig) {
            super(niwsClientConfig)
        }
    }
}
