package io.zhijian.file;


import org.apache.commons.net.ftp.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class FTPService {
    private FTPClient ftpClient = null;
    private String host;
    private int port;
    private String username;
    private String password;
    private static FTPService ftpService;

    public FTPService(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public static FTPService getInstance() {
        ftpService = new FTPService(FTPConfig.getHost(), FTPConfig.getPort(), FTPConfig.getUsername(), FTPConfig.getPassword());
        return ftpService;
    }

    /**
     * 链接到服务器
     *
     * @return
     * @throws Exception
     */
    public boolean open() {
        /*if (ftpClient != null && ftpClient.isConnected()) {
            return true;
		}*/
        try {
            ftpClient = new FTPClient();
            // 连接
            ftpClient.connect(this.host, this.port);
            ftpClient.login(this.username, this.password);
            // 检测连接是否成功
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                this.close();
                System.err.println("FTP host refused connection.");
                System.exit(1);
            }
//			System.out.println("open FTP success:" + this.host + ";port:" + this.port + ";name:" + this.username + ";pwd:" + this.password);
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE); // 设置上传模式.binally
            // or ascii
            return true;
        } catch (Exception ex) {
            // 关闭
            this.close();
            ex.printStackTrace();
            return false;
        }
    }

    private boolean cd(String dir) throws IOException {
        if (ftpClient.changeWorkingDirectory(dir)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取目录下所有的文件名称
     *
     * @param filePath
     * @return
     * @throws IOException
     */

    private FTPFile[] getFileList(String filePath) throws IOException {
        FTPFile[] list = ftpClient.listFiles();
        return list;

    }

    /**
     * 循环将设置工作目录
     */
    public boolean changeDir(String ftpPath) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {

            // 将路径中的斜杠统一
            char[] chars = ftpPath.toCharArray();
            StringBuffer sbStr = new StringBuffer(256);
            for (int i = 0; i < chars.length; i++) {

                if ('\\' == chars[i]) {
                    sbStr.append('/');
                } else {
                    sbStr.append(chars[i]);
                }
            }
            ftpPath = sbStr.toString();
            // System.out.println("ftpPath"+ftpPath);

            if (ftpPath.indexOf('/') == -1) {
                // 只有一层目录
                // System.out.println("change"+ftpPath);
                ftpClient.changeWorkingDirectory(new String(ftpPath.getBytes(), "iso-8859-1"));
            } else {
                // 多层目录循环创建
                String[] paths = ftpPath.split("/");
                // String pathTemp = "";
                for (int i = 0; i < paths.length; i++) {
                    // System.out.println("change "+paths[i]);
                    ftpClient.changeWorkingDirectory(new String(paths[i].getBytes(), "iso-8859-1"));
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 循环创建目录，并且创建完目录后，设置工作目录为当前创建的目录下
     */
    public boolean mkDir(String ftpPath) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {

            // 将路径中的斜杠统一
            char[] chars = ftpPath.toCharArray();
            StringBuffer sbStr = new StringBuffer(256);
            for (int i = 0; i < chars.length; i++) {

                if ('\\' == chars[i]) {
                    sbStr.append('/');
                } else {
                    sbStr.append(chars[i]);
                }
            }
            ftpPath = sbStr.toString();
//			System.out.println("ftpPath" + ftpPath);

            if (ftpPath.indexOf('/') == -1) {
                // 只有一层目录
                FTPFile[] ftpFiles = ftpClient.listFiles(ftpPath);
                if (ftpFiles.length == 0) {
                    ftpClient.makeDirectory(new String(ftpPath.getBytes(), "iso-8859-1"));
                }

                ftpClient.changeWorkingDirectory(new String(ftpPath.getBytes(), "iso-8859-1"));
            } else {
                // 多层目录循环创建
                String[] paths = ftpPath.split("/");
                // String pathTemp = "";
                for (int i = 0; i < paths.length; i++) {
                    // 只有一层目录
                    FTPFile[] ftpFiles = ftpClient.listFiles(ftpPath);
                    if (ftpFiles.length == 0) {
                        ftpClient.makeDirectory(new String(paths[i].getBytes(), "iso-8859-1"));
                    }
                    ftpClient.changeWorkingDirectory(new String(paths[i].getBytes(), "iso-8859-1"));
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 上传文件到FTP服务器
     *
     * @param localDirectoryAndFileName 本地文件目录和文件名
     * @param ftpFileName               上传后的文件名
     * @param ftpDirectory              FTP目录如:/path1/pathb2/,如果目录不存在回自动创建目录
     * @throws Exception
     */
    public boolean put(String localDirectoryAndFileName, String ftpFileName, String ftpDirectory) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        boolean flag = false;
        if (ftpClient != null) {
            File srcFile = new File(localDirectoryAndFileName);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(srcFile);

                // 创建目录

                this.mkDir(ftpDirectory);

                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");

                // 设置文件类型（二进制）
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                // 上传
                flag = ftpClient.storeFile(new String(ftpFileName.getBytes(), "iso-8859-1"), fis);
            } catch (Exception e) {
                this.close();
                e.printStackTrace();
                return false;
            } finally {
                this.close();
            }
        }

//		System.out.println("success put file " + localDirectoryAndFileName + " to " + ftpDirectory + ftpFileName);
        return flag;
    }

    /**
     * @param @param  srcFile
     * @param @param  fileType 文件类型
     * @param @param  ftpDirectory
     * @param @return
     * @return boolean  返回文件上传信息
     * @throws
     * @Description: 上传文件
     * @author zhangwei
     * @date 2016-4-29
     */
    public String upload(File srcFile, String ftpDirectory) {
        String ftpFileName = UUID.randomUUID().toString() + srcFile.getName().substring(srcFile.getName().lastIndexOf("."));
        this.open();
        if (!ftpClient.isConnected()) {
            return null;
        }
        boolean flag = false;
        if (ftpClient != null) {
            //File srcFile = new File(localDirectoryAndFileName);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(srcFile);
                // 创建目录
                this.mkDir(ftpDirectory);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                // 设置文件类型（二进制）
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                // 上传
                flag = ftpClient.storeFile(new String(ftpFileName.getBytes(), "iso-8859-1"), fis);
                return ftpDirectory + "/" + ftpFileName;
            } catch (Exception e) {
                this.close();
                e.printStackTrace();
                return null;
            } finally {
                this.close();
            }
        }

        //System.out.println("success put file " + localDirectoryAndFileName + " to " + ftpDirectory + ftpFileName);
        return ftpFileName;
    }

    /**
     * @param @param  source
     * @param @param  targetDir  /back
     * @param @throws IOException
     * @return void
     * @throws
     * @Description: 删除文件
     * @author zhangwei
     * @date 2016-5-9
     */
    public String removeFile(String source, String targetDir) throws IOException {
        this.open();
        String storagePath = "";
        String sourceDir = source.substring(0, source.lastIndexOf("/"));
        String sourceFileName = source.substring(source.lastIndexOf("/") + 1);
        storagePath = targetDir + sourceFileName;
        ByteArrayInputStream in = null;
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        try {
			/*
			 * if (!existDirectory(targetDir)) { createDirectory(targetDir); }
			 */
            this.mkDir(targetDir);
            ftpClient.setBufferSize(1024 * 2);
            // 变更工作路径
            ftpClient.changeWorkingDirectory(sourceDir);
            // 设置以二进制流的方式传输
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            // 将文件读到内存中
            ftpClient.retrieveFile(new String(sourceFileName.getBytes("UTF-8"), "iso-8859-1"), fos);
            in = new ByteArrayInputStream(fos.toByteArray());
            if (in != null) {
                ftpClient.changeWorkingDirectory(targetDir);
                ftpClient.storeFile(new String(sourceFileName.getBytes("UTF-8"), "iso-8859-1"), in);
            }
            ftpClient.deleteFile(source);//删除备用文件
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (in != null) {
                in.close();
            }
            if (fos != null) {
                fos.close();
            }
            this.close();
        }
        return storagePath;
    }

    public String upload(byte[] bytes, String fileType, String ftpDirectory) {
        //fileType =".png";//temp
        String ftpFileName = UUID.randomUUID().toString() + fileType;
        this.open();
        if (!ftpClient.isConnected()) {
            return null;
        }
        boolean flag = false;
        if (ftpClient != null) {
            //File srcFile = new File(localDirectoryAndFileName);
            FileInputStream fis = null;
            try {
                //fis = new FileInputStream(srcFile);
                InputStream is = new ByteArrayInputStream(bytes);
                // 创建目录
                this.mkDir(ftpDirectory);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                // 设置文件类型（二进制）
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                // 上传
                //flag = ftpClient.storeFile(new String(ftpFileName.getBytes(), "iso-8859-1"), fis);
                flag = ftpClient.storeUniqueFile(new String(ftpFileName.getBytes(), "iso-8859-1"), is);
                return ftpDirectory + "/" + ftpFileName;
            } catch (Exception e) {
                this.close();
                e.printStackTrace();
                return null;
            } finally {
                this.close();
            }
        }

        //System.out.println("success put file " + localDirectoryAndFileName + " to " + ftpDirectory + ftpFileName);
        return ftpFileName;
    }

    /**
     * 从FTP服务器上下载文件并返回下载文件长度
     *
     * @param ftpDirectoryAndFileName
     * @param localDirectoryAndFileName
     * @return
     * @throws Exception
     */
    public long get(String ftpDirectoryAndFileName, String localDirectoryAndFileName) {

        long result = 0;
        if (!ftpClient.isConnected()) {
            return 0;
        }
        ftpClient.enterLocalPassiveMode(); // Use passive mode as default
        // because most of us are behind
        // firewalls these days.

        try {
            // 将路径中的斜杠统一
            char[] chars = ftpDirectoryAndFileName.toCharArray();
            StringBuffer sbStr = new StringBuffer(256);
            for (int i = 0; i < chars.length; i++) {

                if ('\\' == chars[i]) {
                    sbStr.append('/');
                } else {
                    sbStr.append(chars[i]);
                }
            }
            ftpDirectoryAndFileName = sbStr.toString();
            String filePath = ftpDirectoryAndFileName.substring(0, ftpDirectoryAndFileName.lastIndexOf("/"));
            String fileName = ftpDirectoryAndFileName.substring(ftpDirectoryAndFileName.lastIndexOf("/") + 1);
            // System.out.println("filePath | "+filePath);
            // System.out.println("fileName | "+fileName);
            this.changeDir(filePath);
            ftpClient.retrieveFile(new String(fileName.getBytes(), "iso-8859-1"), new FileOutputStream(localDirectoryAndFileName)); // download
            // file
//			System.out.print("file length"+ftpClient.getReplyString()); // check result

        } catch (IOException e) {
            e.printStackTrace();
        }
//		System.out.println("Success get file" + ftpDirectoryAndFileName + " from " + localDirectoryAndFileName);
        return result;
    }

    /**
     * @param
     * @return void
     * @throws IOException
     * @throws
     * @Description: 文件下载
     * @author zhangwei
     * @date 2016-4-29
     */
    public void downloadFile(HttpServletResponse response, String realFilename, String ftpDirectoryAndFileName) throws IOException {
        this.open();

        //String localDirectoryAndFileName ="D:/temp/upload/fdsfdsfsd.png";//本地文件的存放路径 文件名不能相同
        int size = this.getFileSize(ftpDirectoryAndFileName);//获取ftp服务器文件大小
        ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
        long result = 0;
        if (!ftpClient.isConnected()) {
            //return 0;
//			System.out.println("ftp服务器连接失败");
        }
        ftpClient.enterLocalPassiveMode(); // Use passive mode as default
        // because most of us are behind
        // firewalls these days.

        try {
            // 将路径中的斜杠统一
            char[] chars = ftpDirectoryAndFileName.toCharArray();
            StringBuffer sbStr = new StringBuffer(256);
            for (int i = 0; i < chars.length; i++) {

                if ('\\' == chars[i]) {
                    sbStr.append('/');
                } else {
                    sbStr.append(chars[i]);
                }
            }
            ftpDirectoryAndFileName = sbStr.toString();
            String filePath = ftpDirectoryAndFileName.substring(0, ftpDirectoryAndFileName.lastIndexOf("/"));
            String fileName = ftpDirectoryAndFileName.substring(ftpDirectoryAndFileName.lastIndexOf("/") + 1);
            // System.out.println("filePath | "+filePath);
            // System.out.println("fileName | "+fileName);
            this.changeDir(filePath);
            //ftpClient.retrieveFile(new String(fileName.getBytes(), "iso-8859-1"), new FileOutputStream(localDirectoryAndFileName)); // download
            ftpClient.retrieveFile(new String(fileName.getBytes(), "iso-8859-1"), bos);
            // file
//			System.out.print(ftpClient.getReplyString()); // check result

        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("Success get file" + ftpDirectoryAndFileName + " from " + localDirectoryAndFileName);
        //byte[]  bytes = this.toByteArray(localDirectoryAndFileName);
        byte[] bytes = bos.toByteArray();
        bos.close();
        this.close();
        try {
            // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
            response.addHeader("Content-Disposition", "attachment;filename=test.png");
            //response.addHeader("Content-Length", "" + file.length());
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            os.write(bytes);// 输出文件
            os.flush();
            os.close();
            // print(bytes);
        } catch (Exception e) {
//			System.out.println("fail download file");
        }
    }

    /**
     * @param @param  fileName 路径+文件名 文件名需唯一
     * @param @return 没有文件返回0
     * @return long
     * @throws IOException
     * @throws
     * @Description: 获取服务器文件大小
     * @author zhangwei
     * @date 2016-4-29
     */
    public int getFileSize(String fileName) throws IOException {
        FTPListParseEngine engine = ftpClient.initiateListParsing("/upload/uu/mytest.jar");//demo
        while (engine.hasNext()) {
            FTPFile[] files = engine.getNext(5);
            for (int i = 0; i < files.length; i++) {// 获取文件名
//				System.out.println(files[i].getName());// 获取文件大小
                int size = (int) files[i].getSize();
//				System.out.println(size / 1024 + "kb");
                return size;
            }
        }
        return 0;
    }

    /**
     * @param @param  filename
     * @param @return
     * @param @throws IOException
     * @return byte[]
     * @throws
     * @Description: 将文件转换成byte[]
     * @author zhangwei
     * @date 2016-4-29
     */
    public byte[] toByteArray(String filename) throws IOException {
        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 返回FTP目录下的文件列表
     *
     * @param ftpDirectory
     * @return
     */
    public List getFileNameList(String ftpDirectory) {
        List list = new ArrayList();
        // if (!open())
        // return list;
        // try {
        // DataInputStream dis = new DataInputStream(ftpClient
        // .nameList(ftpDirectory));
        // String filename = "";
        // while ((filename = dis.readLine()) != null) {
        // list.add(filename);
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        return list;
    }

    /**
     * 删除FTP上的文件
     *
     * @param ftpDirAndFileName
     */
    public boolean deleteFile(String ftpDirAndFileName) {
        this.open();
        if (!ftpClient.isConnected()) {
            return false;
        }
        this.open();
        try {
            ftpClient.deleteFile(ftpDirAndFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.close();
        return true;
    }

    /**
     * 删除FTP目录
     *
     * @param ftpDirectory
     */
    public boolean deleteDirectory(String ftpDirectory) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        // ToDo
        return true;
    }

    /**
     * 关闭链接
     */
    public void close() {
        try {
            if (ftpClient != null && ftpClient.isConnected())
                ftpClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
//		System.out.println("Close Server Success :" + this.host + ";port:" + this.port);
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {

        this.ftpClient = ftpClient;
    }


    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        if (count > 2147483647L) {
            return -1;
        }
        return (int) count;
    }

    public static long copyLarge(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[4096];
        long count = 0L;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
}

