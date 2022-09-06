package com.kit.mock.processor;

import com.kit.mock.exception.KitMockException;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.mockito.Mockito;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.util.List;

public class MockDubboBeanProcessor implements BeanPostProcessor, BeanFactoryPostProcessor {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> userClass = ClassUtils.getUserClass(bean);
        List<Field> annotatedFields = FieldUtils.getFieldsListWithAnnotation(userClass, DubboReference.class);
        for (Field field : annotatedFields) {
            Class<?> type = field.getType();
            String mockBeanName = BeanFactoryUtils.transformedBeanName(type.getSimpleName());
            if (!beanFactory.containsBean(mockBeanName)) {
                GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
                genericBeanDefinition.setInstanceSupplier(() -> Mockito.mock(type));
                genericBeanDefinition.setPrimary(true);
                ((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(mockBeanName, genericBeanDefinition);
            }
            try {
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                field.set(bean, beanFactory.getBean(mockBeanName));
                field.setAccessible(accessible);
            } catch (IllegalAccessException e) {
                throw new KitMockException("Mock Dubbo Bean Error", e);
            }
        }
        return bean;
    }

}
