package ru.fns.suppliers.service.client;

import ru.fns.suppliers.model.Path;
import java.util.List;

public interface Client {

   String load(List<Path> path);

}
