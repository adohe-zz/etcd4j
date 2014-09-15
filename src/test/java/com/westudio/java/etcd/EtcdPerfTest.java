package com.westudio.java.etcd;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EtcdPerfTest {
    String prefix;
    EtcdClient client;
    static final int N_THREADS = 8;

    @Before
    public void setUp() throws Exception {
        this.prefix = "perf-jfasjdlajsldjalksjdlajdlajdksdjalsjdlajdljaslkdjlasjdlajdklajsdlkajsdlkjasldjaldjaljdglajglakjdlkajdlkajdljsaldjskdjlaksjdlajdglajgdljaldgjsakldjalsdjlasjdlajdglajdlajsdljaskdjaksldjakldjlajdlajdglakjksjdksjdljdkjaldgjalkdjlsjdkalsjdglajsdlakjdglajsdglajdglajsdlksajdkasljdglajsdlajsdglajdglajsdljasdkjsldjglasjdglajsdglasjdlajsdlasdgjlasdjgalsjdgajgeoajdfalsdjglajgdlasjdlasjdglajdglasjdlasjdlasjgaljdglajdgaldjglajdglajdglajsdlsajdlajgdlasjdlsjdalsdjgaljdgaldjlsajdlasjdlajdglajgdlajglajgalgjalsjdkjalsgdjaljgdaljglajgaljgdjasljljllsdfjlakjsdglajgsdljasdgjlajlgdsagjlaljgaljsdjlasgdljjl" + UUID.randomUUID().toString();
        this.client = new EtcdClient(URI.create("http://etcd.soa.uat.qa.nt.ctripcorp.com/"));
    }
    @Test
    public void testPut() throws Exception{
        int count = 10000;
        long start = System.nanoTime();

        for(int i = 0; i < count; i++) {
            long sstart = System.currentTimeMillis();
            EtcdResponse result = client.set(prefix + i, prefix + i);
            assertNotNull(result);
            long send = System.currentTimeMillis();
            System.out.println("end 1: " + (send - sstart));
        }

        long endOne = System.nanoTime();
        System.out.printf("Put %4f k operations per second%n", (double)(count * 1e6 / (endOne - start)));
        System.out.println("end 1");

        for(int i = 0; i < count; i++) {
            EtcdResponse result = client.get(prefix + i);
            assertNotNull(result);
            assertEquals(prefix + i, result.node.value);
        }

        long endTwo = System.nanoTime();
        System.out.printf("Get %4f k operations per second%n", (double)(count * 1e6 / (endTwo - endOne)));
        System.out.println("end 2");

        for (int i = 0; i < count; i++) {
            client.delete(prefix + i);
        }

        long endThree = System.nanoTime();
        System.out.printf("Del %4f k operations per second%n", (double)(count * 1e6 / (endThree - endTwo)));
        System.out.println("end 3");

        long time = System.nanoTime() - start;
        System.out.printf("Put/get %4f K operations per second%n",
                (double)(count * 3 * 1e6 / time));
    }

    @Test
    public void testMultThreadsPut() throws ExecutionException, InterruptedException, IOException {
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        System.out.println("Starting test");
        final int COUNT = 4000;
        long start = System.nanoTime();
        List<Future<?>> futures = new ArrayList<Future<?>>();
        for(int t = 0; t < N_THREADS; t++) {
            final int finalT = t;
            futures.add(es.submit(new Runnable() {

                @Override
                public void run() {
                    try {
                        StringBuilder user = new StringBuilder();
                        for (int i = finalT; i < COUNT; i += N_THREADS) {
                            client.set(prefix + i, prefix + i);
                        }
                        System.out.println("testMultThreadsPut end 1");
                        for (int i = finalT; i < COUNT; i += N_THREADS) {
                            EtcdResponse result = client.get(prefix + i);
                            assertNotNull(result);
                            assertEquals(prefix + i , result.node.value);
                        }
                        System.out.println("testMultThreadsPut end 2");
                        for (int i = finalT; i < COUNT; i += N_THREADS)
                            assertNotNull(client.get(prefix + i));
                        System.out.println("testMultThreadsPut end 3");
                        for (int i = finalT; i < COUNT; i += N_THREADS)
                            client.delete(prefix + i);

                        System.out.println("testMultThreadsPut end 4");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }));
        }
        for (Future<?> future : futures) {
            future.get();
        }

        System.out.println("end test");
        long time = System.nanoTime() - start;
        System.out.printf("Put/get %,f K operations per second%n",
                (double) (COUNT * 4 * 1e6 / time));
        es.shutdown();

    }
}
