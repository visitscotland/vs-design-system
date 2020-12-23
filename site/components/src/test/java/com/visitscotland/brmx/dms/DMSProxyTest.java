package com.visitscotland.brmx.dms;

import com.visitscotland.brmx.utils.CommonUtils;
import com.visitscotland.brmx.utils.Properties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DMSProxyTest {

    DMSProxy proxy;

    @Mock
    CommonUtils utils;

    @Mock
    HttpURLConnection huc;

    @BeforeEach
    void init() {
        //Resets the last Failure.
        DMSProxy.lastRegisteredFailure = null;

        //Overrides the method that defines that instances the connection
        proxy = new DMSProxy() {
            @Override
            protected HttpURLConnection openConnection(String url) {
                return huc;
            }
        };
    }

    @Test
    @DisplayName("Serves the content of an endpoint that returns a 200 status code")
    void requestContent() throws IOException {
        final String CONTENT = "This is it";
        when(huc.getResponseCode()).thenReturn(200);
        when(huc.getInputStream()).thenReturn(new ByteArrayInputStream(CONTENT.getBytes()));

        Assertions.assertEquals(CONTENT, proxy.request("/happy-path"));

        verify(huc).setRequestProperty(eq(DMSProxy.HEADER_TOKEN), any());
    }

    @Test
    @DisplayName("There were several attempts of retrieving the data")
    void exceptionPropagation() throws IOException {
        // Verifies that the exception is propagated and a number of tries is attempted
        when(huc.getResponseCode()).thenThrow(new SocketTimeoutException());

        Assertions.assertNull(proxy.request("/timeout"));

        verify(huc, times(Properties.DMS_TRIES)).getResponseCode();
    }


    @Test
    @DisplayName("Simultaneous connections are only executed once")
    void simultaneousConnection() throws IOException, InterruptedException {
        //Creates a Pool of 10 simultaneous connections
        ExecutorService service = Executors.newFixedThreadPool(20);
        Properties.DMS_TRIES = 1;

        //Apart from throwing the exceptions it halts the test to ensure concurrent calls to the method
        when(huc.getResponseCode()).then(invocationOnMock -> {
            TimeUnit.SECONDS.sleep(1);
            throw new SocketTimeoutException();
        });

        //Request 50 chuncks of data.
        for (int i = 0; i < 50; i++) {
            service.submit(() -> proxy.request("/gimme-data"));
        }
        //Send the request and wait 10 seconds maximum
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
        if (!service.isTerminated()) {
            Assertions.fail("Concurrency hasn't been managed properly");
        }

        Assertions.assertNotNull(DMSProxy.lastRegisteredFailure);

        verify(huc, times(1)).getResponseCode();
    }

    @Test
    @DisplayName("Test that the lock is release after a while")
    void lockRelease() throws IOException {
        when(huc.getResponseCode()).thenThrow(new SocketTimeoutException());

        for (int i = 0; i < 3; i++) {
            Assertions.assertNull(proxy.request("/fails-and-lock"));
        }

        // Reset the Failure as if it were done an hour ago
        DMSProxy.lastRegisteredFailure = new Date().getTime() - TimeUnit.HOURS.toMillis(1);

        for (int i = 0; i < 3; i++) {
            Assertions.assertNull(proxy.request("/fails-and-lock"));
        }

        verify(huc, times(2)).getResponseCode();
    }

}
