package io.zhijian.log.annotation;

import java.lang.annotation.*;

/**
 * @author Hao
 * @create 2017-03-29
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**模块*/
    String module() default "";

    /**描述*/
    String description() default "";
}
