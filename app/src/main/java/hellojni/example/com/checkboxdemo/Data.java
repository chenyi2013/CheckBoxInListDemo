package hellojni.example.com.checkboxdemo;

/**
 * Created by kevin on 15/9/12.
 */
public class Data {

    private String title;
    private String lable;
    private boolean isLable;

    public Data(){

    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public boolean isLable() {
        return isLable;
    }

    public void setIsLable(boolean isLable) {
        this.isLable = isLable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
