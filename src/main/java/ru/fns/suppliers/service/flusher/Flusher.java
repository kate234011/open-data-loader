package ru.fns.suppliers.service.flusher;


public interface Flusher {

	String cleanUpRepository(String lawType);

	String flushRepository(String lawType);
}
