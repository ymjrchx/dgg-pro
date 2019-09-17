package net.dgg.dqc.backservice.utils;

import net.dgg.dqc.backservice.entity.parse.ErrorRecord;
import net.dgg.dqc.backservice.entity.parse.NewsModel;
import net.dgg.dqc.backservice.entity.parse.QccCompany;
import net.dgg.dqc.backservice.entity.parse.SbDetails;
import net.dgg.dqc.backservice.entity.parse.resume.ResumeModel;
import net.dgg.dqc.backservice.entity.parse.zcgl.ZcglModel;
import net.dgg.dqc.backservice.service.SyncDealDataService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDateTime;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiangsh on 2018/6/8 11:41
 */
public class BaseUtil {
    public static final String TAG = "-";
    public static final String EMPTY = "";
    public static final String UNKNOW = "未知";
    public static final int ZERO = 0;

    public static String oot(String s) {
        if (StringUtils.isNotEmpty(s))
            return Optional.ofNullable(s.trim()).orElse(TAG);
        else return TAG;
    }

    public static String oot(Object o, String url, String target, String prefix, String targetPrefix) {
        if (o != null)
            return Optional.ofNullable(o.toString().replace(url, target).replace(prefix, targetPrefix)).orElse(TAG);
        else return TAG;
    }

    public static String oot(Object o, String url, String target, String url1, String target1, String prefix, String targetPrefix) {
        if (o != null)
            return Optional.ofNullable(o.toString().replace(url, target).replace(url1, target1).replace(prefix, targetPrefix)).orElse(TAG);
        else return TAG;
    }

    public static String oot(Object o) {
        if (o != null && !o.toString().equals(""))
            return Optional.ofNullable(o.toString().trim()).orElse(TAG);
        else return TAG;
    }

    public static String oon(Object o) {
        if (o != null) {
            if (o.toString().equals("")) return "0";
            if (o.toString().contains("-")) return "0";
            return Optional.ofNullable(o.toString().trim().replace("第", "").replace("期", "").replace("增", "")).orElse("0");
        }  else return "0";
    }

    public static String oo8(Object o) {
        if (o != null)
            return Optional.ofNullable(o.toString().trim().replace(" ", "T").replace("Z","")).orElse(TAG);
        else return TAG;
    }

    public static String oot(Object o, int n) {
        if (o != null && n == 1) {
            if (o.toString().equals("-"))
                return EMPTY;
            return Optional.ofNullable(o.toString().trim()).orElse(EMPTY);
        }
        else return EMPTY;
    }

    public static String oot(JSONObject jo, String s) {
        if (jo.has(s))
            return Optional.ofNullable(s).orElse(TAG);
        else return TAG;
    }

    public static String dates(Object date) {
        if (date != null && StringUtils.isNotEmpty(date.toString()) && date.toString().contains(" 00:00:00.0"))
            return date.toString().replace(" 00:00:00.0", "");
        else return null;
    }

    public static int ooz(String s) {
        return Optional.ofNullable(Integer.valueOf(s.trim())).orElse(ZERO);
    }

    public static int ooz(Object o, int n) {
        if (o != null && n == 1) {
            if (o.toString().equals("-"))
                return ZERO;
            return Optional.ofNullable(Integer.valueOf(o.toString().trim())).orElse(ZERO);
        }
        else return ZERO;
    }

    public static int ooz(Object o) {
        if (o != null)
            return Optional.ofNullable(Integer.valueOf(o.toString().trim())).orElse(ZERO);
        else return ZERO;
    }

    public static double dealMoney(Object o) { //返回单位是万元
        String capital = null;
        if (o != null) {
            capital = o.toString();
            if (!hasDigit(capital))         return 0d;
            if (capital.contains("null"))   return 0d;
            if (capital.contains("未公示")) return 0d;
            if (capital.contains("-"))      return 0d;
            if (capital.contains("/"))      return 0d;
        }
        if (StringUtils.isNotEmpty(capital)) {
            String s = capital.replace(" 人民币元", "").replace("元", "").replace("万", "")
                    .replace("人民币", "").replace("()", "").replace(" ", "")
                    .trim();
            if (s.contains(", ")) {
                String[] ss = s.split(", ");
                return Double.valueOf(ss[0]) + Double.valueOf(ss[1]);
            }
            if (s.contains(",")) {
                s = s.replace(",", "");
            }
            if (s.contains("(") || s.contains(")") || s.contains(" ") || s.contains("（") || s.contains("）")) {
                s = s.replace("(", "").replace(")", "").replace(" ", "").replace("（", "").replace("）","");
            }
            if (s.contains("美"))
                return (double)Math.round(Double.valueOf(s.replace("美", "").trim()) * 6.4274);
            else if (s.contains("日"))
                return (double)Math.round(Double.valueOf(s.replace("日", "").trim()) * 0.05918);
            else if (s.contains("瑞士法郎"))
                return Math.round(Double.valueOf(s.replace("瑞士法郎", "").trim())) * 6.7001;
            else if (s.contains("英镑"))
                return Math.round(Double.valueOf(s.replace("英镑", "").trim())) * 8.7436;
            else if (s.contains("新加坡"))
                return Math.round(Double.valueOf(s.replace("新加坡", "").trim())) * 4.9092;
            else if (s.contains("德国马克"))
                return Math.round(Double.valueOf(s.replace("德国马克", "").trim())) * 4.7;
            else if (s.contains("加拿大"))
                return Math.round(Double.valueOf(s.replace("加拿大", "").trim())) * 5.1750;
            else if (s.contains("原洲货币单位"))
                return Math.round(Double.valueOf(s.replace("原洲货币单位", "").trim())) * 0.01254;
            else if (s.contains("阿富汗尼"))
                return Math.round(Double.valueOf(s.replace("阿富汗尼", "").trim())) * 0.0898;
            else if (s.contains("韩国园"))
                return Math.round(Double.valueOf(s.replace("韩国圆", "").trim())) * 0.005723;
            else if (s.contains("澳大利亚"))
                return Math.round(Double.valueOf(s.replace("澳大利亚", "").trim())) * 5.0362;
            else if (s.contains("欧") || s.contains("欧元"))
                return Math.round(Double.valueOf(s.replace("欧", "").replace("元","").trim())) * 7.8097;
            else if (s.contains(" 港（港币）") || s.contains("港") || s.contains("香") || s.contains("币"))
                return Math.round(Double.valueOf(s.replace("港（港币）", "").replace("币", "")
                        .replace("港", "").replace("香", "").trim())) * 0.848;
            else if (s.equals(""))
                return 0d;
            else
                return Optional.ofNullable(Double.valueOf(s)).orElse(0d);
        } else return 0d;
    }

