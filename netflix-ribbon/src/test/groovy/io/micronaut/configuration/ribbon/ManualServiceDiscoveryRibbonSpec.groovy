package io.micronaut.configuration.ribbon

import com.netflix.client.config.CommonClientConfigKey
import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.reactivex.Flowable
import spock.lang.Specification

import javax.inject.Inject
import javax.inject.Singleton

class ManualServiceDiscoveryRibbonSpec extends Specification {

    void "test manual load balancer config"() {
        given:
        ApplicationContext ctx = ApplicationContext.run(
                'micronaut.http.services.foo.urls': 'https://google.com',
                'foo.ribbon.VipAddress':'test'
        )

        when:
        SomeService someService = ctx.getBean(SomeService)

        RibbonLoadBalancer balancer = (RibbonLoadBalancer) someService.client.loadBalancer

        then:
        balancer.clientConfig
        balancer.clientConfig.get(CommonClientConfigKey.VipAddress) == 'test'

        when:
        def si = Flowable.fromPublisher(balancer.select()).blockingFirst()

        then:
        si.URI == URI.create("https://google.com:-1")

        cleanup:
        ctx.close()

    }

    void "test manual load balancer config with Ribbon config"() {
        given:
        ApplicationContext ctx = ApplicationContext.run(
                'foo.ribbon.listOfServers':'https://google.com'
        )

        when:
        SomeService someService = ctx.getBean(SomeService)
        RibbonLoadBalancer balancer = (RibbonLoadBalancer) someService.client.loadBalancer

        then:
        balancer.clientConfig

        when:
        def si = Flowable.fromPublisher(balancer.select()).blockingFirst()

        then:
        si.URI == URI.create("https://google.com:443")

        cleanup:
        ctx.close()

    }

    @Singleton
    static class SomeService {
        @Inject @Client('foo') RxHttpClient client

    }

}
