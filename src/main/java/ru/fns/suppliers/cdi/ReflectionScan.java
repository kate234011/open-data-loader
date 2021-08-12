package ru.fns.suppliers.cdi;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fns.suppliers.service.datasource.PathBuilder;

import javax.enterprise.context.RequestScoped;
import java.util.Optional;
import java.util.Set;

@Reflection
@RequestScoped
public class ReflectionScan {

    Logger logger = LoggerFactory.getLogger(ReflectionScan.class);

    @ConfigProperty(name = "DefaultPackage")
    private String projectPackage;

    private Optional<Class<? extends PathBuilder>> clazz;


    public ReflectionScan() {
        clazz = Optional.empty();
    }

    public boolean isLawExisted(String lawType) {

        LawType law = LawType.fromString(lawType);

        if (law != LawType.UNKNOWN_LAW) {
            clazz = reflectionsScan(law);
        }

        return clazz.isPresent();
    }

    private Optional<Class<? extends PathBuilder>> reflectionsScan(LawType annotation) {
        Reflections reflections = new Reflections(projectPackage);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Protocol.class);

        for(Class<?> cls: classes) {                                   //TODO Переписать на стримы
            Protocol target = cls.getAnnotation(Protocol.class);
            if (target != null && target.name() == annotation){
                clazz = Optional.ofNullable((Class<? extends PathBuilder>) cls);
                break;
            };
        }                                                           //TODO Переписать на стримы
        return clazz;
    }
}