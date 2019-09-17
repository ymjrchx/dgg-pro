package net.dgg.gspt.dqc.utils;/**
 * Created by Administrator on 2018/5/2.
 */


import net.dgg.gspt.dqc.entity.businessPushRecord.BusinessPushRecord;
import net.dgg.gspt.dqc.entity.operationRecord.OperationRecord;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

/**
 * @author 刘阳
 * @ClassName <MapperUtils>
 * @despriction Dao 的生产
 * @create 2018/05/02 14:27
 **/
public class MapperXmlUtils {
    private String sj = "    "; //缩进
    private Class<?> entity = null;     // 实体
    private String tableName = null;
    private String alias = "t"; // 别名
    private String idName = "id"; // id
    private String daoPackage = "dao";
    private String DaoSuffix = "Dao";

    public static void main(String[] a) {

        new MapperXmlUtils().createMapper(BusinessPushRecord.class, "gspt_dqc_business_push_record");
    }


    /**
     * @param entity            实体
     * @param tableNameAndAlias [0]表名  [1]别名 [2] idName
     */
    public void createMapper(Class<?> entity, String... tableNameAndAlias) {
        this.entity = entity;
        if (this.entity == null) {
            return;
        }
        if (tableNameAndAlias.length >= 1) {
            this.tableName = tableNameAndAlias[0];
        }
        if (tableNameAndAlias.length >= 2) {
            this.alias = tableNameAndAlias[1];
        }
        if (tableNameAndAlias.length >= 3) {
            this.idName = tableNameAndAlias[2];
        }


        String daoPath = this.getDaoPath();

        String daoRootPath = this.getDaoRootPath();
        String mapperPath = daoRootPath.concat("mapper\\");

        String daoFileName = this.getDaoSimpleName().concat(".java");
        String mapperName = this.entity.getSimpleName().concat(this.DaoSuffix).concat("Mapper.xml");

        System.out.println(daoPath + daoFileName);
        System.out.println(mapperPath + mapperName);
        this.createDao(daoPath, daoFileName);
        this.createXml(mapperPath, mapperName);
        System.out.println("创建完成！");
    }

