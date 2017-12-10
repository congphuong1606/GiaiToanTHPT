package vn.phuongcong.math12c3.data;

import java.io.Serializable;

/**
 * Created by congp on 12/9/2017.
 */

public class Lesson implements Serializable {
    int id;
    String lesonName;
    byte[] lesonGiai;
    byte[] lesonNoiDung;

    public Lesson(int id, String lesonName, byte[] lesonGiai, byte[] lesonNoiDung) {
        this.id = id;
        this.lesonName = lesonName;
        this.lesonGiai = lesonGiai;
        this.lesonNoiDung = lesonNoiDung;
    }

    public byte[] getLesonGiai() {
        return lesonGiai;
    }

    public void setLesonGiai(byte[] lesonGiai) {
        this.lesonGiai = lesonGiai;
    }

    public byte[] getLesonNoiDung() {
        return lesonNoiDung;
    }

    public void setLesonNoiDung(byte[] lesonNoiDung) {
        this.lesonNoiDung = lesonNoiDung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLesonName() {
        return lesonName;
    }

    public void setLesonName(String lesonName) {
        this.lesonName = lesonName;
    }
}
