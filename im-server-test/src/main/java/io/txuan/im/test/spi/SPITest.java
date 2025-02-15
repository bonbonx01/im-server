package io.txuan.im.test.spi;


import io.txuan.im.server.spi.loader.ExtensionLoader;
import io.txuan.im.test.spi.service.SPIService;
import org.junit.Test;

public class SPITest {
    @Test
    public void testSpiLoader(){
        SPIService spiService = ExtensionLoader.getExtension(SPIService.class, "spiService");
        String res = spiService.hello("tx");
        System.out.println(res);
    }
}
