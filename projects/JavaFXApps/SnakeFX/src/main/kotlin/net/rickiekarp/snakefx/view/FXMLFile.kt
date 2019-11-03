package net.rickiekarp.snakefx.view;

import java.net.URL;

public enum FXMLFile {

	PANEL("panel.fxml")

	;

	private static final String BASE_DIR = "fxml";

	private URL url;

	FXMLFile(final String path) {

		URL base = this.getClass().getClassLoader().getResource(BASE_DIR);
		if (base == null) {
			throw new IllegalStateException("Can't find the base directory of the fxml files [" + base + "]");
		}

		final String fxmlFilePath = BASE_DIR + "/" + path;
		url = this.getClass().getClassLoader().getResource(fxmlFilePath);

		if (url == null) {
			throw new IllegalStateException("Can't find the fxml file [" + fxmlFilePath + "]");
		}
	}

	public URL url() {
		return url;
	}
}
