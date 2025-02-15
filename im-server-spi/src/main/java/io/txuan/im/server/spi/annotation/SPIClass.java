package io.txuan.im.server.spi.annotation;

import java.lang.annotation.*;

/**
 * 标注到SPI机制接口的实现类上
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SPIClass {
}
