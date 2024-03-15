package ru.hse.edu.tukach;

import static org.mockito.AdditionalAnswers.delegatesTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class TestBeanPostProcessor implements BeanPostProcessor {

    private final Set<Class<?>> classesToSpy = new HashSet<>();

    private TestBeanPostProcessor() {
        Reflections reflections = new Reflections("ru.hse");
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(SpringBootTest.class);
        for (Class<?> aClass : typesAnnotatedWith) {
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                Class<?> type = field.getType();
                if (field.isAnnotationPresent(Autowired.class) && (type.getName().startsWith("ru.hse"))) {
                    classesToSpy.add(field.getType());
                }
            }
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> originalBeanClass = AopUtils.getTargetClass(bean);
        if (classesToSpy.contains(originalBeanClass)) {
            try {
                if (AopUtils.isCglibProxy(bean)) {
                    return mock(originalBeanClass, delegatesTo(bean));
                }
                return spy(bean);
            } catch (Exception e) {
                logger.warn(
                    "Error occurred during creating a spy for bean <{}> of class <{}>",
                    beanName, originalBeanClass.getName(), e
                );
                return bean;
            }
        }
        return bean;
    }
}

