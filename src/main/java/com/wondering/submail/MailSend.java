package com.wondering.submail;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import com.wondering.common.RequestEncoder;
import net.sf.json.JSONObject;

public class MailSend {
	/**
	 * 时间戳接口配置
	 */
	public static final String TIMESTAMP = "https://api.mysubmail.com/service/timestamp";
	/**
	 * 备用接口
	 * http://api.mysubmail.com/service/timestamp
	 * https://api.submail.cn/service/timestamp
	 * http://api.submail.cn/service/timestamp
	 */
	
	public static final String TYPE_MD5 = "md5";
	public static final String TYPE_SHA1 = "sha1";
   /**
    * API 请求接口配置
    */
	private static final String URL="https://api.mysubmail.com/mail/send";

	
//public static void main(String[] args)
public void SendEmailCode(String verifyCode, String email)
{
		TreeMap<String, Object> requestData = new TreeMap<String, Object>();
		/**
		 * --------------------------------参数配置------------------------------------
		 * 请仔细阅读参数配置说明
		 * 
		 * 配置参数包括 appid, appkey ,to , signtype,from,subject
		 * 用户参数设置，其中 appid, appkey, from, to,subject 为必须参数
		 * 请访问 submail 官网创建并获取 appid 和 appkey，链接：https://www.mysubmail.com/chs/mail/apps
		 * 请访问 submail 官网创建获取邮件模板内容，链接：https://www.mysubmail.com/chs/mail/templates
		 * signtype 为可选参数: normal | md5 | sha1
		 * 当 signtype 参数为空时，默认为 normal
		 * 多个联系人，请用 “,” 半角逗号区分  e.g. "leo <leo@submail.cn>, <retro@submail.cn>, service@submail.cn";
         SUBMAIL 支持完整的 RFC 822 收件人标准，请确保您的邮件地址的有效性。请参见 维基百科 EMAIL ADDRESS RFC822 文档
         请注意：当你的  TO 参数和地址簿中的联系人总和大于 50 个联系人时，将自动被转入推广邮件通道，且本次发送不计算为触发类邮件，
         to 参数单次请求上限为 5000 个联系人
		 * --------------------------------------------------------------------------
		 */
		//String verifyCode1 = String.valueOf(new Random().nextInt(899999) + 100000); //生成6位验证码
		System.out.println("myemail:"+verifyCode);
		String appid = "14043";
		String appkey = "12a639acab6b78f1b3b0d24784c21b40";
		String to = email;
		String from = "service@wsgzjh.cn";
		//String text="你好，这里是流浪记服务中心";
		String text="【流浪记】，您的验证码："+verifyCode+"，请在10分钟内输入，如非本人操作请忽略。";
		//添加附件
		//File attachments=new File("");
		String subject="流浪记验证码";
		String signtype ="md5";
		/**
		 *  ---------------------------------------------------------------------------
		 */
		
		/**
		 *  签名验证方式
		 *  详细说明可参考 SUBMAIL 官网，开发文档 → 开始 → API 授权与验证机制
		 */
		requestData.put("appid", appid);
		requestData.put("from", from);
		requestData.put("to", to);
		requestData.put("subject", subject);
		//requestData.put("attachments", attachments);
		requestData.put("text",text);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		@SuppressWarnings("deprecation")
		ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE,HTTP.UTF_8);
		for(Map.Entry<String, Object> entry: requestData.entrySet()){
			String key = entry.getKey();
			Object value = entry.getValue();
			if(value instanceof String){
				builder.addTextBody(key, (String) value,contentType);
			}else if(value instanceof File){	
				builder.addBinaryBody(key, (File) value);
			}
		}	
		if(signtype.equals(TYPE_MD5) || signtype.equals(TYPE_SHA1)){
			String timestamp = getTimestamp();
			requestData.put("timestamp", timestamp);
			requestData.put("sign_type", signtype);
			String signStr = appid + appkey + RequestEncoder.formatRequest(requestData) + appid + appkey;
			System.out.println(signStr);
			builder.addTextBody("timestamp", timestamp);
			builder.addTextBody("sign_type", signtype);
			builder.addTextBody("signature", RequestEncoder.encode(signtype, signStr), contentType);
		}else{
			builder.addTextBody("signature", appkey, contentType);
		}
		/**
		 * http post 请求接口
		 * 成功返回 status: success
		 * 详细的 API 错误日志请访问 SUBMAIL 官网 → 开发文档 → DEBUG → API 错误代码
		 */
		HttpPost httpPost = new HttpPost(URL);
		httpPost.addHeader("charset", "UTF-8");
		httpPost.setEntity(builder.build());
		try{
			CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
			HttpResponse response = closeableHttpClient.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			if(httpEntity != null){
				String jsonStr = EntityUtils.toString(httpEntity, "UTF-8");
				System.out.println(jsonStr);
			}
		}catch(ClientProtocolException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * 获取时间戳
	 * @return
	 */
	private static String getTimestamp(){
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		HttpGet httpget = new HttpGet(TIMESTAMP);
		try{
			HttpResponse response = closeableHttpClient.execute(httpget);
			HttpEntity httpEntity = response.getEntity();
			String jsonStr = EntityUtils.toString(httpEntity,"UTF-8");
			if(jsonStr != null){
				JSONObject json = JSONObject.fromObject(jsonStr);
				return json.getString("timestamp");
			}
			closeableHttpClient.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
