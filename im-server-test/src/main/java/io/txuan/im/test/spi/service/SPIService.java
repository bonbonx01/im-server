package io.txuan.im.test.spi.service;


import io.txuan.im.server.spi.annotation.SPI;

@SPI("spiService")
public interface SPIService {
    String hello(String name);
}
