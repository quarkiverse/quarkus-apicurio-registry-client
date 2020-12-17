package io.quarkiverse.apicurio.test;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.apicurio.registry.client.RegistryRestClientFactory;
import io.quarkus.test.QuarkusUnitTest;

class QuarkiverseApicurioRegistryClientTest {

    @RegisterExtension
    static final QuarkusUnitTest unitTest = new QuarkusUnitTest() // Start unit test with your extension loaded
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class));

    @Test
    public void testRetrofitInitializes() {
        RegistryRestClientFactory.create("http://localhost:8080/api");
    }

}
