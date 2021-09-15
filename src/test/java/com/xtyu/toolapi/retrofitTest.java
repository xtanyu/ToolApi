package com.xtyu.toolapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xtyu.toolapi.service.VideoService;
import com.xtyu.toolapi.utils.RestTemplateUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: 小熊
 * @date: 2021/8/26 17:05
 * @description:phone 17521111022
 */
@SpringBootTest
public class retrofitTest {
    @Autowired
    VideoService videoService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RestTemplateUtil restTemplateUtil;

    @Test
    public void testDouyinHttp(){
        System.out.println(JSON.toJSONString(videoService.getVideoInfo("","https://v.kuaishou.com/9Wou0M")));
//        videoService.parsingDyVideoInfo("https://v.douyin.com/e9ewg4y/");
//        String randomIp = getRandomIp();
//        restTemplate.headForHeaders("https://v.douyin.com/dR9Ew7U/").getLocation().toString();
//        String url = restTemplate.headForHeaders("https://v.douyin.com/dR9Ew7U/").getLocation().toString();
//        System.out.println(url);
//        String id = url.substring(url.indexOf("video/") + 6, url.indexOf("?"));
//        System.out.println(id);
//        String videoUrl = testHttp.getPerson(id).getJSONArray("item_list")
//                .getJSONObject(0).getJSONObject("video")
//                .getJSONObject("play_addr").getJSONArray("url_list").get(0).toString();
//        videoUrl = videoUrl.replace("playwm", "play");
//        url = Jsoup.connect(videoUrl).ignoreContentType(true).execute().url().toString();
//        System.out.println(url);
    }

    private static final Pattern ACCESS_PATTERN = Pattern.compile("(https?://v.kuaishou.com/[\\S]*)");

    //
    @Test
    public void testKSHttp() throws IOException {
        Matcher matcher = ACCESS_PATTERN.matcher("https://v.kuaishou.com/9Wou0M");
        if (matcher.find()) {
            String url = matcher.group(1);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("User-Agent", "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Mobile Safari/537.36");
            httpHeaders.set("Referer", url);
            url = Jsoup.connect(url).execute().url().toString();
            String htmlContent = restTemplateUtil.getForObject(url, httpHeaders, String.class);
            Document document = Jsoup.parse(htmlContent);
            System.out.println(document);
            Elements elements = document.getElementsByTag("script");
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).childNodeSize() > 0) {
                    System.out.println(elements.get(i).childNode(0).toString());
                    Matcher matcherForPageData = Pattern.compile("window.pageData[\\s]*=[\\s]*(.*)[\\s]*").matcher(elements.get(i).childNode(0).toString());
                    if (matcherForPageData.find()) {
                        String pageData = matcherForPageData.group(1);
                        JSONObject pageDataOb = JSONObject.parseObject(pageData);
                        System.out.println("用户名"+pageDataOb.getJSONObject("user").getString("name"));
                        System.out.println("用户头像"+pageDataOb.getJSONObject("user").getString("name"));
                        // 快手视频有图片（横向滑动，纵向滑动）和视频
                        JSONObject mediaJob = pageDataOb.getJSONObject("video");
                        // video.images(横向)、image_long（纵向）
                        String photoType = mediaJob.getString("type");
                        if ("video".equals(photoType)) {
                            System.out.println("视频地址"+mediaJob.getString("srcNoMark"));
                        }
                    }
                }

            }
        }
    }

