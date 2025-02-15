package io.txuan.im.test.spi.service.impl;

import io.txuan.im.server.spi.annotation.SPIClass;
import io.txuan.im.test.spi.service.SPIService;
@SPIClass
public class SPIServiceImpl implements SPIService {
    @Override
    public String hello(String name) {
        return "hello " + name;
    }
}
