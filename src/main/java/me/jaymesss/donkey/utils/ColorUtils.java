package me.jaymesss.donkey.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ColorUtils {
    public String translateFromString(String text) {
        return StringEscapeUtils.unescapeJava(ChatColor.translateAlternateColorCodes('&', text));
    }

    public List<String> translateFromArray(List<String> text) {
        List<String> messages = new ArrayList<String>();
        for (String string : text) {
            messages.add(this.translateFromString(string));
        }
        return messages;
    }
}
