package xyz.dongik.project.downloader;

/**
 * Created by dongik on 17. 12. 28.
 */

public class Result{
    int success,fail;
    int total;
    String url;
    Result(){

    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public int getTotal() {
        total = success+fail;
        return total;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
