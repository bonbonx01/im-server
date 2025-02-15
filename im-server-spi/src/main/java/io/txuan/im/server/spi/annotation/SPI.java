package io.txuan.im.server.spi.annotation;

import java.lang.annotation.*;

/**
 * 标志到SPI机制接口上
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPI {

    /**
     * 默认的实现方式
     */
    String value() default "";
}