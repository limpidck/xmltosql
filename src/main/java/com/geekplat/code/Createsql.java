package com.geekplat.code;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import org.dom4j.Element;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Createsql {

        public static void main(String[] args) throws Exception {
        	//xml文件所在目录
        	String xmlPath = "E://stsEnvs/temptest/src/main/resource";
        	//生成sql输出文件绝对地址
        	String sqlPath = "E://stsEnvs/temptest/src/main/resource/test.sql";
            generateSql(xmlPath, sqlPath);
        }
        
        /**
         * 生成sql
         * @param dirPath mapper.xml所在的文件夹
         * @param sqlFile 待生成sql的输出文件
         * @throws IOException
         */
        private static void generateSql(String dirPath,String sqlFile) throws IOException {
            FileWriter fw = null;
            try {
                File dir = new File(dirPath);
                File sql = new File(sqlFile);
                if (sql.exists()){
                    sql.delete();
                }
                sql.createNewFile();
                fw = new FileWriter(sql);

                if (dir.exists() && dir.isDirectory()){
                	//列出目录内所有xml文件
                    File[] files = dir.listFiles();
                    for (File file : files) {
                        if (file.isFile() && file.getName().endsWith(".xml")){
                            System.out.println(file.getName());
                            fw.append("\r\n");
                            //调用生成sql的方法
                            fw.append(getSql(file));
                            fw.flush();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                if (fw != null)
                    fw.close();
            }
        }

        /**
         * 根据xm文件生成sql语句
         * @param xmlfile xml文件file
         * @return sql语句
         * @throws DocumentException
         */
        private static String getSql(File xmlfile) throws DocumentException {
            SAXReader saxReader= new SAXReader();
            Document document = saxReader.read(xmlfile);
            org.dom4j.Element root = document.getRootElement();
            //取出resultMap标签定义进行解析生成sql语句，不同项目中名称定义可能有不同
            Element resultMap = root.element("resultMap");
            TableBo tab = new TableBo();
            tab.setTableName(getTableNameByResultMap(resultMap));
            tab.setColumns(getColumns(resultMap));
            return tab.toString();
        }
        
        //取出所有字段定义
        private static Map<String,String> getColumns(Element resultMap){
            List<Element> elements = resultMap.elements();
            Map<String,String> map = new LinkedHashMap<String, String>();
            for (Element element : elements) {
            	//column和jdbcType均为xml中的标签名称
                map.put(element.attribute("column").getValue(), element.attribute("jdbcType").getValue());
            }
            return map;
        }
        
        //取出表名定义--此处采用驼峰实体名转下划线，前面加统一前缀方式
        private static String getTableNameByResultMap(Element resultMap){
        	//表名前拼接的统一前缀，考虑到部分公司习惯在表名前加上项目或者公司标识
        	String tableNamePrefix = "rd_";
        	//com.test.TestXmlToSql
        	//取出实体类对应路径名称
        	String domainClassPathName = resultMap.attributeValue("type");
        	//类名
        	String className = domainClassPathName.substring(domainClassPathName.lastIndexOf(".")+1);
        	return tableNamePrefix+StringUtil.underscoreName(className);
        }
        
    }