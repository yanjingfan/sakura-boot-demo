package com.sakura.cloud.jpa.strategy;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;
import org.hibernate.engine.jdbc.internal.Formatter;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @auther YangFan
 * @Date 2021/7/12 22:58
 */
public class P6SpyLogger implements MessageFormattingStrategy {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Formatter formatter;

    static {
        formatter = new BasicFormatterImpl();
    }

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        StringBuilder sb = new StringBuilder();
        String beautifulLog = sb.append(this.format.format(LocalDateTime.now()))
                .append(" | took ")
                .append(elapsed)
                .append("ms | ")
                .append(category)
                .append(" | connection ")
                .append(connectionId)
                .append(formatter.format(sql))
                .append(";").toString();
        return !"".equals(sql.trim()) ? beautifulLog : "";
    }
}
