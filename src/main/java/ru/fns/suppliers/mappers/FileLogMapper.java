package ru.fns.suppliers.mappers;

import ru.fns.suppliers.model.UnfairSuppliersLogDto;
import ru.fns.suppliers.model.UnfairSuppliersLog;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FileLogMapper implements Mapper<UnfairSuppliersLogDto,UnfairSuppliersLog> {

    public UnfairSuppliersLog map(UnfairSuppliersLogDto model) {
        return new UnfairSuppliersLog(
                model.getFileName(),
                model.getDateCreated(),
                model.getDateDownload(),
                model.getMd5Hash());
    }

    public UnfairSuppliersLogDto mapDto(UnfairSuppliersLog entity) {
        return new UnfairSuppliersLogDto(
                entity.getFileName(),
                entity.getDateCreated());
    }
}
