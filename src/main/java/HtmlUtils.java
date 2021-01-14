public class HtmlUtils {
    public static String getHtml() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "aaaaaaaaa" +
                "    <p>锚点测试：<a id=\"a10\"></a><a id=\"A15\"></a>bbbbbbb</p>\n" +
                "    <p>&nbsp;</p>\n" +
                "    <p>\n" +
                "        <span style=\"color: #f1c40f; background-color: #bfedd2;\">\n" +
                "            88888888\n" +
                "            <span style=\"background-color: #f8cac6; color: #b96ad9;\">\n" +
                "                888\n" +
                "                <strong>\n" +
                "                    <em>\n" +
                "                        88\n" +
                "                    </em>\n" +
                "                </strong>\n" +
                "                88\n" +
                "                <strong>\n" +
                "                    <em>\n" +
                "                        88\n" +
                "                    </em>\n" +
                "                </strong>\n" +
                "                88\n" +
                "            </span>\n" +
                "            8888\n" +
                "        </span>\n" +
                "    </p>\n" +
                "    <p>&nbsp;</p>\n" +
                "    <p>1，测试加粗--------<strong>加粗</strong></p>\n" +
                "    <p>2，测试斜体--------<em>斜体</em></p>\n" +
                "    <p>3，测试中划线-----<span style=\"text-decoration: line-through;\">删除线</span></p>\n" +
                "    <p><span style=\"color: #000000;\">4，测试字体颜色--<span style=\"color: #000000;\">-</span><span style=\"color: #e03e2d;\">字体</span></span></p>\n" +
                "    <p><span style=\"color: #000000;\">5，测试背景色-----<span style=\"background-color: #2dc26b;\">背景色</span></span></p>\n" +
                "    <p><span style=\"color: #000000;\">6，测试链接-------<a title=\"百度\" href=\"https://www.baidu.com/\">https://www.baidu.com/<img src=\"http://192.168.0.73:28080/platform/doc/2020/12/9/222.jpg\" alt=\"\" width=\"171\" height=\"120\" /></a></span></p>\n" +
                "    <a title=\"百度\" href=\"https://www.baidu.com/\">https://www.baidu.com/<img src=\"http://192.168.0.73:28080/platform/doc/2021/1/14/表情包.png\" alt=\"\" width=\"171\" height=\"120\" /></a>" +
                "    <p><span style=\"color: #000000;\">7，测试图片-------</span></p>\n" +
                "    <p><span style=\"color: #000000;\">8，测试居左显示-</span></p>\n" +
                "    <p style=\"text-align: left;\"><span style=\"color: #000000;\">居左</span></p>\n" +
                "    <p style=\"text-align: left;\"><span style=\"color: #000000;\">9，测试居中显示</span></p>\n" +
                "    <p style=\"text-align: center;\">居中</p>\n" +
                "    <p>10，测试居右显示-</p>\n" +
                "    <p style=\"text-align: right;\">居右</p>\n" +
                "    <p style=\"text-align: justify;\">11，测试两端对齐-</p>\n" +
                "    <p class=\"wa-musicsong-lyric-line\" style=\"text-align: justify;\">\n" +
                "        我自朝来我随暮去我还在追着挽过流云留过飞花你可记得我山川万古作伴 一晌春秋而过三两入夜后\n" +
                "        一梦不舍我自朝来我随暮去我还在追着挽过流云留过飞花你可记得我山川万古作伴 一晌春秋而过三两入夜后\n" +
                "    </p>\n" +
                "    <p class=\"wa-musicsong-lyric-line\" style=\"text-align: justify;\">12，测试表格--</p>\n" +
                "    <table style=\"border-collapse: collapse; width: 100%; height: 63px;\" border=\"1\">\n" +
                "        <tbody>\n" +
                "            <tr style=\"height: 21px;\">\n" +
                "                <td style=\"width: 16.3074%; height: 21px;\">id</td>\n" +
                "                <td style=\"width: 16.3074%; height: 21px;\">name</td>\n" +
                "                <td style=\"width: 16.4011%; height: 21px;\">age</td>\n" +
                "                <td style=\"width: 16.4011%; height: 21px;\">sex</td>\n" +
                "                <td style=\"width: 16.4011%; height: 21px;\">store</td>\n" +
                "                <td style=\"width: 16.4011%; height: 21px;\">weight</td>\n" +
                "                <td style=\"width: 16.4011%; height: 21px;\" rowspan=\"3\">test</td>\n" +
                "            </tr>\n" +
                "            <tr style=\"height: 21px;\">\n" +
                "                <td style=\"width: 16.3074%; height: 21px;\">1</td>\n" +
                "                <td style=\"width: 16.3074%; height: 21px;\">张三</td>\n" +
                "                <td style=\"width: 16.4011%; height: 21px;\">15</td>\n" +
                "                <td style=\"width: 16.4011%; height: 21px;\">男</td>\n" +
                "                <td style=\"width: 16.4011%; height: 21px;\" colspan=\"2\">80</td>\n" +
                "            </tr>\n" +
                "            <tr style=\"height: 21px;\">\n" +
                "                <td style=\"width: 16.3074%; height: 21px;\">2</td>\n" +
                "                <td style=\"width: 16.3074%; height: 21px;\">李思思</td>\n" +
                "                <td style=\"width: 16.4011%; height: 21px;\">18</td>\n" +
                "                <td style=\"width: 16.4011%; height: 21px;\">女</td>\n" +
                "                <td style=\"width: 16.4011%; height: 21px;\">100</td>\n" +
                "                <td style=\"width: 16.4011%; height: 21px;\">100</td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "    <p>13，测试水平分割线---</p>\n" +
                "    <hr />\n" +
                "    <p>14，测试分页符---</p>\n" +
                "    <p>\n" +
                "        <!-- pagebreak -->\n" +
                "    </p>\n" +
                "    <p>15，测试不间断空格</p>\n" +
                "    <p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;\n" +
                "        &nbsp; &nbsp; &nbsp;&nbsp;</p>\n" +
                "    <p>16，测试列表--</p>\n" +
                "    <p>&nbsp; 16.1，有序列表</p>\n" +
                "    <ol style=\"list-style-type: upper-alpha;\">\n" +
                "        <li>张三</li>\n" +
                "        <li>李四</li>\n" +
                "        <li>王五</li>\n" +
                "        <li>赵六</li>\n" +
                "    </ol>\n" +
                "    <p>&nbsp; 16.2，无序列表</p>\n" +
                "    <ul style=\"list-style-type: circle;\">\n" +
                "        <li>香蕉</li>\n" +
                "        <li>苹果</li>\n" +
                "        <li>橘子</li>\n" +
                "    </ul>\n" +
                "    <p>17，测试缩进---</p>\n" +
                "    <p style=\"padding-left: 40px;\">我自朝来我随暮去我还在追着挽过</p>\n" +
                "    <p style=\"padding-left: 120px;\">我自朝来我随暮去我还在追着挽过</p>\n" +
                "    <p style=\"padding-left: 80px;\">我自朝来我随暮去我还在追着挽过</p>\n" +
                "    <p>18，测试清除样式---加粗</p>\n" +
                "    <p>19，测试下划线--<span style=\"text-decoration: underline;\">下划线</span></p>\n" +
                "    <p>20，测试字号----<span style=\"font-size: 36pt;\">字号36px</span></p>\n" +
                "    <p><span style=\"font-size: 12pt;\">21，测试字体---<span style=\"font-family: 'arial black', sans-serif;\">微软雅黑、<span\n" +
                "                    style=\"font-family: 'comic sans ms', sans-serif;\">comic-sanc-MS</span></span></span></p>\n" +
                "    <p>&nbsp;</p>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }

}
