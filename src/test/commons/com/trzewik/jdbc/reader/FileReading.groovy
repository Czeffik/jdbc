package com.trzewik.jdbc.reader

trait FileReading {

    String getText(String fileName) {
        return getFile(fileName).text
    }

    String getAbsolutePath(String fileName) {
        return getFile(fileName).absolutePath
    }

    File getFile(String fileName) {
        return new File(getFileUri(fileName))
    }

    URI getFileUri(String fileName) {
        return this.getClass().getResource("/$fileName").toURI()
    }
}
