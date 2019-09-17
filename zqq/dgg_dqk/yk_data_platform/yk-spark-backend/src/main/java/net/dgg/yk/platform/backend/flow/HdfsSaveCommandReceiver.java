package net.dgg.yk.platform.backend.flow;

import net.dgg.yk.platform.backend.command.Command;
import net.dgg.yk.platform.backend.command.CommandReceiver;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HdfsSaveCommandReceiver implements CommandReceiver {

    @Override
    public String getSupport() {
        return "save";
    }

    @Override
    public <T> T execute(Command command) {
        try {
            Configuration configuration = new Configuration();
            FileSystem fs = FileSystem.get(configuration);
            String database = command.getParameter("database");
            String collection = command.getParameter("collection");
            String content = command.getParameter("content");
            String root = command.getEnvParameter("hdfs.root");
            String jsonOrigin = root + database + "/" + collection + "/" + "origin";
            if (!fs.exists(new Path(jsonOrigin))) {
                fs.mkdirs(new Path(jsonOrigin));
            }
            int partIndex = 0;
            boolean appendMode = true;
            boolean loop = true;
            String partFile = null;
            while (loop) {
                partFile = jsonOrigin + "/" + partIndex++;
                Path partPath = new Path(partFile);
                if (fs.exists(partPath)) {
                    long length = fs.getContentSummary(partPath).getLength();
                    if (length < 1000L * 1000L * 20L) {
                        appendMode = true;
                        loop = false;
                    }
                } else {
                    appendMode = false;
                    loop = false;
                }
            }
            FSDataOutputStream out;
            if (appendMode) {
                out = fs.append(new Path(partFile), 1024);
            } else {
                out = fs.create(new Path(partFile));
            }
            out.write(("\n" + content).getBytes());
            out.flush();
            out.close();
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
