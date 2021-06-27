package org.tritonhacks.memegenerator;

class MemeTemplate {

    private String id;
    private String url;
    private int boxCount;

    /**
     * Constructor setting each instance variable to the corresponding parameter
     * @param id id of meme template image
     * @param url url to meme template image
     * @param boxCount the number of text(boxes) on the meme
     */
    public MemeTemplate(String id, String url, int boxCount) {
        this.id = id;
        this.url = url;

        // TODO: set boxCount field
        this.boxCount = boxCount;
    }

    /**
     * Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set id.
     * @param id id of meme template image
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set url.
     * @param url url to meme template image
     */
    public void setUrl(String url) {
        this.url = url;
    }

    // TODO: implement getter and setter for boxCount
    public int getBoxCount(){ return boxCount; }

    /**
     * Returns boxCount
     */

    public void setBoxCount(int boxCount){ this.boxCount=boxCount;}

    /**
     * Sets boxCount
     */
}
