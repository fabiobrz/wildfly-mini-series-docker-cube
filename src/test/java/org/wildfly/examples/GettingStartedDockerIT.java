package org.wildfly.examples;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import org.arquillian.cube.HostIp;
import org.arquillian.cube.HostPort;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URI;

/**
 * Run integration tests with Arquillian to be able to test CDI beans
 */
@RunWith(Arquillian.class)
public class GettingStartedDockerIT {

    @HostIp
    private String wildflyIp;

    @HostPort(containerName = "wildfly", value = 8080)
    int wildflyPort;

    @Test
    public void testHelloEndpoint() {
        try (Client client = ClientBuilder.newClient()) {
            final String name = "World";
            Response response = client
                    .target(URI.create("http://" + wildflyIp + ":" + wildflyPort + "/"))
                    .path("/hello/" + name)
                    .request()
                    .get();

            Assert.assertEquals(200, response.getStatus());
            Assert.assertEquals(String.format("Hello '%s'.", name), response.readEntity(String.class));

        }
    }
}