//    public MediaParseResult parseVideoOrPhotos(String url, HttpHeaders headers) {
//        MediaParseResult mediaParseResult = new MediaParseResult();
//        mediaParseResult.setMediaApiType(getMediaApiType());
//
//        // 获取网页内容，从而获取pageData
//        String htmlContent = restTemplateUtil.getForObject(url, headers, String.class);
//        System.out.println(htmlContent);
//        Document document = Jsoup.parse(htmlContent);
//        Elements elements = document.getElementsByTag("script");
//        for (int i = 0; i < elements.size(); i++) {
//            if (elements.get(i).childNodeSize() > 0) {
////                log.info(elements.get(i).childNode(0).toString());
//                Matcher matcherForPageData = Pattern.compile("window.pageData[\\s]*=[\\s]*(.*)[\\s]*").matcher(elements.get(i).childNode(0).toString());
//                if (matcherForPageData.find()) {
//                    String pageData = matcherForPageData.group(1);
//                    JSONObject pageDataOb = JSONObject.parseObject(pageData);
//
//                    // 设置用户信息
//                    User user = new User();
//                    user.setName(pageDataOb.getJSONObject("user").getString("name"));
//                    user.setAvatar(pageDataOb.getJSONObject("user").getString("avatar"));
//                    user.setDescription("");
//
//                    // 添加到结果中
//                    mediaParseResult.setUser(user);
//                    // 快手视频有图片（横向滑动，纵向滑动）和视频
//                    JSONObject mediaJob = pageDataOb.getJSONObject("video");
//                    // video.images(横向)、image_long（纵向）
//                    String photoType = mediaJob.getString("type");
//                    if ("video".equals(photoType)) {
//                        // 处理视频
//                        Video video = new Video();
//                        video.setTitle(mediaJob.getString("caption"));
//                        video.setVideoCover(mediaJob.getString("poster"));
//                        List<String> videoUrls = new LinkedList<>();
//                        videoUrls.add(mediaJob.getString("srcNoMark"));
//                        video.setUrls(videoUrls);
//
//                        mediaParseResult.setMedia(video);
//                        return mediaParseResult;
//                    }
//                    if ("images".equals(photoType) || "image_long".equals(photoType)) {
//                        // 处理图片
//                        // 图片、音乐CDN域名
//                        String imageCDN = "https://" + mediaJob.getString("imageCDN");
//                        Photos photos = new Photos();
//                        photos.setDescription(mediaJob.getString("caption"));
//                        List<Photo> photoList = null;
//                        // 背景音乐地址
//                        String audioUrl = imageCDN + mediaJob.getString("audio");
//                        photoList = mediaJob.getJSONArray("images").stream().map(image -> {
//                            JSONObject imageJob = ((JSONObject) image);
//                            Photo photo = new Photo();
//                            photo.setUrl(imageCDN + imageJob.getString("path"));
//                            photo.setTitle(mediaJob.getString("caption"));
//                            photo.setAudioUrl(audioUrl);
//                            return photo;
//                        }).collect(Collectors.toList());
//                        photos.setPhotoList(photoList);
//
//                        mediaParseResult.setMedia(photos);
//                        return mediaParseResult;
//                    }
//                    break;
//                }
//            }
//
//        }
//
//        throw new CustomerException("解析失败");
//    }

    /*
     * 随机生成国内IP地址
     */
    public static String getRandomIp() {
        //ip范围
        int[][] range = {{607649792, 608174079},//36.56.0.0-36.63.255.255
                {1038614528, 1039007743},//61.232.0.0-61.237.255.255
                {1783627776, 1784676351},//106.80.0.0-106.95.255.255
                {2035023872, 2035154943},//121.76.0.0-121.77.255.255
                {2078801920, 2079064063},//123.232.0.0-123.235.255.255
                {-1950089216, -1948778497},//139.196.0.0-139.215.255.255
                {-1425539072, -1425014785},//171.8.0.0-171.15.255.255
                {-1236271104, -1235419137},//182.80.0.0-182.92.255.255
                {-770113536, -768606209},//210.25.0.0-210.47.255.255
                {-569376768, -564133889}, //222.16.0.0-222.95.255.255
        };
        Random rdInt = new Random();
        int index = rdInt.nextInt(10);
        String ip = num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
        return ip;
    }

    /*
     * 将十进制转换成ip地址
     */
    public static String num2ip(int ip) {
        int[] b = new int[4];
        String x = "";
        b[0] = (int) ((ip >> 24) & 0xff);
        b[1] = (int) ((ip >> 16) & 0xff);
        b[2] = (int) ((ip >> 8) & 0xff);
        b[3] = (int) (ip & 0xff);
        x = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(b[2]) + "." + Integer.toString(b[3]);
        return x;
    }
}
