package vn.phuongcong.math12c3.data;

/**
 * Created by congp on 12/9/2017.
 */

public class Chapter {
   Integer chapterID;
   String chapterName;
   String chapterIndex;

    public String getChapterIndex() {
        return chapterIndex;
    }

    public Chapter(Integer chapterID, String chapterName, String chapterIndex) {
        this.chapterID = chapterID;
        this.chapterName = chapterName;
        this.chapterIndex = chapterIndex;
    }

    public void setChapterIndex(String chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    public Integer getChapterID() {
        return chapterID;
    }

    public void setChapterID(Integer chapterID) {
        this.chapterID = chapterID;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }


}
