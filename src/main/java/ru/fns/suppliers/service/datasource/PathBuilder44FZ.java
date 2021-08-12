package ru.fns.suppliers.service.datasource;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fns.suppliers.cdi.LawType;
import ru.fns.suppliers.cdi.Protocol;
import ru.fns.suppliers.model.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;

@RequestScoped
@Protocol(name = LawType.FZ44)
public class PathBuilder44FZ implements PathBuilder {

    Logger logger = LoggerFactory.getLogger(PathBuilder44FZ.class);

    @ConfigProperty(name = "DailyLoad") // для теста
    private String dailyLoad;

    @ConfigProperty(name = "PrevMonthLoad") // для теста
    private String prevMonthLoad;

    @ConfigProperty(name = "AnnualLoad") // боевая загрузка
    private String annualLoad;

    @ConfigProperty(name = "FullLoad") // todo Падает на кластере, при смене роута
    private String fullLoad;

    private final Path path;

    public PathBuilder44FZ() {
        path = new Path();
    }

    // Формирует список директорий, по которому camel должен пробежаться и забрать документы
    @Override
    public Optional<Path> lookUp() {
        logger.info("Path for daily load: " + dailyLoad);
        path.setLawType(LawType.FZ44);
        path.setListPath(new ArrayList<String>(Arrays.asList(dailyLoad)));
        return Optional.ofNullable(path);
    }
}
