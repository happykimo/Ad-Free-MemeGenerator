package org.tritonhacks.memegenerator;

public class RandomMeme {

    private String postLink;

    private String subreddit;
    private String title;
    private String url;

    /**
     * Constructor setting each instance variable to the corresponding parameter
     * @param postLink link to post
     * @param subreddit a specific community in Reddit
     * @param title title of meme
     * @param url url to meme
     */
    public RandomMeme(String postLink, String subreddit, String title, String initUrl) {

        this.postLink = postLink;
        this.subreddit = subreddit;
        this.title = title;

        // TODO: set url field
        url=initUrl;
    }

    /**
     * Returns the post link.
     */
    public String getPostLink() {
        return this.postLink;
    }

    /**
     * Set postLink.
     * @param postLink link to post
     */
    public void setPostLink(String postLink) { this.postLink = postLink; }

    /**
     * Returns the subreddit.
     */
    public String getSubreddit() {
        return this.subreddit;
    }

    /**
     * Set subreddit.
     * @param subreddit a specific community in Reddit
     */
    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    /**
     * Returns the title of meme.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Set title.
     * @param postLink title of meme
     */
    public void setTitle(String title) {
        this.title = title;
    }

    // TODO: implement getter and setter for url

    public String getUrl() {return this.url; }
    /**
    *Returns the url of the meme.
     */
    public void setUrl(String url){ this.url = url;}
    /**
     * Set url
     */
}
