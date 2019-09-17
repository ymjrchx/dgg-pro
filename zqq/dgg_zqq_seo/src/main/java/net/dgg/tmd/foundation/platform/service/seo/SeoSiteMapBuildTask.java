package net.dgg.tmd.foundation.platform.service.seo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.dgg.tmd.foundation.platform.stack.ssh.SecureShellService;
import net.dgg.tmd.foundation.platform.utils.Toolkit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 李程 on 2018/11/12.
 * 此类定义了SEO生成网站地图任务，每天晚上的時候觸發，通過掃描靜態文件生成SiteMap，注意是分割的SiteMap
 */
@Slf4j
public class SeoSiteMapBuildTask {

    @Autowired
    SecureShellService secureShellService;

    Gson gson;

    JsonParser jsonParser;

    @Value("${seo.mount.http.path}")
    String mountHttpPath;

    @Value("${seo.sitemap.build.host}")
    String sitemapHost;

    @Value("${seo.sitemap.build.port}")
    Integer sitemapPort;

    @Value("${seo.sitemap.build.user}")
    String sitemapUser;

    @Value("${seo.sitemap.build.passwd}")
    String sitemapPasswd;

    @Value("${seo.sitemap.build.root}")
    String sitemapRoot;

    @Value("${seo.sitemap.build.mount.path}")
    String siteMapMountPath;

    @Value("${seo.robots.host}")
    String robotsHost;

    @Value("${seo.robots.port}")
    Integer robotsPort;

    @Value("${seo.robots.user}")
    String robotsUser;

    @Value("${seo.robots.passwd}")
    String robotsPasswd;

    @Value("${seo.robots.root}")
    String robotsRoot;

    @Value("${seo.robots.mount.path}")
    String robotsMount;

    @Value("${debug}")
    Boolean debug;

    @Value("${seo.sitemap.build.segment}")
    Integer segment;

    SecureShellService siteMapShell = null;

    SecureShellService rootShell = null;

    public SeoSiteMapBuildTask() {
    }

    @PostConstruct
    public void init() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        gsonBuilder.setDateFormat("yyyy-MM-dd");
        gson = gsonBuilder.create();
        jsonParser = new JsonParser();
    }

    @SneakyThrows
    public void generateSiteMap() {
        try {
            Long current = System.currentTimeMillis();
            String year = Toolkit.DateUtils.format(current, "yyyy");
            String yearMonth = Toolkit.DateUtils.format(current, "yyyy-MM");
            String root = "/" + year + "/" + yearMonth + "/";
            List<String> files = secureShellService.collect(root);

            files = files.parallelStream().filter(file -> file.endsWith(".html")).filter(file -> file.startsWith(root)).collect(Collectors.toList());
            List<List<String>> urlSets = new ArrayList<>();
            List<String> currentList = new ArrayList<>();
            for (int i = 0; i < files.size(); i++) {
                if (i > 0 && i % segment == 0) {
                    urlSets.add(currentList);
                    currentList = new ArrayList<>();
                }
                currentList.add(files.get(i));
            }
            urlSets.add(currentList);

            List<String> urlSetContents = new ArrayList<>();
            for (int i = 0; i < urlSets.size(); i++) {
                currentList = urlSets.get(i);
                if (currentList != null && !currentList.isEmpty()) {
                    Document urlSet = buildUrlSet(currentList);
                    String urlSetContent = Toolkit.DomHelper.doc2str(urlSet);
                    urlSetContents.add(urlSetContent);
                }
            }

            Document siteMap = buildSiteMap(urlSetContents);
            SecureShellService shell;
            if (rootShell != null) {
                shell = rootShell;
            } else {
                shell = SecureShellService.builder().host(robotsHost).port(robotsPort).user(robotsUser).password(robotsPasswd).rootPath(robotsRoot).build();
                rootShell = shell;
            }
            String siteMapContent = Toolkit.DomHelper.doc2str(siteMap);
            shell.upload(new ByteArrayInputStream(siteMapContent.getBytes()), "/sitemap.xml");

            //生成robots.txt并加入SiteMap位置
            @Cleanup ByteArrayOutputStream robotOutputStream = new ByteArrayOutputStream();
            @Cleanup PrintWriter pw = new PrintWriter(robotOutputStream);
            pw.println("User-Agent:  *");
            pw.println("Allow:  /");
            pw.println();
            pw.print("Sitemap: ");
            pw.print(robotsMount);
            pw.flush();
            shell.upload(new ByteArrayInputStream(robotOutputStream.toByteArray()), "/robots.txt");
        } catch (Exception e) {
            log.info("发生错误：{}", e.getMessage());
        }
    }

    @SneakyThrows
    Document buildSiteMap(List<String> urlSetContents) {
        SecureShellService shell;
        if (siteMapShell != null) {
            shell = siteMapShell;
        } else {
            shell = SecureShellService.builder().host(sitemapHost).port(sitemapPort).user(sitemapUser).password(sitemapPasswd).rootPath(sitemapRoot).build();
            siteMapShell = shell;
        }
        String ymd = Toolkit.DateUtils.format(System.currentTimeMillis(), "yyyy-MM-dd");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        document.setXmlVersion("1.0");

        Element siteMapIndex = document.createElement("sitemapindex");
        document.appendChild(siteMapIndex);

        for (int i = 0; i < urlSetContents.size(); i++) {
            String fileName = "/" + i + ".xml";
            shell.upload(new ByteArrayInputStream(urlSetContents.get(i).getBytes()), fileName);

            Element siteMap = document.createElement("sitemap");
            siteMapIndex.appendChild(siteMap);

            Element loc = document.createElement("loc");
            siteMap.appendChild(loc);
            loc.appendChild(document.createTextNode(siteMapMountPath.concat(fileName)));

            Element lastMod = document.createElement("lastmod");
            siteMap.appendChild(lastMod);
            lastMod.appendChild(document.createTextNode(ymd));
        }
        return document;
    }

    @SneakyThrows
    Document buildUrlSet(List<String> list) {
        String ymd = Toolkit.DateUtils.format(System.currentTimeMillis(), "yyyy-MM-dd");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        document.setXmlVersion("1.0");
        Element urlSet = document.createElement("urlset");
        document.appendChild(urlSet);
        for (int i = 0; i < list.size(); i++) {

            Element url = document.createElement("url");
            urlSet.appendChild(url);

            Element loc = document.createElement("loc");
            url.appendChild(loc);
            loc.appendChild(document.createTextNode(mountHttpPath + list.get(i)));

            Element lastMod = document.createElement("lastmod");
            url.appendChild(lastMod);
            lastMod.appendChild(document.createTextNode(ymd));

            Element changeFreq = document.createElement("changefreq");
            url.appendChild(changeFreq);
            changeFreq.appendChild(document.createTextNode("always"));

            Element priority = document.createElement("priority");
            url.appendChild(priority);
            priority.appendChild(document.createTextNode("1.0"));

        }
        return document;
    }

}
