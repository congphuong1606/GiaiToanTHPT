package vn.phuongcong.math12c3.data;

import android.app.Activity;
import android.net.Uri;


import com.artifex.mupdf.viewer.MuPDFCore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Ominext on 1/9/2018.
 */

public class MuPdfData {
    public static Uri getUri(String filename) {
        String outfile = "data/data/vn.phuongcong.math12c3/databases/" + filename + ".pdf";
        File file = new File(outfile);
        if (file.exists()) {
            return Uri.fromFile(file);
        } else return null;
    }

    public static void getUriFromFileCopy(Activity activity) {
        for (int i = 1; i <= 136; i++) {
            try {

                String outFileName = activity.getApplicationInfo().dataDir + "/databases/" + i + ".pdf";
                File f = new File(outFileName);
                if (!f.exists()) {
                    InputStream e = activity.getAssets().open(i + ".pdf");
                    File folder = new File(activity.getApplicationInfo().dataDir + "/databases/");
                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                    FileOutputStream myOutput = new FileOutputStream(outFileName);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = e.read(buffer)) > 0) {
                        myOutput.write(buffer, 0, length);
                    }
                    myOutput.flush();
                    myOutput.close();
                    e.close();
                } else {

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public static MuPDFCore openFile(MuPDFCore core, String path) {
        int lastSlashPos = path.lastIndexOf('/');
        String mFileName = new String(lastSlashPos == -1
                ? path
                : path.substring(lastSlashPos + 1));
        System.out.println("Trying to open " + path);
        try {
            core = new MuPDFCore(path);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } catch (OutOfMemoryError e) {
            //  out of memory is not an Exception, so we catch it separately.
            System.out.println(e);
            return null;
        }
        return core;
    }

    public static MuPDFCore openBuffer(MuPDFCore core, byte buffer[], String magic) {
        System.out.println("Trying to open byte buffer");
        try {
            core = new MuPDFCore(buffer, magic);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return core;
    }
}
