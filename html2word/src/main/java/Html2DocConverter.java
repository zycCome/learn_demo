/**
 * @author zhuyc
 * @date 2022/03/02 20:47
 **/
import java.io.*;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * 将html文件转换成word文件
 *
 * @author 王文路
 * @date 2015-7-23
 */
public class Html2DocConverter {

    private String inputPath;    // 输入文件路径，以.html结尾
    private String outputPath;    // 输出文件路径，以.doc结尾

    public Html2DocConverter(String inputPath, String outputPath) {
        super();
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    /**
     * 读取html文件到word
     *
     * @param filepath
     *            html文件的路径
     * @return
     * @throws Exception
     */
    public boolean writeWordFile() throws Exception {

        InputStream is = null;
        FileOutputStream fos = null;

        // 1 找不到源文件, 则返回false
        File inputFile = new File(this.inputPath);
//        if (!inputFile.exists()) {
//            return false;
//        }

        File outputFile = new File(this.outputPath);
        // 2 如果目标路径不存在 则新建该路径
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }

        try {
//            String content = "<h1>标题头</h1><h2>第二个标题</h2><a href=\"www.baidu.com\">百度搜索</a>";
            String content = "<p class=\\\"p\\\" style=\\\"text-indent: 0.0000pt; padding: 0pt 0pt 0pt 0pt; layout-grid-mode: char; text-autospace: ideograph-numeric; mso-pagination: widow-orphan; line-height: 28.0000pt; mso-line-height-rule: exactly; margin: 0.0000pt;\\\"><span style=\\\"font-family: 仿宋_GB2312; color: #333333; letter-spacing: 0pt; font-size: 15pt;\\\">&nbsp; &nbsp; &ldquo;企业发展怎样？有哪些困难亟需解决的？&rdquo;</span></p>\\n<p class=\\\"p\\\" style=\\\"text-indent: 0.0000pt; padding: 0pt 0pt 0pt 0pt; layout-grid-mode: char; text-autospace: ideograph-numeric; mso-pagination: widow-orphan; line-height: 28.0000pt; mso-line-height-rule: exactly; margin: 0.0000pt;\\\"><span style=\\\"font-family: 仿宋_GB2312; color: #333333; letter-spacing: 0pt; font-size: 15pt;\\\"><span style=\\\"font-family: 仿宋_GB2312;\\\">　　</span>&ldquo;你们能享受的惠企政策都有兑现落实了吗？&rdquo;</span></p>\\n<p class=\\\"p\\\" style=\\\"text-indent: 0.0000pt; padding: 0pt 0pt 0pt 0pt; layout-grid-mode: char; text-autospace: ideograph-numeric; mso-pagination: widow-orphan; line-height: 28.0000pt; mso-line-height-rule: exactly; margin: 0.0000pt;\\\"><span style=\\\"font-family: 仿宋_GB2312; color: #333333; letter-spacing: 0pt; font-size: 15pt;\\\"><span style=\\\"font-family: 仿宋_GB2312;\\\">　　</span>&ldquo;您对政府职能部门工作人员的工作作风满意吗？有哪些建议？&rdquo;</span></p>\\n<p class=\\\"p\\\" style=\\\"text-indent: 0.0000pt; padding: 0pt 0pt 0pt 0pt; layout-grid-mode: char; text-autospace: ideograph-numeric; mso-pagination: widow-orphan; line-height: 28.0000pt; mso-line-height-rule: exactly; margin: 0.0000pt;\\\"><span style=\\\"font-family: 仿宋_GB2312; color: #333333; letter-spacing: 0pt; font-size: 15pt;\\\"><span style=\\\"font-family: 仿宋_GB2312;\\\">　　近日，德清县纪委县监委各下访组来到企业一线，通过进行</span>&ldquo;清风入企&rdquo;访评调查、实地接访等方式，深入了解企业发展中遇到的重点、难点、堵点问题。&ldquo;行政管理部门承担着政府对企业的发展指导和服务与管理的职能，跟企业有着千丝万缕的联系，防范廉政风险，构建&lsquo;亲&rsquo;&lsquo;清&rsquo;政商关系至关重要。&rdquo;据县纪委县监委相关负责人介绍，致力于靠前监督，县纪委县监委统筹派驻组、委机关各室力量，成立19支下访组，与镇（街道）实行对点联系，在建立每月进村走访调研机制的基础上，下访组联合镇（街道）纪（工）委的力量，对负责辖区内的企业进行延伸走访，重点做好&ldquo;亲&rdquo;&ldquo;清&rdquo;新型政商关系负面清单和正面清单宣传；&ldquo;清风入企&rdquo;访评调查，听取企业关于营商环境的意见建议；收集干部在服务企业中存在的不作为、乱作为、慢作为等形式主义官僚主义问题；了解惠企政策有无宣传到位，企业有无实际困难和需求等。</span></p>\\n<p class=\\\"p\\\" style=\\\"text-indent: 0.0000pt; padding: 0pt 0pt 0pt 0pt; layout-grid-mode: char; text-autospace: ideograph-numeric; mso-pagination: widow-orphan; line-height: 28.0000pt; mso-line-height-rule: exactly; margin: 0.0000pt;\\\"><span style=\\\"font-family: 仿宋_GB2312; color: #333333; letter-spacing: 0pt; font-size: 15pt;\\\"><span style=\\\"font-family: 仿宋_GB2312;\\\">　　</span>&ldquo;我们坚持对存在的问题即知即改，以扎实的作风、高效的服务护航企业发展。&rdquo;联系该县钟管镇的下访组负责人表示。不久前，该组与钟管镇纪委在走访中了解到该镇一家规上企业还未申领技改补贴，及时告知政策内容，并与镇上相关办公室对接，帮助该公司顺利办理了补贴申请手续。事后下访组督促该镇做好惠企政策多轮式全覆盖宣传，确保企业实实在在享受政策红利。</span></p>\\n<p class=\\\"p\\\" style=\\\"text-indent: 0.0000pt; padding: 0pt 0pt 0pt 0pt; layout-grid-mode: char; text-autospace: ideograph-numeric; mso-pagination: widow-orphan; line-height: 28.0000pt; mso-line-height-rule: exactly; margin: 0.0000pt;\\\"><span style=\\\"font-family: 仿宋_GB2312; color: #333333; letter-spacing: 0pt; font-size: 15pt;\\\"><span style=\\\"font-family: 仿宋_GB2312;\\\">　　针对收集到的涉企廉情信息，该县纪委县监委及时剖析研判、挂号督办，立足</span>&ldquo;点上问题、面上治理&rdquo;落实整改，并适时对整改情况进行&ldquo;回头看&rdquo;&ldquo;综合看&rdquo;，形成高质高效的企业营商环境诉求监督闭环。如了解到企业关于改善招商环境的诉求后，该县纪委县监委督促县商务局等职能部门制定招商行为规范，进一步建立健全招商引资廉洁承诺、招商引资信息公开、招商引资项目集体决策、领导干部联系重大招商企业等制度，为推进招商引资营造良好的内部环境。</span></p>\\n<p class=\\\"p\\\" style=\\\"text-indent: 0.0000pt; padding: 0pt 0pt 0pt 0pt; layout-grid-mode: char; text-autospace: ideograph-numeric; mso-pagination: widow-orphan; line-height: 28.0000pt; mso-line-height-rule: exactly; margin: 0.0000pt;\\\"><span style=\\\"font-family: 仿宋_GB2312; color: #333333; letter-spacing: 0pt; font-size: 15pt;\\\"><span style=\\\"font-family: 仿宋_GB2312;\\\">　　钟管镇纪委创新建立驻企监察信息员制度，从当地试点的</span>29家规上企业的负责人、中层管理人员、党务工作者、财务人员和法务人员中选聘企业监察信息员，信息员们利用&ldquo;1对1&rdquo;驻点优势，积极发挥参谋咨询、桥梁纽带、政策宣传、&ldquo;一线&rdquo;信息收集等作用；新市镇纪委组织&ldquo;清风跑小二&rdquo;跑企业现场、跑项目现场，对发现问题进行专题通报并督促整改&hellip;&hellip;入企走访行动开展以来，各镇（街道）纪工委立足实际，前移监督关口，架设监督&ldquo;探头&rdquo;，主动作为，切实帮助企业纾困解难。</span></p>\\n<p class=\\\"p\\\" style=\\\"text-indent: 0.0000pt; padding: 0pt 0pt 0pt 0pt; layout-grid-mode: char; text-autospace: ideograph-numeric; mso-pagination: widow-orphan; line-height: 28.0000pt; mso-line-height-rule: exactly; margin: 0.0000pt;\\\"><span style=\\\"font-family: 仿宋_GB2312; color: #333333; letter-spacing: 0pt; font-size: 15pt;\\\"><span style=\\\"font-family: 仿宋_GB2312;\\\">　　</span>&ldquo;营商环境就是发展环境，我们将坚决立足监督第一职责，建立健全与纾困惠企工作体系、政策体系、落实体系相匹配的监督机制，办实事，解企忧，切实以&lsquo;硬监督&rsquo;推动营商环境再优化、民生利益再大化，助力经济社会高质量发展。&rdquo;德清县纪委县监委相关负责人表示。</span></p>\\n<p class=\\\"p\\\" style=\\\"text-indent: 0.0000pt; padding: 0pt 0pt 0pt 0pt; layout-grid-mode: char; text-autospace: ideograph-numeric; mso-pagination: widow-orphan; line-height: 28.0000pt; mso-line-height-rule: exactly; margin: 0.0000pt;\\\"><span style=\\\"font-family: 仿宋_GB2312; color: #333333; letter-spacing: 0pt; font-size: 15pt;\\\"><img class=\\\"wscnph\\\" src=\\\"http://118.195.172.125:18080/zgDSP/common/file/upload/20210702/60debf4ce4b069ffc9135e87.png\\\" width=\\\"200\\\" /></span></p>";

            StringBuffer sbf = new StringBuffer();
            sbf.append("<html><body>");
            sbf.append(content);
            sbf.append("</body></html");
            // 3 将html文件内容写入doc文件
//            is = new FileInputStream(inputFile);
            is = new ByteArrayInputStream(sbf.toString().getBytes("GBK"));
            POIFSFileSystem poifs = new POIFSFileSystem();
            DirectoryEntry directory = poifs.getRoot();
            directory.createDocument(
                    "WordDocument", is);

            fos = new FileOutputStream(this.outputPath);
            poifs.writeFilesystem(fos);

            System.out.println("转换word文件完成!");

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null)
                fos.close();
            if (is != null)
                is.close();
        }

        return false;
    }
    public static void main(String[] args) throws Exception {

        new Html2DocConverter("G:/123.html" , "E:/temp6.doc").writeWordFile();
    }

    public int testSlot(int c) {

        int a = 1;
        if(c == 1) {
            int b1 = 2;
            int b2 = 2;
            System.out.println(b1+b2);
        } else {
            int c1 = 2;
            System.out.println(c1);
        }


        if(c == 1) {
            int b1 = 2;
            int b2 = 2;
            System.out.println(b1+b2);
        } else {
            int c1 = 2;
            System.out.println(c1);
        }
        int d1 = 100;
        return d1;
    }
}
