package dxh.support.pdf.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * pdf表格列注解
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface VePdfTableColumn {

	String name();

	int sortNum();

	float size() default 8;

}
