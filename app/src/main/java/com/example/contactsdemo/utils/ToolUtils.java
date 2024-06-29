package com.example.contactsdemo.utils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author Phobos
 */
public class ToolUtils {


public String getCap(String s){
    HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
    format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
    format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    if (s == null || s.equals("")) {
        return "#";
    }
    char ch = s.charAt(0);

    if (ch >= 'a' && ch <= 'z') {
        return (char) (ch - 'a' + 'A') + "";
    }

    if (ch >= 'A' && ch <= 'Z') {
        return ch + "";
    }
    if (Character.toString(ch).matches("[\\u4E00-\\u9FA5]+")){
        try {
            String[] array = PinyinHelper.toHanyuPinyinStringArray(ch, format);
            if (array != null) {
                return array[0].charAt(0) + "";
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            throw new RuntimeException(e);
        }
    }

    return "#";
}



}
