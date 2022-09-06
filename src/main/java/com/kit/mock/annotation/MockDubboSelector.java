package com.kit.mock.annotation;

import com.kit.mock.processor.MockDubboBeanProcessor;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 限流器Selector
 *
 * @author <a href = "kamtohung@gmail.com">hongjintao</a>
 * @since 2022/3/8 17:14
 */
public class MockDubboSelector implements DeferredImportSelector {

    @Override
    public String[] selectImports(final AnnotationMetadata importingClassMetadata) {
        return new String[]{
            MockDubboBeanProcessor.class.getName()
        };
    }

}
