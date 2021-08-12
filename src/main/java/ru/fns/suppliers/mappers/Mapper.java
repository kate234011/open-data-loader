package ru.fns.suppliers.mappers;

public interface Mapper<T, K> {

	K map(T dto);

	T mapDto(K entity);
}
