import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.rtf.RtfWriter2;

import java.io.FileOutputStream;
import java.time.Instant;

public class Test {
    public static void main(String[] args) {
        WordUtils utils = new WordUtils();
        Document document = new Document(PageSize.A4);
        String path = "D:/test/word" + Instant.now().getEpochSecond() + ".doc";

        try {
            RtfWriter2.getInstance(document, new FileOutputStream(path));
            document.open();
            int level = 0;


            //解析html
            utils.parseHtml(document, level, HtmlUtils.getHtml());


            document.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}
