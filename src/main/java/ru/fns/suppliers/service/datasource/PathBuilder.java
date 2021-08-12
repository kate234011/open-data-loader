package ru.fns.suppliers.service.datasource;

import ru.fns.suppliers.model.Path;
import java.util.Optional;


// Интерфейс должен использоваться для 44-ФЗ, 223-ФЗ, 665-ФЗ
public interface PathBuilder {

   Optional<Path> lookUp();

}
