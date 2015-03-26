package com.xqbase.etcd4j;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Performance Test.
 *
 * @author Tony He
 */
@RunWith(Parameterized.class)
public class EtcdPerfTest {

    private EtcdClient client;
    private static final int N_THREADS = 8;

    @Parameterized.Parameter(value = 0)
    public String prefix;

    @Parameterized.Parameters
    public static Collection<String[]> data() throws IOException {
        String[][] data = { { "/perfTest-testtest"},
                            { "/perfTest-testtesttesttest"},
                            { "/perfTest-testtesttesttesttesttesttesttest"}};

        return Arrays.asList(data);
    }

    @Before
    public void setUp() throws Exception {
        this.client = new EtcdClient(URI.create("http://localhost:4001/"));
    }

    @After
    public void tearDown() throws Exception {
        if (client == null) {
            return;
        }

        try {
            client.deleteDir(prefix, true);
        } catch (EtcdClientException e) {}
    }

    @Test
    public void testPut() throws Exception{
        System.out.println("Starting testPut");

        int count = 4000;
        long start = System.nanoTime();

        for(int i = 0; i < count; i++) {
            client.set(prefix + i, prefix + i);
        }

        long endOne = System.nanoTime();
        System.out.printf("Put %4f k operations per second%n", (double) (count * 1e6 / (endOne - start)));

        for(int i = 0; i < count; i++) {
            String result = client.get(prefix + i);
            assertNotNull(result);
            assertEquals(prefix + i, result);
        }

        long endTwo = System.nanoTime();
        System.out.printf("Get %4f k operations per second%n", (double) (count * 1e6 / (endTwo - endOne)));

        for (int i = 0; i < count; i++) {
            client.delete(prefix + i);
        }

        long endThree = System.nanoTime();
        System.out.printf("Del %4f k operations per second%n", (double) (count * 1e6 / (endThree - endTwo)));

        long time = System.nanoTime() - start;
        System.out.printf("Put/get %4f K operations per second%n",
                (double)(count * 3 * 1e6 / time));
    }

    @Test
    public void testMultiThreadsPut() throws ExecutionException, InterruptedException, IOException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(N_THREADS);

        final int COUNT = 4000;

        for (int i = 0; i < N_THREADS; i++) {
            final int finalT = i;
            Thread t = new Thread() {
                @Override
                public void run() {

                    try {
                        startGate.await();

                        try {
                            for (int i = finalT; i < COUNT; i += N_THREADS) {
                                client.set(prefix + i, prefix + i);
                            }
                            for (int i = finalT; i < COUNT; i += N_THREADS) {
                                String result = client.get(prefix + i);
                                assertNotNull(result);
                                assertEquals(prefix + i , result);
                            }
                            for (int i = finalT; i < COUNT; i += N_THREADS)
                                assertNotNull(client.get(prefix + i));
                            for (int i = finalT; i < COUNT; i += N_THREADS)
                                client.delete(prefix + i);
                        } catch (Exception e) {
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException ignored){}
                }
            };

            t.start();
        }

        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long time = System.nanoTime() - start;
        System.out.printf("Put/get %,f K operations per second%n",
                (double) (COUNT * 4 * 1e6 / time));
    }

}
