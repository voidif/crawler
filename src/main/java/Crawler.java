import java.util.List;

interface Crawler {
    /**
     * Init
     */
    void init();

    /**
     * Get a un crawled page, mark it as crawled
     */
    WebPage getUncrawlPage();

    /**
     * Add new pages to crawl list
     */
    List<WebPage> addToCrawlList(List<WebPage> webPages);

    /**
     * Save song to known song list
     */
    Song saveSong(Song song);

    /**
     * Get all know songs
     */
    List<Song> getSongs();

    /**
     * Get a un crawled page, parse its html and mark new pages
     */
    void doRun();

    /**
     * Main function
     */
    default void run() {
        init();
        doRun();
    }

}
