package io.txuan.im.server.spi.factory;

import io.txuan.im.server.spi.annotation.SPI;
import io.txuan.im.server.spi.annotation.SPIClass;
import io.txuan.im.server.spi.loader.ExtensionLoader;

import java.util.Optional;

/**
 * SPI机制 扩展类加载器工厂具体实现类
 */
@SPIClass
public class SpiExtensionFactory implements ExtensionFactory {
    @Override
    public <T> T getExtension(String key, Class<T> clazz) {
        return Optional.ofNullable(clazz)
                .filter(Class::isInterface)
                .filter(cls -> cls.isAnnotationPresent(SPI.class))
                .map(ExtensionLoader::getExtensionLoader)
                .map(ExtensionLoader::getDefaultSpiClassInstance)
                .orElse(null);
    }
}
