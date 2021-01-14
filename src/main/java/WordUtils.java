import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.rtf.graphic.RtfShape;
import com.lowagie.text.rtf.graphic.RtfShapePosition;
import com.lowagie.text.rtf.graphic.RtfShapeProperty;
import com.lowagie.text.rtf.style.RtfParagraphStyle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class WordUtils {


    /**
     * 创建主标题
     *
     * @param document word文档
     * @param title    标题文本
     */
    private void createMainTitle(Document document, String title) throws DocumentException {
        RtfParagraphStyle rtfGsBt1 = RtfParagraphStyle.STYLE_HEADING_1;
        rtfGsBt1.setAlignment(Element.ALIGN_CENTER);
        rtfGsBt1.setStyle(Font.BOLD);
        rtfGsBt1.setSize(14);

        Paragraph p = new Paragraph(title);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p.setFont(rtfGsBt1);
        document.add(p);
        createTitle(document, 0, "");
    }

    /**
     * 创建层级标题
     *
     * @param document word文档
     * @param level    标题等级
     * @param title    标题文本
     */
    private void createTitle(Document document, int level, String title) throws DocumentException {
        Font font = new Font(Font.NORMAL, 10, Font.BOLD, new Color(0, 0, 0));
        Paragraph p = new Paragraph(title, font);
        p.setAlignment(Paragraph.ALIGN_LEFT);
        p.setIndentationLeft(level * 20);
        p.setSpacingAfter(8f);//段前间距
        p.setSpacingBefore(8f);//段后间距
        document.add(p);
    }

    /**
     * 解析html到word
     *
     * @param document word文档
     * @param level    等级编号
     * @param html     富文本html内容
     */
    public void parseHtml(Document document, int level, String html) throws IOException, DocumentException {
        //解析html节点对象
        org.jsoup.nodes.Document htmlDocument = Jsoup.parse(html);
        Elements children = htmlDocument.body().children();
        HashMap<String, String> attrs = new HashMap<>();
        if (children.size() > 0) {
            for (org.jsoup.nodes.Element e : children) {
                createElement(document, e, level, attrs);
            }
        }
    }

    /**
     * 解析节点
     *
     * @param document 文档
     * @param node     节点
     * @param level    等级
     * @param attrs    节点属性
     */
    private void createElement(ElementListener document, Node node, int level, HashMap<String, String> attrs) throws IOException, DocumentException {
        if (node.childNodes().size() == 0) {
            //没有孩子的节点
            createContent(document, node);
        } else {
            //有孩子的节点
            createChildContent(document, node, level, attrs);
        }
    }

    /**
     * 创建没有孩子节点的word项
     *
     * @param document 文档
     * @param node     节点
     */
    private void createContent(ElementListener document, Node node) throws DocumentException {
        String tagName = node.nodeName();
        if ("img".equals(tagName)) {
            Phrase p = new Phrase();
            createImg(p, node);
            document.add(p);
        } else if ("hr".equals(tagName)) {
            createLine(document);
        } else {
            createText(document, node);
        }
    }

    /**
     * 创建普通文字
     *
     * @param document word文档
     * @param node     文字节点
     */
    private void createText(ElementListener document, Node node) throws DocumentException {
        Paragraph p = new Paragraph(node.toString().replace("&nbsp;", ""));
        document.add(p);
    }

    /**
     * 创建横线
     *
     * @param document word文档
     */
    private void createLine(ElementListener document) throws DocumentException {
        RtfShapePosition position;
        position = new RtfShapePosition(150, 0, 10400, 150);
        position.setXRelativePos(RtfShapePosition.POSITION_X_RELATIVE_MARGIN);
        position.setYRelativePos(RtfShapePosition.POSITION_Y_RELATIVE_PARAGRAPH);
        RtfShape shape = new RtfShape(RtfShape.SHAPE_LINE, position);
        RtfShapeProperty property = new RtfShapeProperty(RtfShapeProperty.PROPERTY_LINE_COLOR, Color.decode("#9a9a9a"));
        shape.setProperty(property);
        Paragraph line = new Paragraph(shape);
        document.add(line);
    }

    /**
     * 创建有孩子节点的word项
     *
     * @param document    文档
     * @param node        节点
     * @param level       层次
     * @param parentAttrs 父节点属性集合
     */
    private void createChildContent(ElementListener document, Node node, int level, HashMap<String, String> parentAttrs) throws DocumentException, IOException {
        Paragraph p = new Paragraph();
        p.setSpacingBefore(10);
        p.setIndentationLeft((level + 1) * 10);
        HashMap<String, String> attrs = new HashMap<>(parentAttrs);
        String name = node.nodeName();
        if ("p".equals(name)) {
            createParagraph(p, node, attrs);
        } else if ("table".equals(name)) {
            createTable(p, node, attrs);
        } else if ("ol".equals(name)) {
            createOl(p, node, attrs);
        } else if ("ul".equals(name)) {
            createUl(p, node, attrs);
        } else if ("a".equals(name)) {
            createLink(p, node, attrs);
        }
        document.add(p);
    }

    /**
     * 获取节点样式
     *
     * @param node  节点
     * @param attrs 节点属性集合
     */
    private void getStyles(Node node, HashMap<String, String> attrs) {
        if (node.hasAttr("style")) {
            String style = node.attr("style");
            if (!style.isEmpty()) {
                style = style.replace(" ", "");
                String[] split = style.split(";");
                for (String s : split) {
                    if (s.contains(":")) {
                        String[] split1 = s.split(":");
                        if (split1.length == 2) {
//                            String fuck = attrs.getOrDefault(split1[0], "not found");
//                                if(fuck.equals("not found")){

                            attrs.put(split1[0], split1[1]);

//                                }
                        }
                    }
                }
            }
        }
    }

    /**
     * 创建段落
     *
     * @param paragraphNode 段落系欸但
     */
    private void createParagraph(Paragraph paragraph, Node paragraphNode, HashMap<String, String> attrs) throws IOException, DocumentException {
        getStyles(paragraphNode, attrs);
        String align = attrs.getOrDefault("text-align", "not found");
        if (!"not found".equals(align)) {
            switch (align) {
                case "left":
                    paragraph.setAlignment(Paragraph.ALIGN_LEFT);
                    break;
                case "center":
                    paragraph.setAlignment(Paragraph.ALIGN_CENTER);
                    break;
                case "right":
                    paragraph.setAlignment(Paragraph.ALIGN_RIGHT);
                    break;
                case "justify":
                    paragraph.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                    break;
            }
        }

        String padding = attrs.getOrDefault("padding-left", "not found");
        if (!"not found".equals(padding)) {
            if (padding.endsWith("px")) {
                padding = padding.replace("px", "");
                paragraph.setIndentationLeft(Float.parseFloat(padding) * 0.75f);
            }
        }

        Phrase p = createParagraph(paragraphNode, attrs);
        if (p.getContent().trim().equals("")) {
            paragraph.setSpacingBefore(5);
        }
        paragraph.add(p);
    }

    /**
     * 创建段落
     *
     * @param node  段落节点
     * @param attrs 属性集合
     * @return 段落内容
     */
    private Phrase createParagraph(Node node, HashMap<String, String> attrs) throws IOException, DocumentException {
        Phrase p = new Phrase();

        for (Node childNode : node.childNodes()) {
            String name = childNode.nodeName();
            if (!"#text".equals(name)) {
                //递归创建子节点
                createParagraphContent(p, childNode, attrs);
            } else {
                p.add(childNode.toString().replace("&nbsp;", ""));
            }
        }
        return p;
    }

    /**
     * 创建段落子节点内容
     *
     * @param content     word容器
     * @param node        文档节点
     * @param parentAttrs 父节点属性
     */
    private void createParagraphContent(Phrase content, Node node, HashMap<String, String> parentAttrs) throws IOException, DocumentException {

        // 继承子节点的样式属性
        HashMap<String, String> selfAttrs = new HashMap<>(parentAttrs);

        //分类型处理
        switch (node.nodeName()) {
            case "span":
                createSpan(content, node, selfAttrs);
                break;
            case "a":
                createLink(content, node, selfAttrs);
                break;
            case "img":
                createImg(content, node);
                break;
            case "strong":
                createStrong(content, node, selfAttrs);
                break;
            case "em":
                createEm(content, node, selfAttrs);
                break;
        }
    }

    /**
     * 创建短语
     *
     * @param content   短语
     * @param spanNode  短语节点
     * @param selfAttrs 当前节点的属性集合
     */
    private void createSpan(Phrase content, Node spanNode, HashMap<String, String> selfAttrs) throws IOException, DocumentException {
        Phrase span = new Phrase();
        Chunk spanContent = null;
        if (spanNode.hasAttr("style")) {
            String style = spanNode.attr("style").replace(" ", "");
            String[] cssAttr = style.split(";");
            for (String attr : cssAttr) {
                String[] split = attr.split(":");
                if (selfAttrs.getOrDefault(split[0], "not found").equals("not found")) {
                    selfAttrs.remove(split[0]);
                }
                selfAttrs.put(split[0], split[1]);

                Font fontSpan = span.getFont();
                if (split[0].equals("text-decoration")) {
                    if (split[1].equals("line-through")) {
                        fontSpan.setStyle(8);
                    }
                    if (split[1].equals("underline")) {
                        fontSpan.setStyle(4);
                    }
                }

                if (split[0].equals("color")) {
                    if (split[1].startsWith("#")) {
                        fontSpan.setColor(Color.decode(split[1]));
                    }
                }

                if (split[0].equals("font-size")) {
                    if (split[1].endsWith("pt")) {
                        split[1] = split[1].replace("pt", "");
                        fontSpan.setSize(Float.parseFloat(split[1]));
                    }
                }

//                        // 字体 目前无法设置 因为不能直接赋值 html字体要替换成电脑字体的名称
//                        if (split[0].equals("font-family")) {
//                            if(split[1].contains(",")){
//                                String[] split1 = split[1].split(",");
//                                if(split1[0].startsWith("'")&&split1[0].endsWith("'")){
//                                    String family = split1[0].replace("'", "");
//                                    if(family.equals("Comic Sans MS"))
//                                        fontSpan.setFamily("Comic Sans MS");
//                                }
//                            }
//                        }

                span.setFont(fontSpan);
                if (split[0].equals("background-color")) {
                    if (split[1].startsWith("#")) {
                        if (spanContent == null) {
                            spanContent = new Chunk();
                        }
                        spanContent.setBackground(Color.decode(split[1]));
                    }
                }
            }
        }

        for (Node childNode : spanNode.childNodes()) {
            if (!"#text".equals(childNode.nodeName())) {
                //子节点
                createParagraphContent(content, childNode, selfAttrs);
            } else {
                //普通文字
                String replace = childNode.toString().replaceFirst("^\\s+", "").replace("&nbsp;", "");
                if (spanContent != null) {
                    Chunk c = new Chunk(spanContent);
                    c.append(replace);
                    Phrase sp = new Phrase(span);
                    sp.add(c);
                    content.add(sp);

                } else {
                    Phrase sp = new Phrase(span);
                    sp.add(replace);
                    content.add(sp);
                }
            }
        }
    }

    /**
     * 创建粗体
     *
     * @param content    短语
     * @param strongNode 粗体节点
     * @param selfAttrs  当前节点的属性集合
     */
    private void createStrong(Phrase content, Node strongNode, HashMap<String, String> selfAttrs) throws IOException, DocumentException {

        selfAttrs.put("strong", "true");
        Phrase strongContent = new Phrase();
        Font fontStrong = strongContent.getFont();
        fontStrong.setStyle(1);

        String color = selfAttrs.getOrDefault("color", "not found");
        if (!color.equals("not found")) {
            if (color.startsWith("#")) {
                fontStrong.setColor(Color.decode(color));
            }
        }

        strongContent.setFont(fontStrong);
        for (Node childNode : strongNode.childNodes()) {
            if (!"#text".equals(childNode.nodeName())) {
                createParagraphContent(strongContent, childNode, selfAttrs);
            } else {
                Chunk link = new Chunk(childNode.toString().replace(" ", "").replace("&nbsp;", ""));

                String backgroundColor = selfAttrs.getOrDefault("background-color", "not found");
                if (!backgroundColor.equals("not found")) {
                    if (backgroundColor.startsWith("#")) {
                        link.setBackground(Color.decode(backgroundColor));
                    }
                }

                strongContent.add(link);
            }
        }

        content.add(strongContent);
    }

    /**
     * 创建斜体
     *
     * @param phrase    短语
     * @param emNode    斜体节点
     * @param selfAttrs 当前节点的属性集合
     */
    private void createEm(Phrase phrase, Node emNode, HashMap<String, String> selfAttrs) throws IOException, DocumentException {
        selfAttrs.put("em", "true");
        Phrase emContent = new Phrase();
        Font fontEm = emContent.getFont();
        fontEm.setStyle(2);
        emContent.setFont(fontEm);
        for (Node childNode : emNode.childNodes()) {
            if (!"#text".equals(childNode.nodeName())) {
                createParagraphContent(emContent, childNode, selfAttrs);
            } else {
                Chunk link = new Chunk(childNode.toString().replace(" ", "").replace("&nbsp;", ""));
                String backgroundColor = selfAttrs.getOrDefault("background-color", "not found");
                if (!backgroundColor.equals("not found")) {
                    if (backgroundColor.startsWith("#")) {
                        link.setBackground(Color.decode(backgroundColor));
                    }
                }
                emContent.add(link);
            }
        }

        phrase.add(emContent);
    }

    /**
     * 创建超链接
     *
     * @param paragraph 段落
     * @param linkNode  超链接节点
     * @param selfAttrs 当前节点的属性集合
     */
    private void createLink(Paragraph paragraph, Node linkNode, HashMap<String, String> selfAttrs) throws IOException, DocumentException {
        Phrase linkContent = new Phrase();
        createLink(linkContent, linkNode, selfAttrs);
        paragraph.add(linkContent);
    }

    /**
     * 创建超链接
     *
     * @param phrase    短语
     * @param linkNode  超链接节点
     * @param selfAttrs 当前节点的属性集合
     */
    private void createLink(Phrase phrase, Node linkNode, HashMap<String, String> selfAttrs) throws IOException, DocumentException {

        Phrase linkContent = new Phrase();
        for (Node childNode : linkNode.childNodes()) {
            if (!"#text".equals(childNode.nodeName())) {
                createParagraphContent(linkContent, childNode, selfAttrs);
            } else {
                Chunk link = new Chunk(childNode.toString().replace("&nbsp;", ""));
                if (linkNode.hasAttr("href")) {

                    Font font = link.getFont();
                    font.setColor(Color.blue);
                    font.setStyle(4);
                    link.setFont(font);
                }
                linkContent.add(link);
            }
        }

        com.lowagie.text.Anchor anchor = new Anchor(linkContent);
        if (linkNode.hasAttr("href")) {
            String href = linkNode.attr("href");
            anchor.setReference(href);
            if (linkNode.hasAttr("title")) {
                String title = linkNode.attr("title");
                anchor.setName(title);
            }
            phrase.add(anchor);
        } else {
            phrase.add(linkContent);
        }
    }

    /**
     * 创建有序列表
     *
     * @param paragraph   段落
     * @param olNode      有序列表节点
     * @param parentAttrs 父节点属性集合
     */
    private void createOl(Paragraph paragraph, Node olNode, HashMap<String, String> parentAttrs) {
        com.lowagie.text.List list;
        getStyles(olNode, parentAttrs);

        if (olNode.hasAttr("style")) {
            String style = olNode.attr("style");
            if (!style.contains("list-style-type")) {
                //style里面没有列表样式的css 默认用数字

                list = new com.lowagie.text.List(com.lowagie.text.List.ORDERED, com.lowagie.text.List.ALPHABETICAL);

            } else {
                if (style.contains("lower-alpha")) {
                    //小写字母
                    list = new com.lowagie.text.List(com.lowagie.text.List.ORDERED, com.lowagie.text.List.ALPHABETICAL);
                    list.setNumbered(com.lowagie.text.List.NUMERICAL);
                    list.setLowercase(com.lowagie.text.List.LOWERCASE);

                } else if (style.contains("upper-alpha")) {
                    //大写字母
                    list = new com.lowagie.text.List(com.lowagie.text.List.ORDERED, com.lowagie.text.List.ALPHABETICAL);
                    list.setNumbered(com.lowagie.text.List.NUMERICAL);
                    list.setLowercase(com.lowagie.text.List.UPPERCASE);

                } else if (style.contains("lower-roman")) {
                    //小写罗马 没法做 用小写字母
                    list = new com.lowagie.text.List(com.lowagie.text.List.ORDERED, com.lowagie.text.List.ALPHABETICAL);
                    list.setNumbered(com.lowagie.text.List.NUMERICAL);
                    list.setLowercase(com.lowagie.text.List.LOWERCASE);

                } else if (style.contains("upper-roman")) {
                    //大写罗马 没法做 用大写字母
                    list = new com.lowagie.text.List(com.lowagie.text.List.ORDERED, com.lowagie.text.List.ALPHABETICAL);
                    list.setNumbered(com.lowagie.text.List.NUMERICAL);
                    list.setLowercase(com.lowagie.text.List.UPPERCASE);

                } else {
                    //小写希腊 没法做 用数字
                    list = new com.lowagie.text.List(com.lowagie.text.List.ORDERED, com.lowagie.text.List.ALPHABETICAL);
                }
            }
        } else {
            // 没有style属性 默认用数字
            list = new com.lowagie.text.List(com.lowagie.text.List.ORDERED, com.lowagie.text.List.ALPHABETICAL);
        }

        list.setIndentationLeft(10);
        list.setSymbolIndent(5);

        for (Node childNode : olNode.childNodes()) {
            if (childNode.childNodes().size() > 0)
                list.add(new ListItem(childNode.childNode(0).toString()));
        }

        paragraph.add(list);
    }

    /**
     * 创建无序列表
     *
     * @param paragraph   段落
     * @param ulNode      无序列表节点
     * @param parentAttrs 父节点属性集合
     */
    private void createUl(Paragraph paragraph, Node ulNode, HashMap<String, String> parentAttrs) {
        com.lowagie.text.List list;

        getStyles(ulNode, parentAttrs);

        if (ulNode.hasAttr("style")) {
            String style = ulNode.attr("style");
            if (!style.contains("list-style-type")) {
                //style里面没有列表样式的css 默认用数字
                list = new com.lowagie.text.List(com.lowagie.text.List.UNORDERED);
                list.setPreSymbol("●");
                list.setIndentationLeft(10);
                list.setSymbolIndent(5);

            } else {
                if (style.contains("circle")) {
                    //空心圆
                    list = new com.lowagie.text.List(com.lowagie.text.List.UNORDERED);
                    list.setPreSymbol("○");
                    list.setIndentationLeft(10);
                    list.setSymbolIndent(5);
                    list.setNumbered(com.lowagie.text.List.NUMERICAL);
                    list.setLowercase(com.lowagie.text.List.UPPERCASE);


                } else if (style.contains("square")) {
                    //正方形
                    list = new com.lowagie.text.List(com.lowagie.text.List.UNORDERED);
                    list.setPreSymbol("■");
                    list.setIndentationLeft(10);
                    list.setSymbolIndent(5);
                    list.setNumbered(com.lowagie.text.List.NUMERICAL);
                    list.setLowercase(com.lowagie.text.List.UPPERCASE);


                } else {
                    //默认实心圆
                    list = new com.lowagie.text.List(com.lowagie.text.List.UNORDERED);
                    list.setPreSymbol("●");
                    list.setIndentationLeft(10);
                    list.setSymbolIndent(10);
                }
            }
        } else {
            // 没有style属性 默认用●
            list = new com.lowagie.text.List(com.lowagie.text.List.UNORDERED);
            list.setPreSymbol("●");
            list.setIndentationLeft(10);
            list.setSymbolIndent(10);
        }

        for (Node childNode : ulNode.childNodes()) {
            if (childNode.childNodes().size() > 0)
                list.add(new ListItem(childNode.childNode(0).toString()));
        }
        paragraph.add(list);
    }

    /**
     * 根据html Table节点创建表
     *
     * @param paragraph 段落
     * @param tableNode   表格节点
     * @param parentAttrs 父节点属性集合
     */
    private void createTable(Paragraph paragraph, Node tableNode, HashMap<String, String> parentAttrs) throws DocumentException, IOException {
        getStyles(tableNode, parentAttrs);
        Table table = new Table(getTableColSize(tableNode));

        Node tBody = null;
        for (Node childNode : tableNode.childNodes()) {
            if ("tbody".equals(childNode.nodeName())) {
                tBody = childNode;
                break;
            }
        }
        if (tBody != null && tBody.childNodeSize() > 0) {
            for (Node tr : tBody.childNodes()) {
                if (tr != null && tr.childNodeSize() > 0) {
                    List<Node> tds = tr.childNodes();
                    for (Node td : tds) {
                        if ("td".equals(td.nodeName())) {
                            Phrase cellPhrase = new Phrase();
                            for (Node childNode : td.childNodes()) {
                                if (!"#text".equals(childNode.nodeName())) {
                                    cellPhrase.add(createParagraph(childNode, parentAttrs));
                                } else {
                                    cellPhrase.add(childNode.toString());
                                }
                            }
                            Cell cell = new Cell(cellPhrase);

                            if (td.hasAttr("colspan")) {
                                String colspanStr = td.attr("colspan");
                                if (!"".equals((colspanStr))) {
                                    int colspan = Integer.parseInt(colspanStr);
                                    cell.setColspan(colspan);
                                }
                            }

                            if (td.hasAttr("rowspan")) {
                                String rowspanStr = td.attr("rowspan");
                                if (!"".equals((rowspanStr))) {
                                    int rowspan = Integer.parseInt(rowspanStr);
                                    cell.setRowspan(rowspan);
                                }
                            }
                            table.addCell(cell);
                        }
                    }
                }
            }
        }
        table.setWidth(92);
        table.setAlignment(Table.ALIGN_CENTER);
        paragraph.add(table);
    }

    /**
     * 获取表的列长度
     *
     * @param tableNode 表格节点
     * @return 表格列数量
     */
    private int getTableColSize(Node tableNode) {
        int colSize = 0;

        if (tableNode != null) {
            Node tBody = null;
            for (Node childNode : tableNode.childNodes()) {
                if ("tbody".equals(childNode.nodeName())) {
                    tBody = childNode;
                    break;
                }
            }
            if (tBody != null && tBody.childNodeSize() > 0) {
                Node tr1 = tBody.childNode(1);
                if (tr1 != null && tr1.childNodeSize() > 0) {
                    List<Node> tds = tr1.childNodes();
                    for (Node td : tds) {
                        if ("td".equals(td.nodeName())) {
                            if (td.hasAttr("colspan")) {
                                String colSpanSizeStr = td.attr("colspan");
                                int colSpanSiz = Integer.parseInt(colSpanSizeStr);
                                colSize += colSpanSiz;
                            } else {
                                colSize++;
                            }
                        }

                    }
                }
            }
        }

        return colSize;
    }

    /**
     * 创建图像
     *
     * @param phrase 容器
     * @param node   图像节点
     */
    private void createImg(Phrase phrase, Node node) {
        if (node.hasAttr("src")) {
            String href = node.attr("src");
            if (!"".equals(href)) {
                double width = 50;
                if (node.hasAttr("width")) {
                    String widthStr = node.attr("width");
                    width = Double.parseDouble(widthStr);
                }
                double height = 50;
                if (node.hasAttr("height")) {
                    String heightStr = node.attr("height");
                    height = Double.parseDouble(heightStr);
                }

                try {
                    Image image = Image.getInstance(new URL(href));
                    image.scaleAbsolute((int) width, (int) height);
                    image.setAlignment(Image.LEFT);
                    Chunk c = new Chunk(image, 0, 0);
                    phrase.add(c);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

}