    public static String dealStatus(Object status) {
        if (status != null && StringUtils.isNotEmpty(status.toString())) {
            return status.toString().replace("（在营、开业、在册）", "").replace("（开业）企业", "");
        } else return UNKNOW;
    }

    public static boolean justDate(Object date) {
        if (date != null && !date.toString().equals("-")) {
            return true;
        } else return false;
    }

    public static boolean justDatelen(Object date) {
        if (date == null) return false;
        final String s = date.toString();
        if (!s.equals("-") && !s.contains("/") && !s.contains("无固定期限") && !s.contains("长期") && !s.contains("*") && !s.equals("") && !s.equals("--") && !s.equals("商标申请")) {
            return true;
        } else return false;
    }

    public static String dealDate(String date) {
        if (StringUtils.isNotEmpty(date)) {
            if (isNumeric(date)) return stampToTime(date);
            if (date.contains("/")) {
                date = date.replace("/", "-").trim();
            }
            String d = date.replace("年", "-").replace("月", "-").replace("日", "").replace(",", "");
            if (d.contains(" ")) {
                d = d.split(" ")[0];
            }
            String[] s = d.split("-");
            StringBuffer sb = new StringBuffer();
            if (s[0].equals("")) return EMPTY;
            sb.append(s[0]);
            if (s.length > 1) {
                if (s[1].equals("00")) {
                    s[1] = "01";
                }
                if (s[1].length() == 1) {
                    sb.append("-").append("0").append(s[1]);
                } else sb.append("-").append(s[1]);
            }

            if (s.length > 2)
                if (s[2].length() == 1) {
                    sb.append("-").append("0").append(s[2]);
                } else sb.append("-").append(s[2]);

            return sb.toString();
        } else return EMPTY;
    }

    // 时间戳转换日期
    public static String stampToTime(String stamp) {
        String sd;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sd = sdf.format(new Date(Long.parseLong(stamp)));
        return sd;
    }

    // 判断一个字符串是否含有数字
    public static boolean hasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    //判断是否全为数字
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static String curretTime() {
        return LocalDateTime.now().toString();
    }

    public static String minusDay(int day) {
        return LocalDateTime.now().minusDays(day).toString();
    }

    public static void saveError(SbDetails c, Exception e, String type) {
        SyncDealDataService.saveErrorMsg(new ErrorRecord(c.getRegNo(), c.getName(), e.getMessage(), type));
    }

    public static void saveError(QccCompany c, Exception e, String type) {
        SyncDealDataService.saveErrorMsg(new ErrorRecord(c.getCompanyId(), c.getName(), e.getMessage(), type));
    }

    public static void saveError(ResumeModel c, Exception e, String type) {
        SyncDealDataService.saveErrorMsg(new ErrorRecord(c.getUrl(), c.getName(), e.getMessage(), type));
    }

    public static void saveError(ZcglModel c, Exception e, String type) {
        SyncDealDataService.saveErrorMsg(new ErrorRecord(c.getCompanyId(), c.getQymc(), e.getMessage(), type));
    }

    public static void saveError(NewsModel c, Exception e, String type) {
        SyncDealDataService.saveErrorMsg(new ErrorRecord(c.getTitle(), null, e.getMessage(), type));
    }

    public static void main(String[] args) {
//        System.out.println("()0".replace("()", ""));
//        System.out.println(oot("https://www.qichacha.com/firm_00005eab2daee9f2cc3d68e3617f5b4a.html",
//                "https://www.qichacha.com/", "qcc", ".html", "pre"));
//        String s = LocalDateTime.now().toString();
//        System.out.println(s);
//        System.out.println(justDatelen("2018-08-19T15:24:45"));
//        System.out.println("2018-08-19T15:24:45".equals(""));For input string: "6000000 "
//        System.out.println(dealMoney("6000000 人民币元"));
//        System.out.println(LocalDateTime.now().minusDays(2));
        System.out.println("mm".length());
    }
}
