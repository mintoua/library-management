package com.syniiq.library_management.utils;

import com.github.slugify.Slugify;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@AllArgsConstructor
public class Tool {


    public static String cleanIt(String arg0) {

        return Jsoup.clean(StringEscapeUtils.escapeHtml4(StringEscapeUtils.escapeHtml4(arg0)), Safelist.basic());
    }



}
