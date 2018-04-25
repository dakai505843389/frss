package com.hd.clc.frss.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hd.clc.frss.vo.FaceUtilVO.FaceSearchResultVO;
import com.hd.clc.frss.vo.FaceUtilVO.FaceSetVO;
import com.hd.clc.frss.vo.FaceUtilVO.FaceVO;

import javax.net.ssl.SSLException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FaceUtil {

    private final static String API_KEY = "btTt1iJQC1O3sPEPTsZRJKdTZFb94GKX";
    private final static String API_SECRET = "1mQxXaUSESmMYnCk9xTCLHjrB_i-PPyZ";
    // 传入图片进行人脸检测和人脸分析的apiUrl
    private final static String DETECT_API_URL = "https://api-cn.faceplusplus.com/facepp/v3/detect";
    // 在一个已有的 FaceSet 中找出与目标人脸最相似的一张或多张人脸，返回置信度和不同误识率下的阈值的apiUrl
    private final static String SEARCH_API_URL = "https://api-cn.faceplusplus.com/facepp/v3/search";
    // 创建一个人脸的集合 FaceSet的apiUrl（用于存储人脸标识 face_token。一个 FaceSet 能够存储 10000 个 face_token）
    private final static String CREATE_FACESET_API_URL = "https://api-cn.faceplusplus.com/facepp/v3/faceset/create";
    // 为一个已经创建的 FaceSet 添加人脸标识 face_token的apiUrl
    private final static String ADD_FACE_API_URL = " https://api-cn.faceplusplus.com/facepp/v3/faceset/addface";
    // 分隔符
    private static String boundaryString = StringUtil.getBoundary();

    /**
     * 辨认人脸得到face
     * @param imagePath
     * @return
     * @throws IOException
     */
    public static FaceVO detect(String imagePath) throws IOException {
        File file = new File(imagePath);
        byte[] buff = IOUtil.getBytesFromFile(file);
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> byteMap = new HashMap<>();
        map.put("api_key", API_KEY);
        map.put("api_secret", API_SECRET);
        map.put("return_attributes", "facequality");
        byteMap.put("image_file", buff);
        try {
            byte[] bacd = post(DETECT_API_URL, map, byteMap);
            String str = new String(bacd);
            JSONObject faceObject = JSONObject.parseObject(str);
            FaceVO faceVO = JSONObject.parseObject(str, FaceVO.class);
            faceVO.setFace_token(faceObject.getJSONArray("faces").getJSONObject(0).getString("face_token"));
            faceVO.setThreshold(faceObject.getJSONArray("faces").getJSONObject(0).getJSONObject("attributes").getJSONObject("facequality").getDouble("threshold"));
            faceVO.setValue(faceObject.getJSONArray("faces").getJSONObject(0).getJSONObject("attributes").getJSONObject("facequality").getDouble("value"));
            return faceVO;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 添加face到FaceSet
     * @param faceset_token
     * @param face_tokens
     * @return
     * @throws IOException
     */
    public static FaceSetVO addFace(String faceset_token, String face_tokens) throws IOException{
        HashMap<String, String> map = new HashMap<>();
        map.put("api_key", API_KEY);
        map.put("api_secret", API_SECRET);
        map.put("faceset_token", faceset_token);
        map.put("face_tokens", face_tokens);
        byte[] bacd = post(ADD_FACE_API_URL, map, null);
        String str = new String(bacd);
        JSONObject faceSetObject = JSONObject.parseObject(str);
        FaceSetVO faceSetVO = JSONObject.parseObject(str, FaceSetVO.class);
        JSONArray failure_detail = faceSetObject.getJSONArray("failure_detail");
        if (failure_detail != null && failure_detail.size() > 0){
            faceSetVO.setReason(failure_detail.getJSONObject(0).getString("reason"));
        }
        return faceSetVO;
    }

    /**
     * 在指定faceSet中搜索与当前face匹配的token
     * @param imagePath
     * @param faceset_token
     * @return
     * @throws IOException
     */
    public static FaceSearchResultVO search(String imagePath, String faceset_token) throws IOException{
        File file = new File(imagePath);
        byte[] buff = IOUtil.getBytesFromFile(file);
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> byteMap = new HashMap<>();
        map.put("api_key", API_KEY);
        map.put("api_secret", API_SECRET);
        map.put("faceset_token", faceset_token);
        byteMap.put("image_file", buff);
        byte[] bacd = post(SEARCH_API_URL, map, byteMap);
        String str = new String(bacd);
        JSONObject faceSearchObject = JSONObject.parseObject(str);
        FaceSearchResultVO faceSearchResultVO = JSONObject.parseObject(str, FaceSearchResultVO.class);
        if (faceSearchResultVO.getError_message() == null) {
            faceSearchResultVO.setE_3(faceSearchObject.getJSONObject("thresholds").getDouble("1e-3"));
            faceSearchResultVO.setFace_token(faceSearchObject.getJSONArray("results").getJSONObject(0).getString("face_token"));
            faceSearchResultVO.setConfidence(faceSearchObject.getJSONArray("results").getJSONObject(0).getDouble("confidence"));
        }
        return faceSearchResultVO;
    }

    /**
     * 新建FaceSet集合
     * @return
     * @throws IOException
     */
    public static FaceSetVO createFaceSet() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put("api_key", API_KEY);
        map.put("api_secret", API_SECRET);
        byte[] bacd = post(CREATE_FACESET_API_URL, map, null);
        String str = new String(bacd);
        FaceSetVO faceSetVO = JSONObject.parseObject(str, FaceSetVO.class);
        return faceSetVO;
    }

    /**
     * post方法
     * @param url
     * @param map
     * @param fileMap
     * @return
     * @throws Exception
     */
    public static byte[] post(String url, HashMap<String, String> map, HashMap<String, byte[]> fileMap) throws IOException {
        HttpURLConnection conne;
        URL url1 = new URL(url);
        conne = (HttpURLConnection) url1.openConnection();
        conne.setDoOutput(true);
        conne.setUseCaches(false);
        conne.setRequestMethod("POST");
        conne.setConnectTimeout(BaseVar.CONNECT_TIME_OUT);
        conne.setReadTimeout(BaseVar.READ_OUT_TIME);
        conne.setRequestProperty("accept", "*/*");
        conne.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
        conne.setRequestProperty("connection", "Keep-Alive");
        conne.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
        DataOutputStream obos = new DataOutputStream(conne.getOutputStream());
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry) iter.next();
            String key = entry.getKey();
            String value = entry.getValue();
            obos.writeBytes("--" + boundaryString + "\r\n");
            obos.writeBytes("Content-Disposition: form-data; name=\"" + key
                    + "\"\r\n");
            obos.writeBytes("\r\n");
            obos.writeBytes(value + "\r\n");
        }
        if(fileMap != null && fileMap.size() > 0){
            Iterator fileIter = fileMap.entrySet().iterator();
            while(fileIter.hasNext()){
                Map.Entry<String, byte[]> fileEntry = (Map.Entry<String, byte[]>) fileIter.next();
                obos.writeBytes("--" + boundaryString + "\r\n");
                obos.writeBytes("Content-Disposition: form-data; name=\"" + fileEntry.getKey()
                        + "\"; filename=\"" + encode(" ") + "\"\r\n");
                obos.writeBytes("\r\n");
                obos.write(fileEntry.getValue());
                obos.writeBytes("\r\n");
            }
        }
        obos.writeBytes("--" + boundaryString + "--" + "\r\n");
        obos.writeBytes("\r\n");
        obos.flush();
        obos.close();
        InputStream ins = null;
        int code = conne.getResponseCode();
        try{
            if(code == 200){
                ins = conne.getInputStream();
            }else{
                ins = conne.getErrorStream();
            }
        }catch (SSLException e){
            e.printStackTrace();
            return new byte[0];
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];
        int len;
        while((len = ins.read(buff)) != -1){
            baos.write(buff, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        ins.close();
        return bytes;
    }

    /**
     * encode方法
     * @param value
     * @return
     * @throws Exception
     */
    private static String encode(String value) throws IOException{
        return URLEncoder.encode(value, "UTF-8");
    }

}
