package net.dgg.tmd.foundation.platform.stack.ssh;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created by 李程 on 2018/11/9.
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SecureShellService {

    private String rootPath;

    private String host;

    private Integer port;

    private String user;

    private String password;

    private Session session = null;

    @SneakyThrows
    public Session getSession() {
        if (session == null || !session.isConnected()) {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
        }
        return session;
    }

    @SneakyThrows
    public void upload(InputStream inputStream, String path) {
        if (path.indexOf("/") > -1 && path.lastIndexOf("/") > 0) {
            String dir = path.substring(0, path.lastIndexOf("/"));
            String fullPath = rootPath.concat("/").concat(dir).replaceAll("/{2,}", "/");
            /**
             String[] paths = dir.split("/");
             String currentPath = rootPath;
             ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
             for (String p : paths) {
             if (org.apache.commons.lang3.StringUtils.isNotEmpty(p)) {
             currentPath = currentPath.concat("/").concat(p).replaceAll("/{2,}", "/");
             sftp.mkdir(currentPath);
             }
             }
             sftp.quit();
             */
            ChannelExec shell = (ChannelExec) getSession().openChannel("exec");
            String command = "mkdir -p ".concat(fullPath);
            shell.setCommand(command);
            shell.setInputStream(null);
            ByteArrayOutputStream err = new ByteArrayOutputStream();
            shell.setErrStream(err);
            InputStream in = shell.getInputStream();
            shell.connect();
            shell.start();
            shell.disconnect();
        }
        ChannelSftp sftp = (ChannelSftp) getSession().openChannel("sftp");
        sftp.connect();
        try {
            String uploadPath = rootPath.concat("/").concat(path).replaceAll("/{2,}", "/");
            sftp.put(inputStream, uploadPath);
        } finally {
            sftp.quit();
        }
    }

    @SneakyThrows
    public List<String> collect(String root) {
        String seed = rootPath.concat("/").concat(root).replaceAll("/{2,}", "/");
        Session session = getSession();
        ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
        sftp.connect();
        List<String> files = new ArrayList<>();
        LinkedList<String> waitForEach = new LinkedList<>();
        waitForEach.add(seed);
        while (waitForEach.size() > 0) {
            String current = waitForEach.removeFirst();
            Vector<ChannelSftp.LsEntry> ls = sftp.ls(current);
            for (ChannelSftp.LsEntry entry : ls) {
                if (!entry.getFilename().matches("\\.+")) {
                    if (entry.getAttrs().isDir()) {
                        String fileName = current.concat("/").concat(entry.getFilename()).replaceAll("/{2,}", "/");
                        waitForEach.add(fileName);
                    } else {
                        String fileName = current.concat("/").concat(entry.getFilename()).replaceAll("/{2,}", "/");
                        files.add(fileName.substring(rootPath.length()));
                    }
                }
            }
        }
        sftp.disconnect();
        return files;
    }

    public void close() {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private SecureShellService secureShellService;

        public Builder() {
            this.secureShellService = new SecureShellService();
        }

        public SecureShellService build() {
            return this.secureShellService;
        }

        public Builder rootPath(String rootPath) {
            this.secureShellService.setRootPath(rootPath);
            return this;
        }

        public Builder host(String host) {
            this.secureShellService.setHost(host);
            return this;
        }

        public Builder port(Integer port) {
            this.secureShellService.setPort(port);
            return this;
        }

        public Builder user(String user) {
            this.secureShellService.setUser(user);
            return this;
        }

        public Builder password(String password) {
            this.secureShellService.setPassword(password);
            return this;
        }
    }
}
