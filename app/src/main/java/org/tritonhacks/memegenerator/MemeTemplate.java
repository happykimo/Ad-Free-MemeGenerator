package org.tritonhacks.memegenerator;

class MemeTemplate {

    private final String id;
    private final String url;
    private final int boxCount;

    MemeTemplate(final String id, final String url, final int boxCount) {
        this.id = id;
        this.url = url;
        this.boxCount = boxCount;
    }

    String getId() {
        return id;
    }

    String getUrl() {
        return url;
    }

    int getBoxCount() {
        return boxCount;
    }
}
