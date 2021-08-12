package ru.fns.suppliers.service.client;

import org.apache.commons.net.ftp.FTPHTTPClient;
import javax.enterprise.inject.Produces;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fns.suppliers.cdi.Proxy;

import java.io.IOException;

public class FTPClientFactory {

	Logger logger = LoggerFactory.getLogger(FTPClientFactory.class);

	@ConfigProperty(name = "proxy.passiveMode")
	private boolean passiveMode;

	@ConfigProperty(name = "proxy.url")
	private String proxy;

	@ConfigProperty(name = "proxy.port")
	private int proxyPort;

	@Produces
	@Proxy
	public FTPHTTPClient getClient () throws IOException {

		var ftpClient = new FTPHTTPClient(proxy, proxyPort);

		logger.info("passiveMode value - " + passiveMode);

		if (passiveMode) {
			logger.info("Switch on passive mode - " + passiveMode);
			ftpClient.enterLocalPassiveMode();
		}

		return ftpClient;
	}
}