    private void createXml(String xmlPath, String fileName) {
        this.createDirectory(xmlPath);

        File daoFile = new File(xmlPath.concat(fileName));
        /*if (daoFile.exists()) {
            System.err.println(String.format("%s文件已存在！", xmlPath));
            return;
        }*/
        try {
            daoFile.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(daoFile));
            bw.write(this.getXmlFileText());
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //覆盖文件内容
        if (daoFile.exists()) {
            System.out.println(String.format("%s文件已存在！覆盖中。。。", daoFile));
            try {
                FileOutputStream fos = new FileOutputStream(daoFile);
                fos.write(this.getXmlFileText().getBytes());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                daoFile.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(daoFile));
                bw.write(this.getXmlFileText());
                bw.flush();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getXmlFileText() {
        String sj = "";
        StringBuilder sb = new StringBuilder(500);
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
        sb.append("<mapper namespace=\"" + this.getDaoPackage().concat("." + this.getDaoSimpleName()) + "\">\n");

        sb.append(sj).append("<insert id=\"save\" parameterType=\"" + this.entity.getName() + "\" >\n")
                .append(sj).append(sj).append(new SqlUtils(this.sj).createInsertSql(this.entity, this.tableName)).append("\n")
                .append("</insert>\n\n");

        sb.append(sj).append("<update id=\"update\" parameterType=\"" + this.entity.getName() + "\">\n")
                .append(sj).append(sj).append(new SqlUtils(this.sj).createUpdateSql(this.entity, this.tableName, this.idName)).append("\n")
                .append("</update>\n\n");

        sb.append(sj).append("<select id=\"findById\" parameterType=\"" + this.getIdType() + "\" resultType=\"" + this.entity.getName() + "\">\n")
                .append(new SqlUtils(this.sj).createFindByIdSql(this.entity, this.tableName, this.alias, this.idName)).append("\n")
                .append("</select>\n\n");

        sb.append(sj).append("<select id=\"query\" resultType=\"" + this.entity.getName() + "\">\n")
                .append(new SqlUtils(this.sj).createSelectSql(this.entity, this.tableName, this.alias)).append("\n")
                .append("</select>\n\n");

        sb.append(sj).append("<select id=\"queryMap\" resultType=\"java.util.Map\">\n")
                .append(new SqlUtils(this.sj).createSelectSql(this.entity, this.tableName, this.alias)).append("\n")
                .append("</select>\n\n");

        sb.append(sj).append("<select id=\"count\" resultType=\"java.lang.Integer\">\n")
                .append(new SqlUtils(this.sj).createCountSql(this.entity, this.tableName, this.alias)).append("\n")
                .append("</select>\n\n");

        sb.append(sj).append("<delete id=\"deleteById\" parameterType=\"" + this.getIdType() + "\">\n")
                .append(new SqlUtils(this.sj).createDeleteByIdSql(this.entity, this.tableName, this.idName)).append("\n")
                .append("</delete>\n\n");


        sb.append(sj).append(new SqlUtils(this.sj).createWhereTemplate(this.entity, this.alias)).append("\n");

        sb.append("</mapper>");
        return sb.substring(0);
    }

    private void createDao(String daoFilePath, String fileName) {
        this.createDirectory(daoFilePath);

        File daoFile = new File(daoFilePath.concat(fileName));
        /*if (daoFile.exists()) {
            System.err.println(String.format("%s文件已存在！", daoFilePath));
            return;
        }*/
        try {
            daoFile.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(daoFile));
            bw.write(this.getDaoFileText());
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //覆盖文件内容
        if (daoFile.exists()) {
            System.out.println(String.format("%s文件已存在！覆盖中。。。", daoFilePath));
            try {
                FileOutputStream fos = new FileOutputStream(daoFile);
                fos.write(this.getDaoFileText().getBytes());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                daoFile.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(daoFile));
                bw.write(this.getDaoFileText());
                bw.flush();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String getDaoFileText() {
        String sj = "    ";
        StringBuilder sb = new StringBuilder(500)
                .append("package ").append(this.getDaoPackage()).append(";\n\n")
                .append("import ").append(this.entity.getName()).append(";\n")
                .append("import org.apache.ibatis.annotations.Param;\n")
                .append("import org.springframework.stereotype.Repository;\n\n")
                .append("import java.util.List;\n")
                .append("import java.util.Map;\n\n")
                .append("/**\n")
                .append(" * @author \n")
                .append(" * @ClassName <" + this.getDaoSimpleName() + ">\n")
                .append(" * @despriction \n")
                .append(" * @create " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()) + "+\n")
                .append(" **/ \n")
                .append("@Repository\n")
                .append("public interface ").append(this.getDaoSimpleName()).append(" {\n");

        sb.append(sj).append("void save(").append(this.entity.getSimpleName()).append(" ").append(this.firstLower(this.entity.getSimpleName())).append(");\n\n");
        sb.append(sj).append("void update(").append(this.entity.getSimpleName()).append(" ").append(this.firstLower(this.entity.getSimpleName())).append(");\n\n");
        sb.append(sj).append(this.entity.getSimpleName()).append(" findById(@Param(\"").append(idName).append("\") ").append(this.getIdType()).append(" ").append(idName).append(");").append("\n\n");
        sb.append(sj).append("List<").append(this.entity.getSimpleName()).append("> query(Map map);\n\n");
        sb.append(sj).append("List<Map> queryMap(Map map);\n\n");
        sb.append(sj).append("Integer").append(" count(Map map);\n\n");
        sb.append(sj).append("void deleteById(@Param(\"").append(idName).append("\") ").append(this.getIdType()).append(" ").append(idName).append(");").append("\n\n");
        sb.append("}");

        return sb.substring(0);
    }


    private String getDaoSimpleName() {
        return this.entity.getSimpleName().concat(this.DaoSuffix);
    }

    private String getDaoPackage() {
        String packagePath = this.entity.getPackage().getName().replace("entity", this.daoPackage);
        return packagePath;
    }

    private String getDaoRootPackage() {
        String packagePath = this.entity.getPackage().getName();
        packagePath = packagePath.substring(0, packagePath.indexOf(".entity"));
        packagePath += "." + this.daoPackage;
        return packagePath;
    }

    private String getDaoRootPath() {
        String projectPath = new File("").getAbsolutePath();
        return projectPath + "\\src\\main\\java\\" + this.getDaoRootPackage().replace(".", "\\").concat("\\");
    }

    private String getDaoPath() {
        String projectPath = new File("").getAbsolutePath();
        return projectPath + "\\src\\main\\java\\" + this.getDaoPackage().replace(".", "\\").concat("\\");
    }


    private String firstLower(String str) {
        return StringUtils.isEmpty(str) || str.length() == 0 ? str : (str.substring(0, 1).toLowerCase().concat(str.substring(1)));
    }

    /**
     * 获取表名
     *
     * @return
     */
    private String getTableName() {
        return StringUtils.isEmpty(this.tableName) ? this.entity.getSimpleName() : this.tableName;
    }

    private Field getIdField(Class<?> entity) {
        Field idField = null;
        while (idField == null && entity != Object.class) {
            try {
                idField = entity.getDeclaredField(this.idName);
            } catch (NoSuchFieldException e) {
            }
            entity = entity.getSuperclass();
        }
        return idField;
    }

    private String getIdType() {
        Field idField = this.getIdField(this.entity);
        return idField == null ? "" : idField.getType().getSimpleName();
    }

    private void createDirectory(String path) {
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

}
